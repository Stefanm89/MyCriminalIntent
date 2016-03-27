package stefan.com.mk.mycriminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stefan on 07-Mar-16.
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int itemPosition;

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {//правам ViewHolder што се вика CrimeHolder
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public void bindCrime(Crime crime) {//им доделувам што да прават
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        public CrimeHolder(View itemView) {//тука им правам wiring up на widgets. Ова е constructor
            super(itemView);
            itemView.setOnClickListener(this);//за да можеш да стиснеш
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        @Override
        public void onClick(View v) {
            //Intent i = new Intent(getActivity(), CrimeActivity.class);//Вака ја повикува CrimeActivity
            itemPosition = mCrimeRecyclerView.getChildAdapterPosition(v);//за да се смени само едно Crime и да се лоадира само тоа
            Intent intent = CrimeActivity.TransferCrimeId(getActivity(), mCrime.getId());//му праќам од CrimeListFragment на CrimeActivity и му го праќам ID
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {//во Adapter-от го ставам ViewHolder-от.
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }//Ова е constructor

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {//Креирам view за CrimeHolder со onCreateViewHolder
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);//Тука го bind-увам holder-от со crime, за да прикаже што да прават копчињата сетирани погоре.

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//мора да има LinearLayoutManager за да работи, како да ги нареди TextView-ата
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
           mAdapter.notifyItemChanged(itemPosition);//за да се смени само едно Crime и да се лоадира само тоа
            }
        }
    }

