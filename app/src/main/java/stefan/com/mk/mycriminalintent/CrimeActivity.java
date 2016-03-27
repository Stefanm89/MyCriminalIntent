package stefan.com.mk.mycriminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "stefan.com.mk.mycriminalintent.crimeId";

    public static Intent TransferCrimeId(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);//го добива crimeId од CrimeListFragment
        return intent;
    }

    @Override
    protected Fragment createFragment() {//abstract method од SingleFragmentActivity
        //return new CrimeFragment();//кој Fragment да го креира
        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);//го праќа на fragment-от
        return CrimeFragment.newInstance(crimeId);
    }
}
