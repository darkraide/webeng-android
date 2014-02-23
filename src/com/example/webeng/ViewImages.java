package com.example.webeng;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import Adapter.ImagePagerAdapter;
import BaseClasses.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ViewImages extends BaseActivity {
    private static final String PhOTOS = "photos";
    private static final String STATE_POSITION = "STATE_POSITION";

    DisplayImageOptions options;

    ViewPager pager;

    public void onCreate(Bundle savedInstanceState) {
        setScreenName(getResources().getString(R.string.viewimage_screen));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimage);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String[] imageUrls = bundle.getStringArray(PhOTOS);
        int pagerPosition = bundle.getInt(STATE_POSITION, 0);

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ImagePagerAdapter(imageUrls, this.getApplicationContext()));
        pager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }
}
