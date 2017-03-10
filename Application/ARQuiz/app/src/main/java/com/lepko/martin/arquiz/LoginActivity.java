package com.lepko.martin.arquiz;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lepko.martin.arquiz.Fragments.Login.LoginFragment;
import com.lepko.martin.arquiz.Fragments.Login.LogoutFragment;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends FragmentActivity {

    private static final String TAG = "LoginActivity";

    private SessionManager sessionManager;

    private FrameLayout fragmentContainer;
    private EditText loginEditText;
    private EditText passwordEditText;
    private LoginFragment loginFragment;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());
        pd = new ProgressDialog(this);

        fragmentContainer = (FrameLayout) findViewById(R.id.login_fragment_container);

        if(fragmentContainer != null) {

            if(savedInstanceState == null) {

                if(sessionManager.isLoggedIn()) {

                    LogoutFragment logoutFragment = new LogoutFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.login_fragment_container, logoutFragment).commit();
                } else {

                    loginFragment = new LoginFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.login_fragment_container, loginFragment).commit();
                }
            }
        }
    }

    public void login(View v) {
        if(loginEditText == null) loginEditText = loginFragment.getUserNameEditText();
        if(passwordEditText == null) passwordEditText = loginFragment.getPasswordEditText();

        if(loginEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT);
            return;
        }

        if(passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT);
            return;
        }

        new LoginAsyncTask().execute(Services.LOGIN_URL(), loginEditText.getText().toString(), passwordEditText.getText().toString());
    }

    public void logout(View v) {
        sessionManager.setLogin(false, null);

        setResult(RESULT_OK);
        finish();
    }

    private void onLoginSuccess(JSONObject response) {
        Toast.makeText(this, "Success login", Toast.LENGTH_LONG).show();

        sessionManager.setLogin(true, response.toString());

        setResult(RESULT_OK);
        finish();
    }

    private void onLoginError() {
        Toast.makeText(this, "No response from server", Toast.LENGTH_LONG).show();
    }

    private void onLoginError(JSONObject response) throws JSONException {
        Toast.makeText(this, response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    private class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Helper.showProgressDialog(pd,"Singing you in...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onLoginSuccess(response);
                    else
                        onLoginError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                onLoginError();
            }

            Helper.dismissProgressDialog(pd);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(4000);
                urlConnection.setConnectTimeout(4000);
                urlConnection.setRequestMethod(Services.METHOD_POST);
                urlConnection.setDoInput(true);

                List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();

                data.add(new AbstractMap.SimpleEntry<>("name_login", params[1]));
                data.add(new AbstractMap.SimpleEntry<>("password_login", params[2]));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(Helper.getQuery(data));
                bw.flush();
                bw.close();
                os.close();

                int respCode = urlConnection.getResponseCode();

                if(respCode == HttpsURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = Helper.getStringFromInputStream(in);
                    Log.d(TAG, result);

                    return result;

                } else return "";

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
