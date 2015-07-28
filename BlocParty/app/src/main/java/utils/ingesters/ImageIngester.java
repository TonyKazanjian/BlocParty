package utils.ingesters;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;

import api.model.ImagePost;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImageIngester implements Ingester {

    String TAG = ImageIngester.class.getSimpleName();
    ArrayList<ImagePost> fbImages = new ArrayList<>();
    ImagePost fbPost;

    @Override
    public ArrayList<ImagePost> ingest() {

//    1. Reach out to Facebook SDK and get all image posts

        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{post-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */

                        for (int i = 0; i < response.getJSONArray().length(); i++) {
                            ArrayList<GraphResponse> fbPosts = new ArrayList<GraphResponse>();
                            fbPosts.add(i, response);
                            for (i = 0; i < fbPosts.size(); i++){
                                fbImages.add(i,fbPost);
                            }
                        }
                        Log.d(TAG, "post received");
                    }
                }
        ).executeAsync();
        return fbImages;
        }
    }

//    2. Convert each post into an ImagePost and add it to a return array
//    3. Return that return array
// to convert:
//    for (Post post in FB.Posts()) {
//    ImagePost thisImagePost = new ImagePost(post.imageUrl, post.name, post.username, post.description);
//    returnArray.push(thisImagePost)

