package stefan.com.mk.mycriminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*To create a singleton, you create a class with a private constructor and a get() method. If the instance
already exists, then get() simply returns the instance. If the instance does not exist yet, then get() will
        call the constructor to create it.*/

/**
 * Created by Stefan on 07-Mar-16.
 */
public class CrimeLab {//Singleton class which allows only one instance of itself to be created

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;//листа од Crime.java

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();//објект од класата Crime
            crime.setTitle("Crime #" + i);//Да стави име на секое
            crime.setSolved(i % 2 == 0);//Секое второ
            mCrimes.add(crime);//да го add-не
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }//getter за mCrimes

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
