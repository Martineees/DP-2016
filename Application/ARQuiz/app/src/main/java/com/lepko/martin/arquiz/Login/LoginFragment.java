package com.lepko.martin.arquiz.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lepko.martin.arquiz.R;

/**
 * Created by Martin on 26.2.2017.
 */

public class LoginFragment extends Fragment {

    private EditText userName;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signin_login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userName = (EditText) getView().findViewById(R.id.loginName);
        password = (EditText) getView().findViewById(R.id.loginPassword);
    }

    public EditText getUserNameEditText() {
        return userName;
    }

    public EditText getPasswordEditText() {
        return password;
    }
}
