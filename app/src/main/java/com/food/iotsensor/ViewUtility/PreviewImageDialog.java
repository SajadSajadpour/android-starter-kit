package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;
import com.food.iotsensor.Utility.LoadingImageUtil;

import java.util.ArrayList;

import github.chrisbanes.photoview.HackyViewPager;
import github.chrisbanes.photoview.PhotoView;


/**
 * dialog
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class PreviewImageDialog {
    public static PreviewImageDialog previewImageDialog;
    private DialogUtil dialog;
    private Activity activity;
    ArrayList<String> indicatorList = new ArrayList<>();

    public static PreviewImageDialog getInstance() {
        if (previewImageDialog == null) {
            previewImageDialog = new PreviewImageDialog();
        }

        return previewImageDialog;
    }

    Window dialogWindow;

    View view;

    //这个是需要监听点击确定后处理点击事件的
    public void show(Activity mActivity, final ArrayList<String> imageList, final int position) {
        if (mActivity != null && imageList.size() > 0) {
            activity = mActivity;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_preview_image, null);
            dialog = new DialogUtil(activity, view, true);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        if (dialogWindow != null) {
                            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                            dialogWindow.getDecorView().setSystemUiVisibility(uiOptions);
                        }
                        //返回true禁止返回键关掉dialog
                        return false;
                    } else {
                        return false;
                    }
                }

            });
            dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            dialogWindow.getDecorView().setSystemUiVisibility(uiOptions);

            HackyViewPager viewPager = (HackyViewPager) view.findViewById(R.id.view_pager);

            viewPager.setAdapter(new SamplePagerAdapter(imageList));


            viewPager.setCurrentItem(position, false);


            LinearLayout circleIndicator = (LinearLayout) view.findViewById(R.id.ll_image_gallery_circle_indicator);

            indicatorList.clear();
            for (int i = 0; i < imageList.size(); i++) {
                ImageView myImage = new ImageView(activity);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
                lp.setMargins(5, 5, 5, 5);
                myImage.setLayoutParams(lp);
                int counter = i + 1;
                counter = 5000 + counter;
                if (i == position) {
                    myImage.setImageResource(R.drawable.dot_graywhite);
                } else {
                    myImage.setImageResource(R.drawable.dot_gray);
                }
                myImage.setId(counter);
                circleIndicator.addView(myImage);
                indicatorList.add(String.valueOf(counter));

            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int counterID = 5000 + (position + 1);
                    updateIndicator(view, imageList, counterID);


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            dialog.show();
        }
    }

    private void updateIndicator(View view, ArrayList<String> imageList, int counterID) {
        int arraySize = imageList.size();

        for (int i = 1; i <= arraySize; i++) {
            int imageID = activity.getResources().getIdentifier(String.valueOf(indicatorList.get(i - 1)), "id", activity.getPackageName());
            ImageView imageView = (ImageView) view.findViewById(imageID);
            if (counterID == imageID) {
                imageView.setImageResource(R.drawable.dot_graywhite);
            } else {
                imageView.setImageResource(R.drawable.dot_gray);
            }

        }
    }

    public void cancel() {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                if (dialogWindow != null) {
                    int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                    dialogWindow.getDecorView().setSystemUiVisibility(uiOptions);
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        dialog.dismiss();
                        dialog = null;
                    }
                }, 100);
                //

            }
        }

    }

    class SamplePagerAdapter extends PagerAdapter {
        private ArrayList<String> imageList = new ArrayList<>();


        private SamplePagerAdapter(ArrayList<String> imageList) {
            this.imageList = imageList;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            LoadingImageUtil.loading(photoView, imageList.get(position));

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreviewImageDialog.getInstance().cancel();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
