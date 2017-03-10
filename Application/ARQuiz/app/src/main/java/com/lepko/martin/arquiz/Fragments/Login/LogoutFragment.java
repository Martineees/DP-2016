package com.lepko.martin.arquiz.Fragments.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lepko.martin.arquiz.Entities.User;
import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Utils.SessionManager;

import org.json.JSONException;

/**
 * Created by Martin on 26.2.2017.
 */

public class LogoutFragment extends Fragment {

    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signout_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sessionManager = new SessionManager(getContext());

        try {
            User user = sessionManager.getUserData();

            TextView userName = (TextView) getView().findViewById(R.id.userEmail);
            userName.setText(user.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
