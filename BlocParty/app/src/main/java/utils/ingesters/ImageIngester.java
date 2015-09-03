package utils.ingesters;

import android.os.Bundle;
import android.util.Log;

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
public class ImageIngester implements ImageIngesterListener {

    String TAG = ImageIngester.class.getSimpleName();
    List<ImagePost> fbImages = null;
    ImagePost fbImagePost;

    private ImageIngesterListener mListener;

    public ImageIngester(ImageIngesterListener listener) {
        mListener = listener;
    }


    public void ingest() {

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
                        List<ImagePost> fbPosts = new ArrayList<>();
                        try {
                            if (response.getError() ==null) {
                                JSONObject jsonObject = response.getJSONObject().getJSONObject("posts");
                                fbImages = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.d(TAG, jsonObject.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //converting from a JSONObject to an ImagePost object - this is what a DAO is for
                                    //called the adapter pattern
                                    if (jsonArray.getJSONObject(i).has("full_picture")) {
                                        String imageURL = jsonArray.getJSONObject(i).getString("full_picture");
                                        String message = jsonArray.getJSONObject(i).getString("message");
                                        fbImagePost = new ImagePost(imageURL, message);
                                        fbImagePost.setImageCaption(message)
                                                .setImageURL(imageURL);
                                        fbPosts.add(fbImagePost);
                                        Log.d(TAG, fbPosts.toString());
                                    }
                                }
                                mListener.onSuccess(fbPosts);
                            } else {
                                mListener.onFailure("You failed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mListener.onFailure("Fuuuuck!");
                        }
                    }
                }
        ).executeAsync();
        }

    @Override
    public void onSuccess(List<ImagePost> imagePosts) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}


