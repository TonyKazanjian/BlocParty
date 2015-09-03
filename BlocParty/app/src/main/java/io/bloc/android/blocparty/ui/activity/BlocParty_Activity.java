package io.bloc.android.blocparty.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import io.bloc.android.blocparty.R;
import io.bloc.android.blocparty.ui.activity.fragment.LoginFragment;
import io.bloc.android.blocparty.ui.activity.fragment.PostListFragment;

/**
 * Created by tonyk_000 on 6/21/2015.
 */
public class
        BlocParty_Activity extends FragmentActivity {

    LoginFragment loginFragment = new LoginFragment();
    PostListFragment postListFragment = new PostListFragment();

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    CallbackManager callbackManager = new CallbackManager() {
        @Override
        public boolean onActivityResult(int i, int i1, Intent intent) {
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.placeholder);
        if (fragment != null && !fragment.isVisible() && !fragment.isAdded()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_blocparty);
        callbackManager = CallbackManager.Factory.create();
        getSupportFragmentManager().beginTransaction().replace(R.id.placeholder,loginFragment).commit();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        String TAG = BlocParty_Activity.class.getSimpleName();
                        Log.d(TAG, "error");
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
