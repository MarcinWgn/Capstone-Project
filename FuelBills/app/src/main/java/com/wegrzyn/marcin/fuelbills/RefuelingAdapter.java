package com.wegrzyn.marcin.fuelbills;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wegrzyn.marcin.fuelbills.databinding.RefuelingCardItemViewBinding;

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
        RefuelingCardItemViewBinding binding = DataBindingUtil.bind(view);
        return new RefuelingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelingViewHolder holder, int position) {

        if(refuelingList != null){
            Refueling refueling = refuelingList.get(position);

            String date = String.valueOf(refueling.getDate());
            holder.binding.dateTv.setText(date);

            int tripDist = refueling.getTripDist();
            holder.binding.tripDistTv.setText(String.valueOf(tripDist));

            int dist = refueling.getDist();
            holder.binding.totalDistEt.setText(String.valueOf(dist));

            float quantity = refueling.getQuantity();
            holder.binding.quantityEt.setText(String.valueOf(quantity));

            double price = refueling.getPrice();
            holder.binding.priceEt.setText(String.valueOf(price));

            double totalPrice = refueling.getTotalPrice();
            holder.binding.totalPriceEt.setText(String.valueOf(totalPrice));

            float avg = refueling.getAvg();

            holder.binding.avgTv.setText( String.format(Locale.getDefault(),"%.2f",avg));

            String note = refueling.getNote();
            holder.binding.noteEt.setText(note);

            holder.binding.unitPriceTv.setText(refuelingList.get(position).getCurrency());
            holder.binding.unitTotalPriceTv.setText(refuelingList.get(position).getCurrency());

            if(refuelingList.get(position).getFuelUnit()
                    .contains(context.getString(R.string.mpg))){
                String mil = context.getString(R.string.mil);
                holder.binding.tripUnitTv.setText(mil);
                holder.binding.totalUnitTv.setText(mil);
                holder.binding.quantityUnitTv.setText(R.string.galoon);
                holder.binding.unitAvgTv.setText(R.string.mpg);
            }else {
                String km = context.getString(R.string.km);
                holder.binding.tripUnitTv.setText(km);
                holder.binding.totalUnitTv.setText(km);
                holder.binding.quantityUnitTv.setText(context.getString(R.string.litres));
                holder.binding.unitAvgTv.setText(context.getString(R.string.l_100km));
            }

        }
    }

    @Override
    public int getItemCount() {
        if(refuelingList!=null) return refuelingList.size();
        return 0;
    }

    class RefuelingViewHolder extends RecyclerView.ViewHolder {

        RefuelingCardItemViewBinding binding;

        RefuelingViewHolder(RefuelingCardItemViewBinding viewBinding) {
            super(viewBinding.getRoot());

            binding = viewBinding;

            binding.delRefuelingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemDelete(getAdapterPosition());
                }
            });
            binding.editRefuelingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.editItemData(refuelingList.get(getAdapterPosition()).getRefuelingId());
                }
            });
            viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
