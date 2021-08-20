package com.hyd.bannerdemo.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hyd.bannerdemo.CircleImageView;
import com.hyd.bannerdemo.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * @author hayden
 * @create 2021/8/19
 * @description
 */
public class GlideImageLoader implements ImageLoader {

    private static GlideImageLoader INSTANCE;
    private int default_round = R.drawable.default_round;
    private int default_circle = R.drawable.default_circle;
    private int default_image = R.drawable.default_image;

    private GlideImageLoader() {
    }

    public static synchronized GlideImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (GlideImageLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlideImageLoader();
                }
            }
        }
        return INSTANCE;
    }

    public Glide getGlide(Context context) {
        return Glide.get(context);
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl) {
        load(imageView, imageUrl, default_image);
    }

    @Override
    public void load(@NonNull ImageView imageView, int imageUrl) {
        load(imageView, imageUrl, default_image);
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl, @DrawableRes int defaultImage) {
        if (defaultImage == 0) {
            defaultImage = default_image;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage);
        loadReal(imageView, imageUrl, options);
    }

    @Override
    public void load(@NonNull ImageView imageView, int imageUrl, @DrawableRes int defaultImage) {
        if (defaultImage == 0) {
            defaultImage = default_image;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage);
        loadReal(imageView, imageUrl, options);
    }

    public void load(@NonNull ImageView imageView, String imageUrl, Drawable defaultImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage);
        loadReal(imageView, imageUrl, options);
    }

    public void load(int resId, ImageView imageView, String imageUrl) {
        if (resId == 0) {
            resId = default_image;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(resId)
                .error(resId);
        loadReal(imageView, imageUrl, options);
    }


    public void loadBlur(ImageView imageView, String imageUrl) {
        loadBlur(imageView, imageUrl, 25, 1);
    }

    @Override
    public void loadBlur(@NonNull ImageView imageView, String imageUrl, int radius, int sampling) {
        int resId = 0;
        if (radius == 0) {
            resId = default_image;
        } else {
            resId = default_round;
        }
        RequestOptions requestOptions = bitmapTransform(new BlurTransformation(radius, sampling))
                .placeholder(resId)
                .error(resId);
        loadReal(imageView, imageUrl, requestOptions);
    }

    public void loadCircle(@NonNull ImageView imageView, String imageUrl,int defaultImage){
        if(0==defaultImage){
            defaultImage=default_image;
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage)
                .dontAnimate()
                .transform(new CircleCrop());
        loadReal(imageView, imageUrl, requestOptions);
    }


    @Override
    public void loadCircle(@NonNull ImageView imageView, String imageUrl) {
        loadCircle(imageView,imageUrl,0);
    }

    @Override
    public void loadRound(@NonNull ImageView imageView, String imageUrl, int radius) {
        RequestOptions options = bitmapTransform(new RoundedCorners(radius))
                .placeholder(default_round)
                .error(default_round);
        loadReal(imageView, imageUrl, options);
    }

    public void loadTopRounded(ImageView imageView, String imageUrl, int radius) {
        loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.TOP);
    }

    public void loadBottomRounded(ImageView imageView, String imageUrl, int radius) {
        loadRounded(imageView, imageUrl, radius, 0, RoundedCornersTransformation.CornerType.BOTTOM);
    }

    public void loadRounded(ImageView imageView, String imageUrl, int radius, int margin,
                            RoundedCornersTransformation.CornerType cornerType) {
        RequestOptions options = bitmapTransform(new RoundedCornersTransformation(radius, margin, cornerType))
                .placeholder(R.drawable.default_round)
                .error(R.drawable.default_round);
        loadReal(imageView, imageUrl, options);
    }

    @Override
    public void load(@NonNull ImageView imageView, String imageUrl, Transformation<Bitmap> transformation) {
        boolean circle = false;
        int radius = 0;
        if (transformation instanceof CustomGlideTransform) {
            CustomGlideTransform customGlideTransform = (CustomGlideTransform) transformation;
            circle = customGlideTransform.circle;
            radius = customGlideTransform.radius;
        }
        RequestOptions options = bitmapTransform(transformation)
                .placeholder(circle ? default_circle : (radius == 0 ? default_image : default_round))
                .error(circle ? default_circle : (radius == 0 ? default_image : default_round));
        loadReal(imageView, imageUrl, options);
    }

    private void loadReal(@NonNull ImageView imageView, String imageUrl, RequestOptions options) {
        if (imageUrl == null) {
            return;
        }
        if (imageView instanceof CircleImageView) {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        }

    }

    private void loadReal(@NonNull ImageView imageView, int imageUrl, RequestOptions options) {
        if (imageUrl == 0) {
            return;
        }
        if (imageView instanceof CircleImageView) {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        }

    }

    public void loadWrapHeight(@NonNull final ImageView imageView, final String imageUrl) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap,
                                                @Nullable Transition<? super Bitmap> transition) {
                        float ratio = 1F * bitmap.getHeight() / bitmap.getWidth();
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.height = (int) (layoutParams.width * ratio);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void loadBitmap(Context context, String url, RequestOptions options, final LoadCallback callback) {
        Glide.with(context).asBitmap().load(url).apply(options)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        if (callback != null) {
                            callback.onLoadSuccess(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        if (callback != null) {
                            callback.onLoadFailure();
                        }
                    }
                });
    }

    public void loadBitmap(Context context, String url, final LoadCallback callback) {
        Glide.with(context).asBitmap().load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        if (callback != null) {
                            callback.onLoadSuccess(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        if (callback != null) {
                            callback.onLoadFailure();
                        }
                    }
                });
    }

    public void loadBlurBitmap(@NonNull Context context, String imageUrl, int radius, int sampling, final LoadCallback callback) {
        RequestOptions requestOptions = bitmapTransform(new BlurTransformation(radius, sampling));
        loadBitmap(context, imageUrl, requestOptions, callback);
    }

    public void loadBlurBitmap(@NonNull Context context, int imageRes, int radius, int sampling, final LoadCallback callback) {
        RequestOptions requestOptions = bitmapTransform(new BlurTransformation(radius, sampling));
        Glide.with(context).asBitmap().load(imageRes).apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        if (callback != null) {
                            callback.onLoadSuccess(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        if (callback != null) {
                            callback.onLoadFailure();
                        }
                    }
                });
    }

    public void loadImage(Context context, String uri, int width, int height, final LoadCallback callback) {
        Glide.with(context).asBitmap().load(uri).into(new SimpleTarget<Bitmap>(width, height) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (callback != null) {
                    callback.onLoadSuccess(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (callback != null) {
                    callback.onLoadFailure();
                }
            }
        });
    }

    public void loadResImage(ImageView imageView, Integer resId) {
        Glide.with(imageView.getContext()).load(resId).into(imageView);
    }

    public interface LoadCallback {
        void onLoadSuccess(Bitmap bitmap);

        void onLoadFailure();
    }
}
