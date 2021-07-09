package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.food.iotsensor.Adapter.KeyBoardAdapter;
import com.food.iotsensor.Constant;
import com.food.iotsensor.DataManager;
import com.food.iotsensor.InterFaces.KeyBoardCallBackListener;
import com.food.iotsensor.Network.gsonutil.model.NetWorkCallback;
import com.food.iotsensor.Network.model.Logout;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.ACacheUtil;
import com.food.iotsensor.Utility.DialogUtil;
import com.food.iotsensor.Utility.LoadingImageUtil;
import com.food.iotsensor.Utility.SharedPreferencesUtils;
import com.food.iotsensor.Utility.ToastUtil;



/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CheckUserDialog {
    public static CheckUserDialog checkUserDialog;
    private DialogUtil dialog;
    private static StringBuilder pinData;
    private CustomGridLayoutRecyclerView keyboard_review;
    private StaggeredGridLayoutManager layoutManager;
    private KeyBoardAdapter keyBoardAdapter;
    private ImageView image_1, image_2, image_3, image_4, image_5, image_6;
    private Activity activity;

    public static CheckUserDialog getInstance() {
        if (checkUserDialog == null) {
            checkUserDialog = new CheckUserDialog();
        }

        return checkUserDialog;
    }

    private FontTextView nick_name, greetings;
    private ImageView account_head;

    //这个是需要监听点击确定后处理点击事件的
    public void show(Activity mActivity) {
        if (mActivity != null) {
            String token = ACacheUtil.get(mActivity).getAsString(Constant.authToken);
            if (null == dialog && token != null && !TextUtils.isEmpty(token)) {
                this.activity = mActivity;
                pinData = new StringBuilder();

                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_check_user, null);
                nick_name = (FontTextView) view.findViewById(R.id.nick_name);
                greetings = (FontTextView) view.findViewById(R.id.greetings);
                account_head = (ImageView) view.findViewById(R.id.account_head);
                nick_name.setText(mActivity.getString(R.string.hello) + " " + SharedPreferencesUtils.getInstance(activity).getData(Constant.userNickName, ""));
                greetings.setText(SharedPreferencesUtils.getInstance(activity).getData(Constant.userGreeting, ""));
                LoadingImageUtil.loadingLoginIcon(account_head, SharedPreferencesUtils.getInstance(activity).getData(Constant.userIcon, ""));

                keyboard_review = (CustomGridLayoutRecyclerView) view.findViewById(R.id.keyboard_review);
                image_1 = (ImageView) view.findViewById(R.id.image_1);
                image_2 = (ImageView) view.findViewById(R.id.image_2);
                image_3 = (ImageView) view.findViewById(R.id.image_3);
                image_4 = (ImageView) view.findViewById(R.id.image_4);
                image_5 = (ImageView) view.findViewById(R.id.image_5);
                image_6 = (ImageView) view.findViewById(R.id.image_6);
                // setup RecyclerView
                layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                // gridAdapter = new CategoryAdapter(activity, list, imageList, onRequest);
                keyboard_review.setLayoutManager(layoutManager);
                // grid_recyclerview.setAdapter(gridAdapter);
                keyBoardAdapter = new KeyBoardAdapter(activity, new KeyBoardCallBackListener() {
                    @Override
                    public void click(int number) {

                        switch (number) {
                            case 0:
                                if (pinData.length() < 7) {
                                    pinData.append("1");
                                }
                                break;
                            case 1:
                                if (pinData.length() < 7) {
                                    pinData.append("2");
                                }

                                break;
                            case 2:
                                if (pinData.length() < 7) {
                                    pinData.append("3");
                                }

                                break;
                            case 3:
                                if (pinData.length() < 7) {
                                    pinData.append("4");
                                }

                                break;
                            case 4:
                                if (pinData.length() < 7) {
                                    pinData.append("5");
                                }

                                break;
                            case 5:
                                if (pinData.length() < 7) {
                                    pinData.append("6");
                                }

                                break;
                            case 6:
                                if (pinData.length() < 7) {
                                    pinData.append("7");
                                }

                                break;
                            case 7:
                                if (pinData.length() < 7) {
                                    pinData.append("8");
                                }
                                break;
                            case 8:
                                if (pinData.length() < 7) {
                                    pinData.append("9");
                                }
                                break;
                            case 9:
                                String token = ACacheUtil.get(activity).getAsString(Constant.authToken);
                                System.out.println("cancel in check dialog" + token);
                                if (!token.equals("")) {
                                    ACacheUtil.get(activity).put(Constant.authToken, "");
//                                    LoginPageActivity.actionStart(activity);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 1s = 1000ms
//                                            cancel(); // changed to logout
                                            LogOut();
                                            System.out.println("cancel in check dialog here here");
                                        }
                                    }, 1000);
                                }

                                break;
                            case 10:
                                if (pinData.length() < 6) {
                                    pinData.append("0");
                                }
                                break;
                            case 11:
                                if (pinData.length() > 1) {
                                    pinData.deleteCharAt(pinData.length() - 1);
                                } else {
                                    pinData = new StringBuilder();
                                }


                                break;
                        }
                        if (number != 9 && number != 11 && pinData.length() == 6) {
                            //checkPin();
                        }

                        changeImage();

                    }
                });

                keyboard_review.setAdapter(keyBoardAdapter);


                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {


                            return true;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                dialog.show();

            }
        }
    }

    private void changeImage() {
        switch (pinData.length()) {
            case 0:
                image_1.setBackgroundResource(R.mipmap.check_user_input);
                image_2.setBackgroundResource(R.mipmap.check_user_input);
                image_3.setBackgroundResource(R.mipmap.check_user_input);
                image_4.setBackgroundResource(R.mipmap.check_user_input);
                image_5.setBackgroundResource(R.mipmap.check_user_input);
                image_6.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 1:
                image_1.setBackgroundResource(R.mipmap.check_user_output);
                image_2.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 2:
                image_2.setBackgroundResource(R.mipmap.check_user_output);
                image_3.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 3:
                image_3.setBackgroundResource(R.mipmap.check_user_output);
                image_4.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 4:
                image_4.setBackgroundResource(R.mipmap.check_user_output);
                image_5.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 5:
                image_5.setBackgroundResource(R.mipmap.check_user_output);
                image_6.setBackgroundResource(R.mipmap.check_user_input);
                break;
            case 6:
                image_6.setBackgroundResource(R.mipmap.check_user_output);
                break;

        }
    }

    public void cancel() {
        isShow = false;
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {


                // added -- to fix account info once the user change device and do the verification
//                ACacheUtil.get(activity).put(Constant.authToken, "");
//
//                DataManager.AuthTokenHolder = "";
//                DataManager.update_UserInfo = true;
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.authToken, "");
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerAuthToken, "");
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.userIcon, "");
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.userNickName, "");
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.userGreeting, "");

                dialog.dismiss();

            }

        }
        dialog = null;
    }

    //是否有
    private static boolean isShow = false;

    //退出账号
    public void LogOut() {

        Logout.getResponse(new NetWorkCallback<Logout>() {
            @Override
            public void onResponse(Logout response) {
                if (null != response) {
                    System.out.println("login here here util");
                    // commented this line to fix jump to login
//                    cancel();



                    //这是缓存退出再进入不要进缓存的开关
                    ACacheUtil.get(activity).put(Constant.authToken, "");

                    DataManager.AuthTokenHolder = "";
                    DataManager.update_UserInfo = true;
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.authToken, "");
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerAuthToken, "");
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.userIcon, "");
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.userNickName, "");
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.userGreeting, "");

                    //LoginPageActivity.actionStart(activity);
                }
            }

            @Override
            public void onFailure(String message) {
                if (!TextUtils.isEmpty(message)) {
                    ToastUtil.makeShortText(activity, message);
                }
            }
        });
    }
    /*function for checking pin */

    //    int clickcount=0;

//    private void checkPin() {
//        if (DataManager.AuthTokenHolder == null || "".equalsIgnoreCase(DataManager.AuthTokenHolder)) {
//            if (ACacheUtil.get(activity).getAsString(Constant.authToken) == null || ACacheUtil.get(activity).getAsString(Constant.authToken).equalsIgnoreCase("")) {
//                DataManager.AuthTokenHolder = SharedPreferencesUtils.getInstance(activity).getData(Constant.fingerAuthToken);
//            } else {
//                DataManager.AuthTokenHolder = ACacheUtil.get(activity).getAsString(Constant.authToken);
//            }
//        }
//        final String token = DataManager.AuthTokenHolder;
//        WaitingDialogUtil.show(activity);
//        MemberCheckPinModel.getResponse(pinData.toString(), Installations.id(activity), new NetWorkCallback() {
//            @Override
//            public void onResponse(Object response) {
//                final MemberCheckPinModel model = (MemberCheckPinModel) response;
//                if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//
//                    if (model.getCode().equals("1")) {
//                        if (model.getData().getOther_device().equals("Y")) {
//                            SharedPreferencesUtils.getInstance(activity).setData(Constant.toLoginId, model.getData().getMobile_id());
//                            String language = model.getData().getLanguage();
//                            SharedPreferencesUtils.getInstance(activity).setData(Constant.userLocale, language);
//                            RefreshLanguageUtil.getInstance().toRefresh(activity, language);
//                            toNewCheckOtp(activity, model.getData().getMobile_id());
//                        } else {
//                            SharedPreferencesUtils.getInstance(activity).setData(Constant.toLoginId, model.getData().getMobile_id());
//                            String language = model.getData().getLanguage();
//                            SharedPreferencesUtils.getInstance(activity).setData(Constant.userLocale, language);
//                            RefreshLanguageUtil.getInstance().toRefresh(activity, language);
//                            MainActivity.actionStart(activity, model.getData().getMobile_id(), pinData.toString());
//                            activity.finish();
//                        }
//                    }
//                    else if (model.getCode().equals("2")) {
//                        RemindDialogUtil.show(activity, model.getMsg(), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pinData = new StringBuilder();
//                                changeImage();
//                                /*pinData = new StringBuilder();
//                                changeImage();
//                                if (!token.equals("")) {
//                                    if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//                                        cancel();
//                                    } else {
//                                        LogOut();
//                                    }
//                                } else {
//                                    cancel();
//                                }*/
//                            }
//                        });
//                    }
//                    else {
//                        RemindDialogUtil.show(activity, model.getMsg(), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pinData = new StringBuilder();
//                                changeImage();
//                                if (!token.equals("")) {
//                                    if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//                                        cancel();
//                                    } else {
//                                        System.out.println("wrong1");
//                                        LogOut();
//                                    }
//                                } else {
//                                    cancel();
//                                }
//                            }
//                        });
//                    }
//
//
//                }
//                else {
//                    if (model.getCode().equals("1")) {
//                        cancel();
//
//                    }
//                    else if (model.getCode().equals("2")){
//                        RemindDialogUtil.show(activity, model.getMsg(), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pinData = new StringBuilder();
//                                changeImage();
//                                /*pinData = new StringBuilder();
//                                changeImage();
//                                if (!token.equals("")) {
//                                    if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//                                        cancel();
//                                    } else {
//                                        LogOut();
//                                    }
//                                } else {
//                                    cancel();
//                                }*/
//                            }
//                        });
//                    }
//                    else {
//                        RemindDialogUtil.show(activity, model.getMsg(), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pinData = new StringBuilder();
//                                changeImage();
//                                if (!token.equals("")) {
//                                    if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//                                        cancel();
//                                    } else {
//                                        System.out.println("wrong2");
//                                        LogOut();
//                                        // added this function to handle delay to cancel dialog after finish activity
//                                        final Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                // Do something after 1s = 1000ms
//                                                cancel();
//                                                System.out.println("user dialog here here w");
//                                            }
//                                        }, 1000);
//
//                                        /*if(base_activity.equals("home_s")){
//
//                                            clickcount ++;
//                                            if(clickcount >= 5)
//                                            {
//                                                LogOut();
//                                                //first time clicked to do this
//                                                System.out.println("home clicked--");
//                                            }
//                                            else
//                                            {
//                                                int i1=   Integer.parseInt("5");
//                                                int subtract= i1-clickcount;
//
//                                                //check how many times clicked and so on
//                                                System.out.println("home left--" + subtract);
//                                                Toast.makeText(activity,subtract + " attempt left.", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }
//                                        else if(base_activity.equals("login")){
//                                            clickcount ++;
//                                            if(*//*Integer.parseInt(Pin_attempts)*//*clickcount >= 5)
//                                            {
//                                                LogOut();
//                                                //first time clicked to do this
//                                                System.out.println("first clicked");
//                                            }
//                                            else
//                                            {
//                                                int i1= Integer.parseInt(Pin_attempts);// Integer.parseInt("5");
//                                                int subtract= i1 - clickcount;
//
//                                                //check how many times clicked and so on
//                                                System.out.println("first clicked2 left" + subtract);
//                                                Toast.makeText(activity,subtract + " attempt left.", Toast.LENGTH_SHORT).show();
//
//                                                if(subtract == 0){
////                                                    SharedPreferencesUtils.getInstance(activity).setData(Constant.Pin_attempts, "4");
//                                                    LogOut();
//                                                }
//
//                                            }
//                                        }*/
////                                        else {
////                                            System.out.println("inja a");
////                                        }
//
//
//                                    }
//                                } else {
//                                    cancel();
//                                }
//                            }
//                        });
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(String message) {
//                RemindDialogUtil.show(activity, message, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        pinData = new StringBuilder();
//                        changeImage();
//                        if (!token.equals("")) {
//                            if (LoginAgainUtil.getInstance().isLoginAgain(activity)) {
//                                cancel();
//                            } else {
//                                System.out.println("wrong3");
//                                LogOut();
//                            }
//                        } else {
//                            cancel();
//                        }
//                    }
//                });
//            }
//        });
//    }


    /*OTP CHECK*/

//    public void toNewCheckOtp(final Activity activity, final String phone) {
//        RemindDialogUtil.showDel(activity, activity.getString(R.string.continue_login), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CountDownTimerUtil.toGetCode(activity)) {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("hp", phone);
//
//                    SendOtpRequest.getResponse(params, new NetWorkCallback<SendOtpRequest>() {
//                        @Override
//                        public void onResponse(SendOtpRequest response) {
//                            if (null != response) {
//                                if ("1".equals(response.getCode())) {
//                                    VerifyOtpActivity.actionStart(activity, phone, VerifyOtpActivity.NEW_DEVICE);
//                                } else {
//                                    RemindDialogUtil.showEasy(activity, response.getMsg());
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(String message) {
//                            if (!TextUtils.isEmpty(message)) {
//                                RemindDialogUtil.showEasy(activity, message);
//                            }
//                        }
//                    });
//
//                } else {
//                    ToastUtil.makeText(activity, activity.getString(R.string.get_code_time_fail) + " " + CountDownTimerUtil.getCodeTime + " " + activity.getString(R.string.get_code_time_fail1));
//                }
//
//
//            }
//        });
//
//    }
}
