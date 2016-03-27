package stefan.com.mk.mycriminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
//abstract cannot be instantiated and require subclasses to provide implementations for the abstract methods.
/**
 * Created by Stefan on 07-Mar-16.
 */
public abstract class SingleFragmentActivity extends FragmentActivity { //Ставам support library за FragmentActivity
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();//to add a fragment to an activity in code, you make explicit calls to the activity's FragmentManager.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);//Тука го креирам fragment-от кој што го барам по container што е моментално во activity_fragment.

        if (fragment == null) {
            //fragment = new CrimeFragment();//кој Fragment да го креира
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }
}
