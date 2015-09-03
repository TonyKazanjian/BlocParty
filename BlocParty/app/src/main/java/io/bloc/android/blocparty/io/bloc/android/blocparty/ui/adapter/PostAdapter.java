package io.bloc.android.blocparty.io.bloc.android.blocparty.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import api.model.ImagePost;
import io.bloc.android.blocparty.R;


/**
 * Created by tonyk_000 on 8/16/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>{

    //Handles the posts populating in the view

    List<ImagePost> imagePostList;

    public PostAdapter (List<ImagePost> imagePostList){
        this.imagePostList = imagePostList;
    }

    @Override
    public PostAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        //inflates the full view
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_view, viewGroup, false);
        return new PostAdapterViewHolder(inflate);
    }

    //bind action is needed so the user can scroll down to higher indexes
    @Override
    public void onBindViewHolder(PostAdapterViewHolder PostAdapterViewHolder, int position) {
        PostAdapterViewHolder.update(imagePostList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagePostList.size();
    }

    protected class PostAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView message;

        public PostAdapterViewHolder(View postView) {
            super(postView);
            image = (ImageView) postView.findViewById(R.id.iv_post_image);
            message = (TextView) postView.findViewById(R.id.tv_image_caption);
        }

        public void update(ImagePost imagePost){
            Picasso.with(image.getContext()).load(imagePost.getImageURL()).into(image);
        }
    }

}
