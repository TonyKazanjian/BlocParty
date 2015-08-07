package utils.ingesters;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.model.ImagePost;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImageIngester implements Ingester {

    String TAG = ImageIngester.class.getSimpleName();
    List<ImagePost> fbImages = new ArrayList<>();
    ImagePost fbImagePost;

    @Override
    public List<ImagePost> ingest() {

//    1. Reach out to Facebook SDK and get all image posts
        Bundle bundle = new Bundle();
        bundle.putString("fields","posts{full_picture,place,message}");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                //"me?fields=feed,home{picture}",
                "me",
                bundle,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        //list of strings representing a JSON string
                        List<String> fbPosts = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d(TAG, jsonObject.toString());
                            for (int i = 0; i< jsonArray.length(); i++){
                                fbPosts.add(response.getJSONArray().getString(i));
                                Log.d(TAG, fbPosts.get(i));
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


