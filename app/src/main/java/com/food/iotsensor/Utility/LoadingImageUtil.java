package com.food.iotsensor.Utility;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.food.iotsensor.R;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class LoadingImageUtil {
    //
    public static void loading(ImageView image, String url) {
        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .skipMemoryCache(false)
                .into(image);
    }

    //
    public static void loadingPlaceholder(ImageView image, String url) {
        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .error(R.drawable.icon_gray)
                .skipMemoryCache(false)
                .into(image);
    }

    //
    public static void loadingCircle(ImageView image, String url) {

        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .bitmapTransform(new CropCircleTransformation(ActivityUtil.getActivity()))
                .placeholder(R.drawable.login_user_icon)
                .error(R.drawable.login_user_icon)
                .skipMemoryCache(false)//false是缓存头像
                .into(image);
    }

    //
    public static void loadingCircleEdprofile(ImageView image, String url) {

        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .bitmapTransform(new CropCircleTransformation(ActivityUtil.getActivity()))
                .placeholder(R.mipmap.ed_user_icon)
                .error(R.mipmap.ed_user_icon)
                .skipMemoryCache(false)//false是缓存头像
                .into(image);
    }

    //
    public static void loadingLoginIcon(ImageView image, String url) {

        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .bitmapTransform(new CropCircleTransformation(ActivityUtil.getActivity()))
                .placeholder(R.mipmap.icon_head)
                .error(R.mipmap.icon_head)
                .skipMemoryCache(false)//false是缓存头像
                .into(image);
    }

    //
    public static void loadingFillet(ImageView image, String url) {

        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .bitmapTransform(new GlideRoundTransform(ActivityUtil.getActivity(), 10))
                .dontAnimate()
                .into(image);
    }

    //
    public static void loadingDealsFillet(ImageView image, String url) {

        Glide.with(ActivityUtil.getActivity())
                .load(url)
                .error(R.drawable.icon_gray)
                .bitmapTransform(new GlideRoundTransform(ActivityUtil.getActivity(), 10))
                .dontAnimate()
                .skipMemoryCache(false)//false是缓存头像
                .into(image);
    }
}
