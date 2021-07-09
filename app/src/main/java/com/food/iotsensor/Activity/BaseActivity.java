package com.food.iotsensor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.food.iotsensor.FingerPrint.FingerPrintUtil;
import com.food.iotsensor.InterFaces.FingerCallbackLister;
import com.food.iotsensor.R;
import com.food.iotsensor.SysApplication;
import com.food.iotsensor.Utility.ActivityUtil;
import com.food.iotsensor.Utility.MyLifecycleHandlerUtil;
import com.food.iotsensor.Utility.NetWorkChangUtil;
import com.food.iotsensor.Utility.RemindDialogUtil;
import com.food.iotsensor.Utility.WaitingDialogUtil;
import com.food.iotsensor.ViewUtility.CheckUserDialog;
import com.food.iotsensor.ViewUtility.FingerPrintDialogUtil;
import com.food.iotsensor.ViewUtility.FontTextView;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class BaseActivity extends AppCompatActivity {
    private FontTextView mTitle;
    private RelativeLayout mBack;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.init(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SysApplication.getInstance().addActivity(this);
        ActivityUtil.init(this);
        NetWorkChangUtil.getInstance().openReceiver(this);

        if(MyLifecycleHandlerUtil.isAppForeground){
            MyLifecycleHandlerUtil.isAppForeground=false;
            CheckUserDialog.getInstance().show(this);

            System.out.println("come here 2");

            FingerPrintUtil.getInstance().toFingerPrintLogin(this, new FingerCallbackLister() {
                    @Override
                    public void callBack() {
                        System.out.println("login here here 3");

                        CheckUserDialog.getInstance().cancel();
                        FingerPrintDialogUtil.cancel();
                    }
            });

        }
        // initMessage();
    }


    @Override
        protected void onDestroy() {
        super.onDestroy();
        FingerPrintUtil.getInstance().onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        WaitingDialogUtil.cancel();
        NetWorkChangUtil.getInstance().closeReceiver(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityUtil.init(this);
    }

    //
    public void setupToolbar(String title) {
        mTitle = (FontTextView) findViewById(R.id.second_bar_title);
        mTitle.setText(title);
        mBack = (RelativeLayout) findViewById(R.id.second_bar_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setupToolbar(String title, final View.OnClickListener listener) {
        mTitle = (FontTextView) findViewById(R.id.second_bar_title);
        mTitle.setText(title);
        mBack = (RelativeLayout) findViewById(R.id.second_bar_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

    }


    //
    public void refreshData() {

    }


     private boolean isOpenDialog = false;

    //token
    public void hasWrongToken() {
        final Activity activity = this;

        if (!isOpenDialog && activity != null) {
            isOpenDialog = true;

            RemindDialogUtil.show(this, this.getString(R.string.invalid_token_msg), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isOpenDialog = false;
                    //SplashActivity.actionStart(activity);
                    activity.finish();
                }
            });
        }

    }

    //
    public void getResponseFail() {
        RemindDialogUtil.showEasy(this, getString(R.string.get_data_fail));
        WaitingDialogUtil.cancel();
    }
}
