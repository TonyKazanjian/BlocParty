package api.model;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImagePost {

    //a transfer object for handling calls from the network APIs

    private String imageURL;
    private String imageCaption;
    private String imageId;
    private String imageOwner;
    private long datePosted;


    public ImagePost setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public ImagePost setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
        return this;
    }

    public ImagePost setDatePosted(long datePosted) {
        this.datePosted = datePosted;
        return this;
    }

    public ImagePost setImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }

    public void setImageOwner(String imageOwner) {
        this.imageOwner = imageOwner;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public long getDatePosted() {
        return datePosted;
    }

    public String getImageId() {
        return imageId;
    }

    public String getImageOwner() {
        return imageOwner;
    }

    public ImagePost(String imageURL, String imageCaption) {
        this.imageURL = imageURL;
        this.imageCaption = imageCaption;
    }
}
