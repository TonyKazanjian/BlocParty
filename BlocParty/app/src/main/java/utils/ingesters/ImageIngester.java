package utils.ingesters;

import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.model.ImagePost;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImageIngester implements Ingester {

    String TAG = ImageIngester.class.getSimpleName();
    List<ImagePost> fbImages = new ArrayList<>();
    ImagePost fbImagePost;
    String appId = FacebookSdk.getApplicationId();
    String appSecret = "261194b80ec758edb5bbd661b1b371ce";
    String accessTokenString = "CAACEdEose0cBAIlL6JWby7W0fnxGebuMW55giloSpChF2wZBbiwCPO48sTZCJNwBHXtVUg2NTcGZAfjWI5ZAuKhq9tSYDa5A5oDb0aIHDrN5ZAgZBlJPG0409UlhmK53JplDUCLZAamaWyUsc8GYH22F2LxOtXjNgHMufSFmnFhs1kimaLTe4A6SFrpEKbluYtquvqTKTdEDfjqtAGlz5A41y0ts59sgAQZD";
    String commaSeparetedPermissions = "user_friends,user_about_me,user_photos,user_posts";
    String userId = "10153258368409184";
    public List<ImagePost> ingest4j(){
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId,appSecret);
        AccessToken accessToken = new AccessToken(accessTokenString);
        facebook.setOAuthAccessToken(accessToken);

//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthAppId(appId)
//                .setOAuthAppSecret(appSecret)
//                .setOAuthAccessToken(facebook.getOAuthAccessToken().toString())
//                .setOAuthPermissions(commaSeparetedPermissions);
//        FacebookFactory ff = new FacebookFactory(cb.build());
        try {
            ResponseList<Post> feed = facebook.getHome();
            Log.d(TAG, feed.toString());
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ImagePost> ingest() {

//    1. Reach out to Facebook SDK and get all image posts
        Bundle bundle = new Bundle();
        bundle.putString("fields", "posts.limit(10){message,full_picture,object_id}");
        new GraphRequest(
                com.facebook.AccessToken.getCurrentAccessToken(),
                "me",
                bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        //list of strings representing a JSON string
                        List<String> fbPosts = new ArrayList<>();
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d(TAG, jsonObject.toString());
                            for (int i = 0; i< jsonArray.length(); i++){
                                fbPosts.add(jsonArray.getString(i));
                                fbPosts.add(response.getJSONArray().getString(i));
                                Log.d(TAG, fbPosts.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            for (int i = 0; i < fbPosts.size(); i++){
                                fbImages.add(i,fbImagePost);
                                Log.d(TAG, "added to ImagePost list");
                            }
                        Log.d(TAG, "post received");
//                        //TODO: find out why list is coming up empty
                        Log.d(TAG,fbPosts.toString());
                        Log.d(TAG,fbImages.toString());
                    }
                }
        ).executeAsync();
        return fbImages;
        }
    }


