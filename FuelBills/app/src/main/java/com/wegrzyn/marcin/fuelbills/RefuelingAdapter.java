package com.wegrzyn.marcin.fuelbills;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by Marcin Węgrzyn on 29.07.2018.
 * wireamg@gmail.com
 */
public class RefuelingAdapter extends RecyclerView.Adapter<RefuelingAdapter.RefuelingViewHolder>  {


    private final Context context;
    private List<Refueling> refuelingList;
    private ListItemClickListener itemClickListener;

    RefuelingAdapter(Context context, ListItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    void setRefuelingList(List<Refueling>refuelingList){
        this.refuelingList = refuelingList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RefuelingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.refueling_card_item_view,parent,false);
        return new RefuelingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelingViewHolder holder, int position) {

        if(refuelingList != null){
            Refueling refueling = refuelingList.get(position);

            String date = String.valueOf(refueling.getDate());
            holder.date.setText(date);

            int tripDist = refueling.getTripDist();
            holder.tripDistance.setText(String.valueOf(tripDist));

            int dist = refueling.getDist();
            holder.distance.setText(String.valueOf(dist));

            float quantity = refueling.getQuantity();
            holder.quantity.setText(String.valueOf(quantity));

            double price = refueling.getPrice();
            holder.price.setText(String.valueOf(price));

            double totalPrice = refueling.getTotalPrice();
            holder.totalPrice.setText(String.valueOf(totalPrice));

            float avg = refueling.getAvg();

            holder.avg.setText( String.format(Locale.getDefault(),"%.2f",avg));

            String note = refueling.getNote();
            holder.note.setText(note);

            holder.priceUnit.setText(refuelingList.get(position).getCurrency());
            holder.totalPriceUnit.setText(refuelingList.get(position).getCurrency());

            if(refuelingList.get(position).getFuelUnit()
                    .contains(context.getString(R.string.mpg))){
                String mil = context.getString(R.string.mil);
                holder.tripDistUnit.setText(mil);
                holder.distanceUnit.setText(mil);
                holder.quantityUnit.setText(R.string.galoon);
                holder.avgUnit.setText(R.string.mpg);
            }else {
                String km = context.getString(R.string.km);
                holder.tripDistUnit.setText(km);
                holder.distanceUnit.setText(km);
                holder.quantityUnit.setText(context.getString(R.string.litres));
                holder.avgUnit.setText(context.getString(R.string.l_100km));
            }

        }
    }

    @Override
    public int getItemCount() {
        if(refuelingList!=null) return refuelingList.size();
        return 0;
    }

    class RefuelingViewHolder extends RecyclerView.ViewHolder {

            TextView date;
            TextView tripDistance;
            TextView distance;
            TextView quantity;
            TextView price;
            TextView totalPrice;
            TextView avg;
            TextView note;

            TextView tripDistUnit;
            TextView distanceUnit;
            TextView quantityUnit;
            TextView avgUnit;
            TextView priceUnit;
            TextView totalPriceUnit;

            ImageButton deleteRef;
            ImageButton editRef;

        RefuelingViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_tv);
            tripDistance = itemView.findViewById(R.id.trip_dist_tv);
            distance = itemView.findViewById(R.id.total_dist_et);
            quantity = itemView.findViewById(R.id.quantity_et);
            price = itemView.findViewById(R.id.price_et);
            totalPrice = itemView.findViewById(R.id.total_price_et);
            avg = itemView.findViewById(R.id.avg_tv);
            note = itemView.findViewById(R.id.note_et);
            deleteRef = itemView.findViewById(R.id.del_refueling_btn);
            editRef = itemView.findViewById(R.id.edit_refueling_btn);

            tripDistUnit = itemView.findViewById(R.id.trip_unit_tv);
            distanceUnit =itemView.findViewById(R.id.total_unit_tv);
            quantityUnit =itemView.findViewById(R.id.quantity_unit_tv);
            avgUnit = itemView.findViewById(R.id.unit_avg_tv);
            priceUnit = itemView.findViewById(R.id.unit_price_tv);
            totalPriceUnit = itemView.findViewById(R.id.unit_total_price_tv);

            deleteRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemDelete(getAdapterPosition());
                }
            });
            editRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.editItemData(refuelingList.get(getAdapterPosition()).getRefuelingId());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
