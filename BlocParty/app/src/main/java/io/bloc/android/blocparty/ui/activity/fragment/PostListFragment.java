package io.bloc.android.blocparty.ui.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.List;

import api.model.ImagePost;
import io.bloc.android.blocparty.R;
import io.bloc.android.blocparty.io.bloc.android.blocparty.ui.adapter.PostAdapter;
import utils.ingesters.ImageIngester;
import utils.ingesters.ImageIngesterListener;

/**
 * Created by tonyk_000 on 7/23/2015.
 */

public class PostListFragment extends Fragment  {

   private List<ImagePost>  fbPostList;
   private ImageIngester ingester;
   private RecyclerView postList;
    private PostAdapter postAdapter;
    private TextView errorView;

    private ImageIngesterListener mListener = new ImageIngesterListener() {
        @Override
        public void onSuccess(List<ImagePost> imagePosts) {
            if (imagePosts != null) {
                postList.setVisibility(View.VISIBLE);
                //errorView.setVisibility(View.GONE);
                PostAdapter postAdapter = new PostAdapter(imagePosts);
                postList.setLayoutManager(new LinearLayoutManager(getActivity()));
                postList.setItemAnimator(new DefaultItemAnimator());
                postList.setAdapter(postAdapter);
            }
            else {
                LoginFragment loginFragment =  new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.placeholder, loginFragment).commit();
                Toast.makeText(getActivity(), "Please login to a social network", Toast.LENGTH_LONG).show();
                postList.setVisibility(View.GONE);
                //errorView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFailure(String failMessage) {

        }
    };

    private CallbackManager callbackManager = new CallbackManager() {
        @Override
        public boolean onActivityResult(int i, int i1, Intent intent) {
            return false;
        }
    };

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        fbPostList = new ArrayList<>();
        postAdapter = new PostAdapter(fbPostList);
        ingester = new ImageIngester(mListener);
        ingester.ingest();


    }

   //TODO: set with linearlayout manager
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_postlist, container,false);
        //errorView = (TextView) view.findViewById(R.id.tv_error_test);
        postList = (RecyclerView)view.findViewById(R.id.rv_postlist);
        postList.setAdapter(postAdapter);
        return view;
    }

}
