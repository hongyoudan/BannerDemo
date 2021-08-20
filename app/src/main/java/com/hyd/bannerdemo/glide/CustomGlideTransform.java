package com.hyd.bannerdemo.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * @author hayden
 * @create 2021/8/19
 * @description
 */
public class CustomGlideTransform extends BitmapTransformation {
	private Paint boarderPaint;
	public final boolean circle;
	public final int radius;
	public final int stroke;
	
	public CustomGlideTransform(boolean circle, int radius, int stroke, int strokeColor) {
		this.circle = circle;
		this.radius = radius;
		this.stroke = stroke;

		if (strokeColor == 0) {
			strokeColor = Color.parseColor("#CECECE");
		}
		boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		boarderPaint.setColor(strokeColor);
		boarderPaint.setStyle(Paint.Style.STROKE);
		boarderPaint.setStrokeWidth(stroke);
	}
	
	@Override
	protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
		Bitmap resultBitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
		if (circle) {
			return circleCrop(pool, toTransform);
		} else if (radius > 0) {
			return roundCrop(pool, toTransform, outWidth, outHeight);
		}
		return resultBitmap;
	}
	
	private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
		if (source == null) return null;
		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;
		Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
		Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		float r = size / 2f;
		canvas.drawCircle(r, r, r - stroke, paint);
		
		if (stroke > 0) {
			//绘制boarder
			canvas.drawCircle(r, r, r - stroke, boarderPaint);
		}
		
		return result;
	}
	
	private Bitmap roundCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
		if (source == null) return null;
		
		// Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
		int x = (source.getWidth() - outWidth) / 2;
		int y = (source.getHeight() - outHeight) / 2;
		Bitmap squared = Bitmap.createBitmap(source, x, y, outWidth, outHeight);
		
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		RectF rectF = new RectF(stroke, stroke, outWidth - stroke, outHeight - stroke);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		
		if (stroke > 0) {
			//绘制boarder
			canvas.drawRoundRect(rectF, radius, radius, boarderPaint);
		}
		
		return result;
	}
	
	@Override
	public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
	
	}
}
