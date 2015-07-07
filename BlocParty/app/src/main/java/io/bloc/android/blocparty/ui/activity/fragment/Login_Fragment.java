package io.bloc.android.blocparty.ui.activity.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.bloc.android.blocparty.R;
import io.fabric.sdk.android.Fabric;
import utils.InstagramApp;
import widgets.ToggleButton;


/**
 * Created by tonyk_000 on 6/23/2015.
 */
public class Login_Fragment extends Fragment {

    TwitterLoginButton twitterLoginButton;
    LoginButton facebookLogin;
    ImageButton instagramLoginButton;
    InstagramApp mApp;
    widgets.ToggleButton facebookToggle;
    widgets.ToggleButton twitterToggle;
    widgets.ToggleButton instagramToggle;

    private static final String TWITTER_KEY = "EMBNCj1Iv4NxCOdTHpNZm1rDo";
    private static final String TWITTER_SECRET = "kditozgdp19bQnh3AzNpDf0eJ7ZNfjXU4Drpa37KTBvC5QdqnI";
    private static final String INSTAGRAM_KEY = "db91bf03212344ff89f2cc6995088cc0";
    private static final String INSTAGRAM_SECRET = "714c5647104f43149915207c3d188761";
    private static final String CALLBACK_URL = "http://localhost:8080/BasicWebDemo/handleInstagramToken/";
    private CallbackManager callbackManager = new CallbackManager() {
        @Override
        public boolean onActivityResult(int i, int i1, Intent intent) {
            return false;
        }
    };

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //getting the key hash for Facebook
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "io.bloc.android.blocparty",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this.getActivity(), new Twitter(authConfig));

        mApp = new InstagramApp(getActivity(), INSTAGRAM_KEY,
                INSTAGRAM_SECRET, CALLBACK_URL);
        mApp.setListener(listener);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_activity, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        facebookToggle = (widgets.ToggleButton) view.findViewById(R.id.facebook_toggle);
        instagramToggle = (widgets.ToggleButton) view.findViewById(R.id.instagram_toggle);
        twitterToggle = (widgets.ToggleButton) view.findViewById(R.id.twitter_toggle);

        //for twitter
        twitterLoginButton = (TwitterLoginButton) view.findViewById(R.id.twitter_login_button);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d("Twitter login: ", "working");
                twitterToggle.setToggleOn();
            }

            @Override
            public void failure(TwitterException exception) {
                // ... do something
            }
        });

        twitterToggle.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                twitterLoginButton.callOnClick();
            }
        });

        //for facebook
        facebookLogin = (LoginButton) view.findViewById(R.id.facebook_login);
        facebookLogin.setReadPermissions("user_friends");
        // If using in a fragment
        facebookLogin.setFragment(Login_Fragment.this);
        // Callback registration
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookToggle.setToggleOn();
            }

            @Override
            public void onCancel() {
                facebookToggle.setToggleOff();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        facebookToggle.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                facebookLogin.callOnClick();
            }
        });

        // for instagram
        instagramLoginButton = (ImageButton) view.findViewById(R.id.instagram_login_button);

        instagramLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApp.hasAccessToken()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity());
                    builder.setMessage("Disconnect from Instagram?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            mApp.resetAccessToken();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    mApp.authorize();
                }
            }
        });

        instagramToggle.setOnToggleChanged(new ToggleButton.OnToggleChanged() {

            @Override
            public void onToggle(boolean on) {
               instagramLoginButton.callOnClick();
            }

        });
        return view;
    }
    InstagramApp.OAuthAuthenticationListener listener = new InstagramApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            instagramToggle.setToggleOn();
        }

        @Override
        public void onFail(String error) {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
