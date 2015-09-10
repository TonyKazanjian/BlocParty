package utils.ingesters;

import java.util.List;

import api.model.ImagePost;
import api.model.ImagePostFacebookDAO;
import api.model.ImagePostListener;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImageIngester implements ImageIngesterListener {

    String TAG = ImageIngester.class.getSimpleName();
    ImagePost fbImagePost;

    private ImageIngesterListener mListener;

    public ImageIngester(ImageIngesterListener listener) {
        mListener = listener;
    }

    public void ingest() {

        ImagePostFacebookDAO facebookDAO = new ImagePostFacebookDAO();
        facebookDAO.read(new ImagePostListener() {
            @Override
            public void onSuccess(List<ImagePost> imagePosts) {
                mListener.onSuccess(imagePosts);
            }

            @Override
            public void onFailure(String failMessage) {

            }
        });

        }

    @Override
    public void onSuccess(List<ImagePost> imagePosts) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}


