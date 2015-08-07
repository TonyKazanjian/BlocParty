package api.model;

/**
 * Created by tonyk_000 on 7/16/2015.
 */
public class ImagePost {

    //a transfer object

    private String imageURL;
    private String imageName;
    private String imageOwner;
    private String imageCaption;
    private long imageId;
    private long datePosted;


    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageOwner(String imageOwner) {
        this.imageOwner = imageOwner;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageOwner() {
        return imageOwner;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public long getDatePosted() {
        return datePosted;
    }

    public long getImageId() {
        return imageId;
    }

    public ImagePost(String imageURL, String imageName, String imageOwner, String imageCaption, long imageId, long datePosted) {
        this.imageURL = imageURL;
        this.imageName = imageName;
        this.imageOwner = imageOwner;
        this.imageCaption = imageCaption;
        this.imageId = imageId;
        this.datePosted = datePosted;
    }


}
