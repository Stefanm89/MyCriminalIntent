package stefan.com.mk.mycriminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Stefan on 07-Mar-16.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
