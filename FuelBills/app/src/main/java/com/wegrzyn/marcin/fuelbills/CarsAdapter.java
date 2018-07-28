package com.wegrzyn.marcin.fuelbills;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 28.07.2018.
 * wireamg@gmail.com
 */
public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {

    private final Context context;
    private List<Car> carList;

    private ListItemClickListener itemClickListener;

    CarsAdapter(Context context, ListItemClickListener listItemClickListener) {
        this.context = context;
        itemClickListener = listItemClickListener
;    }

    interface ListItemClickListener{
        void onItemClick(int itemIndex);
        void onItemDelete(int itemDelete);
        void onInsertRefueling();
    }
    void setCars(List<Car>carList){
        this.carList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarsAdapter.CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.car_card_item_view,parent,false);
        return new CarsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.CarsViewHolder holder, int position) {
        if(carList != null){
            Car car = carList.get(position);
            String name = car.getName();
            if(!name.isEmpty())holder.nameTextView.setText(name);
            String vin = car.getVin();
            if(vin!=null) holder.vinTextView.setText(vin);
            int fuel = car.getFuelType();
            if(fuel!= 0)holder.fuelTextView.setText(String.valueOf(fuel));
            String plate = car.getPlate();
            if(plate!=null) holder.plateTextView.setText(plate);
            String tank = String.valueOf(car.getTankSize());
            if(!tank.isEmpty()) holder.tankTextView.setText(tank);
        }else {
            holder.nameTextView.setText("no car");
        }
    }

    @Override
    public int getItemCount() {
        if(carList!=null) return carList.size();
        return 0;
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView vinTextView;
        TextView fuelTextView;
        TextView plateTextView;
        TextView tankTextView;
        ImageButton deleteCar;
        ImageButton insert;

        CarsViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_tv);
            vinTextView = itemView.findViewById(R.id.vin_tv);
            fuelTextView = itemView.findViewById(R.id.fuel_tv);
            plateTextView = itemView.findViewById(R.id.plate_tv);
            tankTextView = itemView.findViewById(R.id.tank_size_tv);
            deleteCar = itemView.findViewById(R.id.del_car_btn);
            insert = itemView.findViewById(R.id.insert_item_btn);

            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onInsertRefueling();
                }
            });
            deleteCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemDelete(getAdapterPosition());
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