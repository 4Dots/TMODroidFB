package fourdots.facebooklogin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;


public class MainActivity extends FragmentActivity {

    private static final String USER_SKIPPED_LOGIN_KEY = "user_skipped_login";
    private static final String TAG = "MainFragment";
    private UiLifecycleHelper uiHelper;
    private boolean isResumed = false;

    /*
    private Session.StatusCallback callback = new Session.StatusCallback() {1º1
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    /*/


    private MainFragment mainFragment;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if(savedInstanceState == null)
            {
                mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().add(android.R.id.content,mainFragment).commit();
            }
            else
            {
                mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();
            int backStackSize = manager.getBackStackEntryCount();
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            // check for the OPENED state instead of session.isOpened() since for the
            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.

        }
    }
    */

}
