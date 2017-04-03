package com.lepko.martin.arquiz.Fragments.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lepko.martin.arquiz.AsyncTasks.PostAsyncTask;
import com.lepko.martin.arquiz.AsyncTasks.SimpleAsyncTask;
import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Utils.Helper;
import com.lepko.martin.arquiz.Utils.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 26.2.2017.
 */

public class SignUpFragment extends Fragment {

    private EditText userName;
    private EditText password;
    private EditText passwordRepeat;

    private Button signUpBtn;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userName = (EditText) getView().findViewById(R.id.loginName);
        password = (EditText) getView().findViewById(R.id.loginPassword);
        passwordRepeat = (EditText) getView().findViewById(R.id.loginPasswordRepeat);

        signUpBtn = (Button) getView().findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration(view);
            }
        });

        pd = new ProgressDialog(getContext());
    }

    public void registration(View v) {
        if(userName.getText().length() < 1) {
            Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.getText().length() < 1) {
            Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwordRepeat.getText().length() < 1) {
            Toast.makeText(getContext(), "Password repeat is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.getText().equals(passwordRepeat.getText())) {
            Toast.makeText(getContext(), "Password repeat must equal", Toast.LENGTH_SHORT).show();
            return;
        }

        List<AbstractMap.SimpleEntry<String, String>> data = new ArrayList<>();
        data.add(new AbstractMap.SimpleEntry<>("name_login", "" + userName.getText()));
        data.add(new AbstractMap.SimpleEntry<>("password_login", "" + password.getText()));

        try {
            new RegistrationAsyncTask().execute(Services.REGISTRATION_URL(), Helper.getQuery(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void onRegistrationSuccess(JSONObject response) throws JSONException {
        Toast.makeText(getContext(), "Registration was successful", Toast.LENGTH_LONG).show();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    private void onRegistrationError(JSONObject response) throws JSONException {
        Toast.makeText(getContext(), response.getString("error_msg"), Toast.LENGTH_LONG).show();
    }

    private void onRegistrationError() {
        Toast.makeText(getContext(), "No response from server", Toast.LENGTH_LONG).show();
    }

    private class RegistrationAsyncTask extends PostAsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Helper.showProgressDialog(pd, "Registration ...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null && !s.isEmpty()) {
                try {
                    JSONObject response = Helper.string2JSON(s);
                    if(!response.getBoolean("error"))
                        onRegistrationSuccess(response);
                    else
                        onRegistrationError(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "JSON error", Toast.LENGTH_LONG).show();
                }

            } else {
                onRegistrationError();
            }

            pd.dismiss();
        }
    }

}
