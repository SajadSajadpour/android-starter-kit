package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import com.food.iotsensor.Utility.DialogUtil;

import java.util.ArrayList;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class ImagePageDialog {

   /* private static ImagePageDialog leavePageDialog;
    private static DialogUtil dialog;
    private Context mContext = null;
    private ArrayList imageList = new ArrayList<String>();
    //是否有
    private static boolean isShow = false;

    private static FragmentRequest mRequest;

    public ImagePageDialog(Context context) {
        mContext = context.getApplicationContext();
    }

    public static ImagePageDialog getInstance(Context context) {
        if (leavePageDialog == null) {
            synchronized (ImagePageDialog.class) {
                if (leavePageDialog == null) {
                    leavePageDialog = new ImagePageDialog(context);
                    try {
                        mRequest = (FragmentRequest) context;
                    } catch (ClassCastException e) {
                        throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
                    }
                }
            }
        }
        return leavePageDialog;
    }


    public void show(final Activity activity, final List<HomeListModel.ResultsetBeanXXXX.PopupBean> popupBeans) {
        if (activity != null) {
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_image_page, null);
                DefaultNormalImageBanner banner = (DefaultNormalImageBanner) view.findViewById(R.id.banner);
                banner.setIndicateViewGravity(Gravity.CENTER);
//                banner.setHeightRatio(1350 / 1080.0f);
                banner.setHeightRatio(1800 / 1080.0f); //1080 x 1800
                imageList.clear();
                for (HomeListModel.ResultsetBeanXXXX.PopupBean resultsetBean : popupBeans) {
                    imageList.add(resultsetBean.getImages());
                }

                banner.setDivideTime(3000).setData(imageList).setOnItemClickListener(new OnBannerItemClickListener() {
                    @Override
                    public void onItemClick(final int position, Object o) {
                        if (!popupBeans.get(position).getUrlX().equalsIgnoreCase("")) {
                            WebviewData webviewData = new WebviewData();
                            webviewData.setUrl(popupBeans.get(position).getUrlX());
                            webviewData.setTitle("");
                            WebviewPage.actionStart(activity, webviewData);
                            cancel();
                        } else {
                            if ("in-app".equals(popupBeans.get(position).getJump_type())) {
                                switch (popupBeans.get(position).getInapp_jump()) {
                                    case "inbox":
                                        WalletInboxActivity.actionStart(activity);
                                        cancel();
                                        break;
                                    case "whats-hot":
                                        mRequest.onRequest("tab_hot");
                                        cancel();
                                        break;
                                    case "reward":
                                        RewardsActivity.actionStart(activity);
                                        cancel();
                                        break;
                                    case "history":
                                        TransactionMainActivity.actionStart(activity);
                                        cancel();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                });

                dialog = new DialogUtil(activity, view, true);
                Window dialogWindow = dialog.getWindow();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            //返回true禁止返回键关掉dialog
                            return true;
                        } else {
                            return false;
                        }
                    }

                });
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialog.show();
                ImageView del_dialog = (ImageView) view.findViewById(R.id.del_dialog);
                del_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel();
                    }
                });
            }
        }
    }

    public static void cancel() {
        isShow = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }*/


}
