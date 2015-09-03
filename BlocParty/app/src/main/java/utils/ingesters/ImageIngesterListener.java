package utils.ingesters;

import java.util.List;

import api.model.ImagePost;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public interface ImageIngesterListener {

    public void onSuccess(List<ImagePost> imagePosts);
    public void onFailure(String failMessage);
}
