package stefan.com.mk.mycriminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Stefan on 25-Mar-16.
 */
public class DatePickerFragment extends DialogFragment{
    public static final String EXTRA_DATE = "stefan.com.mk.mycriminalintent.date";
    private static final String ARG_DATE = "arg_date";//аргумент
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {//го добива date од CrimeFragment

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.picker_date_fragment, null);
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker) v.findViewById(R.id.date_picker_layout);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_crime_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);//resultCode = Activity.RESULT_OK
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);//со extra ќе му ја прати на CrimeFragment датата што ја селектирал корисникот

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


}
