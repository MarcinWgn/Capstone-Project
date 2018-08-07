package com.wegrzyn.marcin.fuelbills;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Marcin Węgrzyn on 03.08.2018.
 * wireamg@gmail.com
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    OnDatePicListener mCallback;

    public interface OnDatePicListener {
        public void onDatePick(String date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String y = String.valueOf(year);
        String m;
        String d;
        String tmp = String.valueOf(month+1);
        if(tmp.length()==1) m = "0"+tmp;
        else m = tmp;
        tmp = String.valueOf(dayOfMonth);
        if(tmp.length()==1) d = "0"+tmp;
        else d = tmp;
        String date = y+"-"+m+"-"+d;
        mCallback.onDatePick(date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnDatePicListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
