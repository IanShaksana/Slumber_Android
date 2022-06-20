package com.felix.slumber.dialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

/**
 * Created by Adrian on 5/30/2018.
 */


public class dialog_date extends AppCompatDialogFragment {



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog wasd =new DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT ,(DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        wasd.setButton(DatePickerDialog.BUTTON_POSITIVE,"OK",wasd);
        wasd.setButton(DatePickerDialog.BUTTON_NEGATIVE,"Cancel",wasd);


        // Create a new instance of DatePickerDialog and return it
        return wasd;
    }


}