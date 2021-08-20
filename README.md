# BannerDemo

<p align="left" style="">
  <img src="https://img.shields.io/github/last-commit/hongyoudan/BannerDemo"></img>
	<img src="https://img.shields.io/github/languages/count/hongyoudan/BannerDemo"></img>
<img src="https://img.shields.io/github/stars/hongyoudan/BannerDemo?style=social"></img>
<img src="https://img.shields.io/github/watchers/hongyoudan/BannerDemo?style=social"></img>
</p>

**整理不易，欢迎 `Star` 和 `Fork` ^_^ ，谢谢~~**



## 前言

使用了Android中Banner样式中最常见的方法，适合学习。

这里推荐一位朋友的开源Banner样式：[BannerViewPager](https://gitee.com/zhpanvip/BannerViewPager)



## 项目演示

| <img src="https://img-blog.csdnimg.cn/67bcfdbce5fc4a48acf7935fde381569.gif#pic_center"></img> |
| ------------------------------------------------------------ |



## 项目说明

添加依赖

```xml
<--banner轮播控件-->
implementation 'com.youth.banner:banner:2.1.0'
<--glide图片加载-->
implementation 'com.github.bumptech.glide:glide:4.11.0'
```

编写布局文件

```xml
<com.youth.banner.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_margin="10dp"
        app:banner_radius="10dp"
        app:layout_constraintTop_toTopOf="parent"
        tools:ignore="MissingConstraints"
        tools:layout_editor_absoluteX="3dp" />
```

编写逻辑代码

```java
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
```

这里封装了 Banner工具类以及Glide方法 ，方便后期复用

```java
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
```



**这里使用了`::`顺便一起来了解一下**

**【方法引用的种类】**

双冒号运算符在 Java 8 中被用作方法引用（Method reference），方法引用是与 lambda 表达式相关的一个重要特性。它提供了一种不执行方法的方法。为此，方法引用需要由兼容的函数接口组成的目标类型上下文。

使用 lambda 表达式会创建匿名方法， 但有时候需要使用一个 lambda 表达式只调用一个已经存在的方法（不做其它）， 所以这才有了方法引用！

有四种方法引用：

| 种类                                 | 句法                                       | 例子                                                         |
| ------------------------------------ | ------------------------------------------ | ------------------------------------------------------------ |
| 引用静态方法                         | `*ContainingClass*::*staticMethodName*`    | `Person::compareByAge` `MethodReferencesExamples::appendStrings` |
| 对特定对象的实例方法的引用           | `*containingObject*::*instanceMethodName*` | `myComparisonProvider::compareByName` `myApp::appendStrings2` |
| 对特定类型的任意对象的实例方法的引用 | `*ContainingType*::*methodName*`           | `String::compareToIgnoreCase` `String::concat`               |
| 对构造函数的引用                     | `*ClassName*::new`                         | `HashSet::new`                                               |







