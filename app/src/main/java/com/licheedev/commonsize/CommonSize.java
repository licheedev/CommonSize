package com.licheedev.commonsize;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 辅助工具
 */

public class CommonSize {

    /**
     * 获取尺寸资源对应的像素
     *
     * @param resources
     * @param resId
     * @return
     */
    public static float getPx(Resources resources, int resId) {
        return resources.getDimension(resId);
    }

    /**
     * 获取尺寸资源对应的像素
     *
     * @param context
     * @param resId
     * @return
     */
    public static float getPx(Context context, int resId) {
        return getPx(context.getResources(), resId);
    }

    /**
     * 应用TextView字体大小
     *
     * @param textView
     * @param resId
     */
    public static void applyTextSize(TextView textView, int resId) {
        float pixel = getPx(textView.getResources(), resId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixel);
    }

    /**
     * dp转px
     */
    public static float dp2px(Resources resources, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            resources.getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
            context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float px) {
        return (px / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
