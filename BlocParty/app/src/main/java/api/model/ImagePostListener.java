package api.model;

import java.util.List;

/**
 * Created by tonyk_000 on 9/9/2015.
 */
public interface ImagePostListener {

    public void onSuccess(List<ImagePost> imagePosts);
    public void onFailure(String failMessage);
}
