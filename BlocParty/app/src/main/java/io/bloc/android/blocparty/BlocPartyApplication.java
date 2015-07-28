package io.bloc.android.blocparty;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import api.model.DataSource;

/**
 * Created by tonyk_000 on 7/27/2015.
 */
public class BlocPartyApplication extends Application {
    public static BlocPartyApplication getSharedInstance() {
        return sharedInstance;
    }

    // #2
    public static DataSource getSharedDataSource() {
        return BlocPartyApplication.getSharedInstance().getDataSource();
    }

    private static BlocPartyApplication sharedInstance;
    private DataSource dataSource;

    // #3
    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource();
        // #1
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

// #2
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
