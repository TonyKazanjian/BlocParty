package api.model;

/**
 * Created by tonyk_000 on 7/29/2015.
 */
public interface ImagePostDAO {

    //creates a new object in the datasource based on the transfer object passed in
    public ImagePost create(ImagePost transferObject); //model that's used to be transfered into the database. Models what the data is gonna be in the database

    //update an existing object in the datasource based on the transfer object passed in
    public boolean update(ImagePost transferObject);

    //retrive an object from the datasource
    public void read(ImagePostListener listener);

    //delete an object from the datasource
    public boolean del();

}
