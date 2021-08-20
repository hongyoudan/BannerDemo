package com.hyd.bannerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.hyd.bannerdemo.utils.BannerUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hayden
 * @create 2021/8/19
 * @description
 */
public class MainActivity extends AppCompatActivity {

    private Banner mBanner;
    private List<String> mBannerLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBanner = findViewById(R.id.banner);

        //图片地址的集合，实际开发中使用实体类加载数据
        mBannerLists = new ArrayList<>();
        mBannerLists.add("https://img2.baidu.com/it/u=445719670,631292389&fm=15&fmt=auto&gp=0.jpg");
        mBannerLists.add("https://img1.baidu.com/it/u=367814199,1817841556&fm=26&fmt=auto&gp=0.jpg");
        mBannerLists.add("https://img2.baidu.com/it/u=2982865628,2526002410&fm=26&fmt=auto&gp=0.jpg");
        mBannerLists.add("https://img2.baidu.com/it/u=3468384679,3521528174&fm=26&fmt=auto&gp=0.jpg");

        //使用工具类显示图片
        BannerUtil.useBanner(mBanner, getApplicationContext(), mBannerLists, MainActivity.this::OnBannerClick);

    }

    public void OnBannerClick(Object data, int position) {
        //banner 点击事件
        ToastUtils.showShort(data + "点击了：" + position);
    }

}