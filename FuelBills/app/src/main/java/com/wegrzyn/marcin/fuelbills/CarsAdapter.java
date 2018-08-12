package com.wegrzyn.marcin.fuelbills;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wegrzyn.marcin.fuelbills.databinding.CarCardItemViewBinding;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 28.07.2018.
 * wireamg@gmail.com
 */
public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {

    private static final int GASOLINE = 0;
    private static final int DIESEL = 1;
    private static final int LPG = 2;

    private final Context context;
    private List<Car> carList;
    private ListItemClickListener itemClickListener;

    CarsAdapter(Context context, ListItemClickListener listItemClickListener) {
        this.context = context;
        itemClickListener = listItemClickListener ;    }

    void setCars(List<Car>carList){
        this.carList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarsAdapter.CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.car_card_item_view,parent,false);
        CarCardItemViewBinding binding = DataBindingUtil.bind(view);
        return new CarsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.CarsViewHolder holder, int position) {
        if(carList != null){
            Car car = carList.get(position);
            String name = car.getName();
            if(!name.isEmpty())holder.viewBinding.nameTv.setText(name);
            String vin = car.getVin();
            if(vin!=null) holder.viewBinding.vinTv.setText(vin);
            int fuel = car.getFuelType();
            setFuelType(holder.viewBinding.fuelTv,fuel);
            String plate = car.getPlate();
            if(plate!=null) holder.viewBinding.plateTv.setText(plate);
        }
    }

    @Override
    public int getItemCount() {
        if(carList!=null) return carList.size();
        return 0;
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {

        CarCardItemViewBinding viewBinding;

        CarsViewHolder(CarCardItemViewBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
            viewBinding.insertItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.editItemData(carList.get(getAdapterPosition()).getCarId());
                }
            });
            viewBinding.delCarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemDelete(getAdapterPosition());
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    private void setFuelType(TextView view, int intType){
        switch(intType){
            case GASOLINE:
                view.setText(R.string.gasoline);
                break;
            case DIESEL:
                view.setText(R.string.diesel);
                break;
            case LPG:
                view.setText(R.string.lpg);
                break;

        }
    }
}
