package stefan.com.mk.mycriminalintent;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;


/**
 * Created by Stefan on 07-Mar-16.
 */
public class CrimeFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String ARG_CRIME_ID = "crime_id";//extra во самиот fragment
    private static final String SHOW_DIALOG = "show_dialog";
    private static final String SHOW_TIME = "show_time";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mTimeButton;

    public static CrimeFragment newInstance(UUID crimeId) {//со Tab само ми прави newInstance

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {//Мора да е public затоа што ќе биде повикано од тоа Activity што е host на Fragment-от.
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();
        //UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);//тоа crimeId што ќе стигне од CrimeListFragment во CrimeActivitiy, вака го земам
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);//да ги го земе од Activity
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//Овде внатре им правам wiring up Widgets
        View v = inflater.inflate(R.layout.fragment_crime, container, false);//Inflate на XML-от, container is your View's parent, дали да го стави ова inflated view to the view's parent.

        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                //TimePickerFragment tialog = new TimePickerFragment();
                TimePickerFragment tialog = TimePickerFragment.newInstance(mCrime.getTime());
                tialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                tialog.show(manager, SHOW_TIME);
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        //mDateButton.setText(DateFormat.format("EEEE, MMM d, yyyy", mCrime.getDate()));
        //mDateButton.setEnabled(false);//да неможе да се стисне
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());//му го праќа Date на DatePickerFragment
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);//Target е CrimeFragment за да го добие date од DatePickerFragment
                dialog.show(manager, SHOW_DIALOG);
            }
        });


        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());//set the value of mTitle in Crime.java //CharSequence е user input и ќе врати string, и со тоа ја сетира Title.
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);//да го земе EXTRA_DATE од DatePickerFragment
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setTime(date);
            updateTime();
        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.format("dd/MM/yyy", mCrime.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(DateFormat.format("hh:mm:00 a", mCrime.getTime()));
    }
}
