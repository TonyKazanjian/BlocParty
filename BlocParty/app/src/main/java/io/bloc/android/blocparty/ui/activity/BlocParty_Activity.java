package io.bloc.android.blocparty.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;

import io.bloc.android.blocparty.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by tonyk_000 on 6/21/2015.
 */
public class
        BlocParty_Activity extends FragmentActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "EMBNCj1Iv4NxCOdTHpNZm1rDo";
    private static final String TWITTER_SECRET = "kditozgdp19bQnh3AzNpDf0eJ7ZNfjXU4Drpa37KTBvC5QdqnI";
    private static final String INSTAGRAM_KEY = "db91bf03212344ff89f2cc6995088cc0";
    private static final String INSTAGRAM_SECRET = "714c5647104f43149915207c3d188761";
    private static final Token EMPTY_TOKEN = null;


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
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null && !fragment.isVisible() && !fragment.isAdded()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //getting instagram access token
        InstagramService service = new InstagramAuthService()
                .apiKey(INSTAGRAM_KEY)
                .apiSecret(INSTAGRAM_SECRET)
                .callback("http://localhost:8080/BasicWebDemo/handleInstagramToken/")
                .build();
        //getting access token
        String authorizationUrl = service.getAuthorizationUrl(null);
        Verifier verifier = new Verifier("verifier you get from the user");
        Token accessToken = service.getAccessToken(null, verifier);
        //generate instagram object
        Instagram instagram = new Instagram(accessToken);
        setContentView(R.layout.activity_blocparty);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
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
