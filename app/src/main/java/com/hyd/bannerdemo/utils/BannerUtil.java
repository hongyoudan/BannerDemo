package com.hyd.bannerdemo.utils;

import android.content.Context;

import com.hyd.bannerdemo.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

/**
 * @author hayden
 * @create 2021/8/19
 * @description
 */
public class BannerUtil {
    public static void useBanner(Banner banner, Context context, List<String> urls, OnBannerListener listener) {

        /**
         * 简单使用：
         * banner.addBannerLifecycleObserver(this)//添加生命周期观察者
         * .setAdapter(new BannerExampleAdapter(DataBean.getTestData()))
         * .setIndicator(new CircleIndicator(this));
         */

        BannerImageAdapter adapter = new BannerImageAdapter<String>(urls) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                GlideImageLoader.getInstance().load(holder.imageView, data);
            }
        };
        banner.setAdapter(adapter)
//                .addBannerLifecycleObserver((LifecycleOwner) context)//添加生命周期观察者
                .setIndicator(new CircleIndicator(context));
        adapter.setOnBannerListener(listener);
    }
}
