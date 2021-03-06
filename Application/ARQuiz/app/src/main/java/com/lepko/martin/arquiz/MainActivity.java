package com.lepko.martin.arquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lepko.martin.arquiz.Entities.User;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    public static final int LOGIN_STATE_REQUEST = 1;

    private Button loginBtn;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn())
            try {
                checkLoggedIn();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            checkLoggedIn();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void explore(View v) {
        //Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        //Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        //Intent intent = new Intent(MainActivity.this, CloudRecoActivity.class);
        Intent intent = new Intent(MainActivity.this, CompetitionsActivity.class);

        startActivity(intent);
    }

    public void login(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivityForResult(intent, LOGIN_STATE_REQUEST);
    }

    private void checkLoggedIn() throws JSONException {
        if(sessionManager.isLoggedIn()) {
            User user = sessionManager.getUserData();

            if(user != null)
                loginBtn.setText(user.getName());
            else Log.e(TAG,"ERROR no user data");
        } else {
            loginBtn.setText(getString(R.string.STR_SIGN_IN));
        }
    }

    private void onLoggedOut() {
        loginBtn.setText(getString(R.string.STR_SIGN_IN));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOGIN_STATE_REQUEST && resultCode == RESULT_OK) {
            if(sessionManager.isLoggedIn())
                try {
                    checkLoggedIn();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            else
                onLoggedOut();
        }
    }
}
