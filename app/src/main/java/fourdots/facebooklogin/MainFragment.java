package fourdots.facebooklogin;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener
 {


    private static final String TAG = "MainFragment";
    private UiLifecycleHelper uiHelper;
    private LoginButton loginButton;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        //authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    @Override
    public void onResume() {

        super.onResume();
        uiHelper.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) )
        {
            onSessionStateChange(session, session.getState(), null);
        }

        /*
        if(session!=null)
        {
            Toast.makeText(getActivity(), session.getAccessToken(), Toast.LENGTH_LONG).show();
        }
        /*/



        //mandar el token
        if (session.isOpened())
        {
            //saca el token
            final String token = session.getAccessToken();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doHTTPRequest(token);
                }
            }).start();

            //Toast.makeText(getActivity(), session.getAccessToken(), Toast.LENGTH_LONG).show();
            /*
            URL url = null;
            HttpURLConnection urlConnection=null;
            try
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            finally
            {
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            /*/

        }



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

     /*
    public void onStart()
    {
        super.onStart();
        loginButton.setOnClickListener(this);
    }

    /*
    public void onClick(View view)
    {
        if (view.getId() == R.id.login_button)
        {
            //Session session = Session.getActiveSession();
            Toast.makeText(getActivity(), "Starting ", Toast.LENGTH_LONG).show();
            //Intent i = new Intent(getActivity(),MainActivity2.class);
            //startActivity(i);
        }
    }
    /*/

     private String readStream(InputStream is) {
         try {
             ByteArrayOutputStream bo = new ByteArrayOutputStream();
             int i = is.read();
             while(i != -1) {
                 bo.write(i);
                 i = is.read();
             }
             return bo.toString();
         } catch (IOException e) {
             return "";
         }
     }

     private void doHTTPRequest(String tokenURL)
     {
         ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
         if (networkInfo != null && networkInfo.isConnected())
         {
             try
             {
                 InputStream is = null;
                 URL url = new URL("http://tmo.herokuapp.com/?oauth=" + tokenURL);
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 conn.setReadTimeout(15000);
                 conn.setConnectTimeout(15000);
                 conn.setDoInput(true);
                 conn.connect();
                 int resp = conn.getResponseCode();
                 Log.d("DBG","The response code is:" + resp);
                 is = conn.getInputStream();

                 String response = getStringBR(new BufferedReader(new InputStreamReader(is)));
                 Log.e("DBG TKN",response);
                 Intent i = new Intent(getActivity(), ComprarBono.class);
                 startActivity(i);
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
             finally
             {
             }
         }
         else
         {
             Toast.makeText(getActivity(), "La solicitud no se puede procesar", Toast.LENGTH_LONG).show();
         }
     }
     private String getStringBR(BufferedReader bufferedReader)
     {
         StringBuilder sb = new StringBuilder();
         String line;
         try
         {
             if (bufferedReader != null)
             {
                 while ((line = bufferedReader.readLine()) != null)
                 {
                     sb.append(line);
                 }
                 bufferedReader.close();
             }
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }

         return sb.toString();
     }



     public void onClick(View view)
     {
         if (view.getId() == R.id.authButton)
         {
             Intent i = new Intent(getActivity(), ComprarBono.class);
             startActivity(i);
         }
     }

 }
