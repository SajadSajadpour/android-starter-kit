package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.food.iotsensor.InterFaces.PinOnClickListener;
import com.food.iotsensor.InterFaces.TranOnClickListener;
import com.food.iotsensor.Network.model.WebviewData;
import com.food.iotsensor.R;
import com.food.iotsensor.ViewUtility.FontTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class RemindDialogUtil {
    private static DialogUtil dialog;

    //
    public static void show(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_remind_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            textView.setText(connent);
            if (!isShow) {
                try {
                    if (dialog != null) {
                        cancel();
                    }
                    isShow = true;
                    dialog = new DialogUtil(activity, view, true);
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {

                                isShow = false;
                                listener.onClick(ok);
                                //true dialog
                                return false;
                            } else {
                                isShow = false;
                                return false;
                            }
                        }

                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isShow = false;
                            listener.onClick(v);
                           cancel();

                        }
                    });
                    dialog.show();

                    }
                catch (Exception ex){
                    System.out.println("RemindDialogUtil exception" + ex);
                }
            }
        }
    }
    public static void show_(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_remind_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            TextView title_tv = view.findViewById(R.id.title_tv);
            title_tv.setVisibility(View.GONE);
//            textView.setText(connent);
            textView.setText(Html.fromHtml(connent));
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            listener.onClick(ok);
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();

                    }
                });
                dialog.show();
            }
        }
    }


    //这个是需要监听点击确定后处理点击事件的
    public static void showNotBack(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_remind_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            textView.setText(connent);
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            //返回true禁止返回键关掉dialog
                            return true;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();

                    }
                });
                dialog.show();
            }
        }
    }



    public static void cancel() {
        isShow = false;
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
                dialog = null;
            }
            catch (Exception ex){
                System.out.println("Reminder dialog cancel btn" + ex);

            }
        }
    }

    //是否有
    private static boolean isShow = false;

    //这里为了适配大多数点击是为了关闭这个dialog而已的操作
    public static void showEasy(Activity activity, String connent) {
        try {

        if (activity != null) {
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_remind_view, null);
                dialog = new DialogUtil(activity, view, true);
                RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.ok_button);
                TextView textView = (TextView) view.findViewById(R.id.connent);
                textView.setText(connent);
                dialog.show();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        cancel();
                    }
                });

            }
        }

        }
        catch (Exception ex){

            System.out.println("Expectation for Easy Dialog" + ex);
        }
    }
    public static void showEasyHTMLTEXT(Activity activity, String connent) {
        if (activity != null) {
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_remind_view, null);
                dialog = new DialogUtil(activity, view, true);
                RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.ok_button);
                TextView textView = (TextView) view.findViewById(R.id.connent);
                TextView title_tv = view.findViewById(R.id.title_tv);
                title_tv.setVisibility(View.GONE);
//                textView.setText(connent);
                textView.setText(Html.fromHtml(connent));

                dialog.show();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        cancel();
                    }
                });

            }
        }
    }
    //imageview dial
    public static void showDelImage(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_del_image_view, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.connent);
            final TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
//            LoadingImageUtil.loading(imageView,connent);
            LoadingImageUtil.loadingPlaceholder(imageView, connent);

//            textView.setText(connent);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();
                    }
                });
                dialog.show();
            }

            TextView click_here = view.findViewById(R.id.click_here);
            click_here.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    WebviewData webviewData = new WebviewData();
                    webviewData.setUrl("http://docs.google.com/viewerng/viewer?url=" + "https://senangpks.com.my/mcash");
                    webviewData.setTitle("");
                   // WebviewPage.actionStart(activity, webviewData);
                }
            });

            Spannable wordtoSpan = new SpannableString(activity.getString(R.string.Click_here__more_info));//Click_here__more_info
            wordtoSpan.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.color_app_theme)), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            click_here.setText(wordtoSpan);
        }
    }



    //这个是需要监听点击确定后处理点击事件的
    public static void showDel(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_del_view, null);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            final TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            textView.setText(connent);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();
                    }
                });
                dialog.show();
            }
        }
    }
    //这个是需要监听点击确定后处理点击事件的
    public static void showDel_vertical_btn(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_del_view_vertical, null);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            final TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
//            textView.setText(connent);
            textView.setText(Html.fromHtml(connent));

            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();
                    }
                });
                dialog.show();
            }
        }
    }

    private static boolean isSelected = false;
    boolean iscolor = true;


    //卡密输入密码弹出框
    public static void getPinShow(Activity activity, final PinOnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_pin_view, null);
            TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            final EditText pin_text = (EditText) view.findViewById(R.id.pin_text);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        if (pin_text.getText().toString().trim().length() > 0) {
                            listener.onClickListener(pin_text.getText().toString().trim());
                            cancel();
                        } else {
                            listener.onClickListener("");
                        }
                    }
                });
                dialog.show();
            }
        }
    }

    //输入常还账单缴费金额
    private static String beForString = "0.00";
    private static String afterString = "";

    public static void fillInMoney(Activity activity, final PinOnClickListener listener) {
        if (activity != null) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_money_view, null);
            TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            final EditText pin_text = (EditText) view.findViewById(R.id.pin_text);

            pin_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    beForString = s.toString();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    afterString = s.toString();

                    KeyboardUtil.getInstance().getAfterrocessing(beForString, afterString, pin_text);

                }
            });
            pin_text.setSelection(pin_text.getText().length());

            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                       cancel();
                        if (pin_text.getText().toString().trim().length() > 1 || isInteger(pin_text.getText().toString().trim())) {
                            listener.onClickListener(pin_text.getText().toString().trim());
                        } else {
                            listener.onClickListener("");
                        }
                    }
                });
                dialog.show();
            }
        }
    }

    //这个是强行退出的页面，弹出后强行关闭
    public static void showExit(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_remind_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            textView.setText(connent);

            dialog = new DialogUtil(activity, view, true);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {


                        listener.onClick(ok);
                        //返回true禁止返回键关掉dialog
                        return false;
                    } else {

                        return false;
                    }
                }

            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(v);
                    cancel();

                }
            });
            dialog.show();
        }
    }

    //这个是需要监听点击确定后处理点击事件的
    public static void showTwo(Activity activity, String connent, final View.OnClickListener listener, final View.OnClickListener canelListener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.dialog_del_view, null);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            final TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            textView.setText(connent);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            canelListener.onClick(view);
                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                        canelListener.onClick(v);
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();
                    }
                });
                dialog.show();
            }
        }
    }

    //这个是需要监听点击确定后处理点击事件的
    public static void showYesOrNo(Activity activity, String connent, final View.OnClickListener listener, final View.OnClickListener canelListener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.dialog_del_view, null);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            final TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            textView.setText(connent);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                        canelListener.onClick(v);
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();
                    }
                });
                dialog.show();
            }
        }
    }


    //输入常还账单缴费金额
    public static void showTowButtonSelect(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_two_button_view, null);
            TextView cancel_button = (TextView) view.findViewById(R.id.cancel_button);
            final TextView ok_button = (TextView) view.findViewById(R.id.ok_button);
            final TextView connent_button = (TextView) view.findViewById(R.id.connent);
            connent_button.setText(connent);
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();

                        listener.onClick(v);

                    }
                });
                dialog.show();
            }
        }
    }

    /**
     * 弹出单个滑动选择框
     *
     * @param activity
     * @param listener
     */
    private static String getWheelViewString = "";

    public static void getWheelView(final Activity activity, final List<String> mOptionsItems, final PinOnClickListener listener) {
        if (activity != null) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_wheelview, null);
            final FontTextView confirm_button = (FontTextView) view.findViewById(R.id.confirm_button);
            final WheelView options1 = (WheelView) view.findViewById(R.id.options1);
            final RelativeLayout dialog_view = (RelativeLayout) view.findViewById(R.id.dialog_view);
            LinearLayout ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);

            options1.setCyclic(false);
            options1.setTextSize(16f);
            getWheelViewString = "";
            options1.setAdapter(new ArrayWheelAdapter(mOptionsItems));
            options1.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    getWheelViewString = "" + mOptionsItems.get(index);
                }
            });

            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                //点击空白处取消dialog\
                dialog_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        RemindDialogUtil.cancel();
                    }
                });
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getWheelViewString.equalsIgnoreCase("")) {
                            if (mOptionsItems.size() > 0) {
                                getWheelViewString = "" + mOptionsItems.get(0);
                            } else {
                                getWheelViewString = "";
                            }
                        }
                        if (getWheelViewString.toString().trim().length() > 0) {
                            listener.onClickListener(getWheelViewString);
                            isShow = false;
                            cancel();
                        } else {
                            isShow = false;
                            cancel();
                        }
                    }
                });
                dialog.show();
            }
        }
    }


    /*public static void getNew_WheelView(final Activity activity, final List<String> mOptionsItems, final PinOnClickListener listener) {
        if (activity != null) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.new_dialog_wheelview, null);
            final FontTextView confirm_button = (FontTextView) view.findViewById(R.id.confirm_button);
            final WheelView options1 = (WheelView) view.findViewById(R.id.options1);
            final RelativeLayout dialog_view = (RelativeLayout) view.findViewById(R.id.dialog_view);
            LinearLayout ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);

            final CheckBox fullday_checkbox = view.findViewById(R.id.fullday_checkbox);
            //check current state of a check box (true or false)
            Boolean checkBoxState = fullday_checkbox.isChecked();

            options1.setCyclic(false);
            options1.setTextSize(16f);
            getWheelViewString = "";
            options1.setAdapter(new ArrayWheelAdapter(mOptionsItems));

            options1.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    getWheelViewString = "" + mOptionsItems.get(index);

                }
            });

            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                //点击空白处取消dialog\
                dialog_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        cancel();
                    }
                });

                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!fullday_checkbox.isChecked()) {

                            if (getWheelViewString.equalsIgnoreCase("")) {

                                System.out.println("value of duration 1 " + mOptionsItems);
                                if (mOptionsItems.size() > 0) {

                                    getWheelViewString = "" + mOptionsItems.get(0);
                                } else {
                                    getWheelViewString = "";
                                }
                            }
                            if (getWheelViewString.toString().trim().length() > 0) {
                                listener.onClickListener(getWheelViewString);
                                isShow = false;
                                cancel();
                            } else {
                                isShow = false;
                               cancel();
                            }

                        }
                        else {
                            System.out.println("value of duration 2" + mOptionsItems.get(mOptionsItems.size() - 1));

                            listener.onClickListener(mOptionsItems.get(mOptionsItems.size() - 1));
                            isShow = false;
                            cancel();

                        }

//                        if (getWheelViewString.equalsIgnoreCase("")) {
//                            if (mOptionsItems.size() > 0) {
//                                getWheelViewString = "" + mOptionsItems.get(0);
//                            } else {
//                                getWheelViewString = "";
//                            }
//                        }
//                        if (getWheelViewString.toString().trim().length() > 0) {
//                            listener.onClickListener(getWheelViewString);
//                            isShow = false;
//                            RemindDialogUtil.cancel();
//                        } else {
//                            isShow = false;
//                            RemindDialogUtil.cancel();
//                        }
                    }
                });
                dialog.show();
            }
        }
    }*/

    private static int isListener = 1;
    private static String options1String = "";
    private static String options2String = "";
    private static String options3String = "";
    private static String[] monthArray_en = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static String[] monthArray_zh = {"", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private static String[] monthArray_ms = {"", "Januari", "Februari", "Mac", "April", "Mungkin", "Jun", "Julai", "Ogos", "September", "Oktober", "November", "Disember"};

//    /**
//     * //交易记录选择dialog
//     *
//     * @param activity
//     * @param time_log
//     * @param type_log
//     * @param isOnClickListener
//     * @param listener
//     */

    /*public static void getTransaction(final Activity activity, final ArrayList<String> time_log,
                                      final ArrayList<TransactionModel.DataBean.TypeLogBean> type_log, int isOnClickListener, final int mYear, final int mMonth, final TranOnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_tran_view, null);
            final FontTextView confirm_button = (FontTextView) view.findViewById(R.id.confirm_button);
            final WheelView options1 = (WheelView) view.findViewById(R.id.options1);
            final WheelView options2 = (WheelView) view.findViewById(R.id.options2);
            final WheelView options3 = (WheelView) view.findViewById(R.id.options3);
            final RelativeLayout dialog_view = (RelativeLayout) view.findViewById(R.id.dialog_view);
            final FontTextView by_month = (FontTextView) view.findViewById(R.id.by_month);
            final FontTextView by_service = (FontTextView) view.findViewById(R.id.by_service);
            LinearLayout ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);

            isListener = isOnClickListener;
            if (isListener == 1) {
                by_month.setBackgroundResource(R.drawable.yellow_round_end_darkgrey_button);
                by_month.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                by_service.setBackgroundResource(R.drawable.add_button_grey);
                by_service.setTextColor(activity.getResources().getColor(R.color.color_333333));
                options1.setVisibility(View.VISIBLE);
                options2.setVisibility(View.VISIBLE);
                options3.setVisibility(View.GONE);
            } else {
                by_month.setBackgroundResource(R.drawable.add_button_grey);
                by_month.setTextColor(activity.getResources().getColor(R.color.color_333333));
                by_service.setBackgroundResource(R.drawable.yellow_round_end_darkgrey_button);
                by_service.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                options1.setVisibility(View.GONE);
                options2.setVisibility(View.GONE);
                options3.setVisibility(View.VISIBLE);
            }
            options1String = "";
            options2String = "";
            options3String = "";

            options1.setCyclic(false);
            options1.setTextSize(16f);
            options2.setCyclic(false);
            options2.setTextSize(16f);
            options3.setCyclic(false);
            options3.setTextSize(16f);


            final List<String> mOptionsItems = new ArrayList<>();
            final List<MonthModel> mMonthModelItems = new ArrayList<>();
            if (time_log.size() > 0) {
                if (time_log.get(0).equalsIgnoreCase(String.valueOf(mYear))) {
                    for (int i = 1; i <= mMonth; i++) {
                        MonthModel monthModel = new MonthModel();
                        if (i < 10) {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        } else {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        }
                    }
                } else {
                    for (int i = 1; i <= 12; i++) {
                        MonthModel monthModel = new MonthModel();
                        if (i < 10) {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        } else {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        }
                    }
                }
            }

            final List<String> serviceList = new ArrayList<>();
            if (type_log.size() > 0) {
                for (TransactionModel.DataBean.TypeLogBean typeLogBean : type_log) {
                    serviceList.add(typeLogBean.getValue());
                }
            }

            if (mMonthModelItems.size() > 0) {
                for (MonthModel mMonthModelItem : mMonthModelItems) {
                    if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                        mOptionsItems.add(mMonthModelItem.getMonth_zh());
                    } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                        mOptionsItems.add(mMonthModelItem.getMonth_en());
                    } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                        mOptionsItems.add(mMonthModelItem.getMonth_ms());
                    }
                }
            }


//            wva.setItems(Arrays.asList(PLANETS),1);//init selected position is 1 初始选中位置为1
            options1.setAdapter(new ArrayWheelAdapter(time_log));
            options1.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    options1String = "" + time_log.get(index);
                    mOptionsItems.clear();
                    mMonthModelItems.clear();
                    if (options1String.equalsIgnoreCase(String.valueOf(mYear))) {
                        for (int i = 1; i <= mMonth; i++) {
                            MonthModel monthModel = new MonthModel();
                            if (i < 10) {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            } else {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            }
                        }
                    } else {
                        for (int i = 1; i <= 12; i++) {
                            MonthModel monthModel = new MonthModel();
                            if (i < 10) {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            } else {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            }
                        }
                    }
                    if (mMonthModelItems.size() > 0) {
                        for (MonthModel mMonthModelItem : mMonthModelItems) {
                            if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                                mOptionsItems.add(mMonthModelItem.getMonth_zh());
                            } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                                mOptionsItems.add(mMonthModelItem.getMonth_en());
                            } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                                mOptionsItems.add(mMonthModelItem.getMonth_ms());
                            }
                        }
                    }
                    options2.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    options2.setCurrentItem(0);
                    options2String = "" + mMonthModelItems.get(0).getId();
                }
            });

            options2.setAdapter(new ArrayWheelAdapter(mOptionsItems));
            options2.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    try {
    //                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                        for (MonthModel mMonthModelItem : mMonthModelItems) {
                            if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                                if (mMonthModelItem.getMonth_zh().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                                if (mMonthModelItem.getMonth_en().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                                if (mMonthModelItem.getMonth_ms().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            }
                        }

                    }
                    catch (Exception ex){
                        System.out.println("RemindDialogUtil expectation" + ex);
                    }
                }
            });
            options3.setAdapter(new ArrayWheelAdapter(serviceList));
            options3.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    try{
                        options3String = "" + serviceList.get(index);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Exception RemindDialogUtil" + ex);
                    }
//                    options3String = "" + serviceList.get(index);
                }
            });
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                //点击空白处取消dialog\
                dialog_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                    }
                });

                //month
                by_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isListener != 1) {
                            isListener = 1;
                            by_month.setBackgroundResource(R.drawable.a_cashback_blue_five);
                            by_month.setTextColor(activity.getResources().getColor(R.color.color_000000));
                            by_service.setBackgroundResource(R.drawable.add_button_grey);
                            by_service.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                            options1.setVisibility(View.VISIBLE);
                            options2.setVisibility(View.VISIBLE);
                            options3.setVisibility(View.GONE);

                        }
                    }
                });
                //service
                by_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isListener != 2) {
                            isListener = 2;
                            by_month.setBackgroundResource(R.drawable.add_button_grey);
                            by_month.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                            by_service.setBackgroundResource(R.drawable.a_cashback_blue_five);
                            by_service.setTextColor(activity.getResources().getColor(R.color.color_000000));
                            options1.setVisibility(View.GONE);
                            options2.setVisibility(View.GONE);
                            options3.setVisibility(View.VISIBLE);

                        }
                    }
                });
                //确认
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;

                        if (isListener == 1) {
                            if (options1String.equalsIgnoreCase("")) {
                                if (time_log.size() > 0) {
                                    options1String = time_log.get(0);
                                } else {
                                    options1String = "";
                                }
                            }
                            if (options2String.equalsIgnoreCase("")) {
                                if (mMonthModelItems.size() > 0) {
                                    options2String = mMonthModelItems.get(0).getId();
                                } else {
                                    options2String = "";
                                }
                            }
//                            Toast.makeText(activity, options1String + "-" + options2String, Toast.LENGTH_SHORT).show();
                            if (options1String.toString().trim().length() > 0 && options2String.toString().trim().length() > 0) {
                                listener.onClickListener(options1String + "-" + options2String, isListener);
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            } else {
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            }

                        } else {
                            if (options3String.equalsIgnoreCase("")) {
                                if (type_log.size() > 0) {
                                    options3String = type_log.get(0).getKey();
                                } else {
                                    options3String = "";
                                }
                            } else {
                                for (TransactionModel.DataBean.TypeLogBean typeLogBean : type_log) {
                                    if (typeLogBean.getValue().equalsIgnoreCase(options3String)) {
                                        options3String = typeLogBean.getKey();
                                        break;
                                    }
                                }
                            }
//                            Toast.makeText(activity, options3String, Toast.LENGTH_SHORT).show();
                            if (options3String.toString().trim().length() > 0) {
                                listener.onClickListener(options3String, isListener);
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            } else {
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            }

                        }
                    }
                });
                dialog.show();
            }
        }
    }*/

//   /* *//**//**
//     * //交易记录选择dialog
//     *
//     * @param activity
//     * @param time_log
//     * @param isOnClickListener
//     * @param listener
//     */

  /*  public static void getTransaction2(final Activity activity, final ArrayList<String> time_log,
                                     *//* final ArrayList<TransactionModel.DataBean.TypeLogBean> type_log,*//* int isOnClickListener, final int mYear, final int mMonth, final TranOnClickListener listener) {
        if (activity != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_tran_view, null);
            final FontTextView confirm_button = (FontTextView) view.findViewById(R.id.confirm_button);
            final WheelView options1 = (WheelView) view.findViewById(R.id.options1);
            final WheelView options2 = (WheelView) view.findViewById(R.id.options2);
            final WheelView options3 = (WheelView) view.findViewById(R.id.options3);
            final RelativeLayout dialog_view = (RelativeLayout) view.findViewById(R.id.dialog_view);
            final FontTextView by_month = (FontTextView) view.findViewById(R.id.by_month);
            final FontTextView by_service = (FontTextView) view.findViewById(R.id.by_service);
            LinearLayout ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);

            isListener = isOnClickListener;
            if (isListener == 1) {
                by_month.setBackgroundResource(R.drawable.yellow_round_end_darkgrey_button);
                by_month.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                by_service.setBackgroundResource(R.drawable.add_button_grey);
                by_service.setTextColor(activity.getResources().getColor(R.color.color_333333));
                options1.setVisibility(View.VISIBLE);
                options2.setVisibility(View.VISIBLE);
                options3.setVisibility(View.GONE);
            } else {
                by_month.setBackgroundResource(R.drawable.add_button_grey);
                by_month.setTextColor(activity.getResources().getColor(R.color.color_333333));
                by_service.setBackgroundResource(R.drawable.yellow_round_end_darkgrey_button);
                by_service.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                options1.setVisibility(View.GONE);
                options2.setVisibility(View.GONE);
                options3.setVisibility(View.VISIBLE);
            }
            options1String = "";
            options2String = "";
            options3String = "";

            options1.setCyclic(false);
            options1.setTextSize(16f);
            options2.setCyclic(false);
            options2.setTextSize(16f);
            options3.setCyclic(false);
            options3.setTextSize(16f);


            final List<String> mOptionsItems = new ArrayList<>();
            final List<MonthModel> mMonthModelItems = new ArrayList<>();
            if (time_log.size() > 0) {
                if (time_log.get(0).equalsIgnoreCase(String.valueOf(mYear))) {
                    for (int i = 1; i <= mMonth; i++) {
                        MonthModel monthModel = new MonthModel();
                        if (i < 10) {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        } else {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        }
                    }
                } else {
                    for (int i = 1; i <= 12; i++) {
                        MonthModel monthModel = new MonthModel();
                        if (i < 10) {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        } else {
                            monthModel.setId("0" + i);
                            monthModel.setMonth_en(monthArray_en[i]);
                            monthModel.setMonth_ms(monthArray_ms[i]);
                            monthModel.setMonth_zh(monthArray_zh[i]);
                            mMonthModelItems.add(monthModel);
                        }
                    }
                }
            }

            final List<String> serviceList = new ArrayList<>();
           *//* if (type_log.size() > 0) {
                for (TransactionModel.DataBean.TypeLogBean typeLogBean : type_log) {
                    serviceList.add(typeLogBean.getValue());
                }
            }*//*

            if (mMonthModelItems.size() > 0) {
                for (MonthModel mMonthModelItem : mMonthModelItems) {
                    if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                        mOptionsItems.add(mMonthModelItem.getMonth_zh());
                    } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                        mOptionsItems.add(mMonthModelItem.getMonth_en());
                    } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                        mOptionsItems.add(mMonthModelItem.getMonth_ms());
                    }
                }
            }


//            wva.setItems(Arrays.asList(PLANETS),1);//init selected position is 1 初始选中位置为1
            options1.setAdapter(new ArrayWheelAdapter(time_log));
            options1.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    options1String = "" + time_log.get(index);
                    mOptionsItems.clear();
                    mMonthModelItems.clear();
                    if (options1String.equalsIgnoreCase(String.valueOf(mYear))) {
                        for (int i = 1; i <= mMonth; i++) {
                            MonthModel monthModel = new MonthModel();
                            if (i < 10) {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            } else {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            }
                        }
                    } else {
                        for (int i = 1; i <= 12; i++) {
                            MonthModel monthModel = new MonthModel();
                            if (i < 10) {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            } else {
                                monthModel.setId("0" + i);
                                monthModel.setMonth_en(monthArray_en[i]);
                                monthModel.setMonth_ms(monthArray_ms[i]);
                                monthModel.setMonth_zh(monthArray_zh[i]);
                                mMonthModelItems.add(monthModel);
                            }
                        }
                    }
                    if (mMonthModelItems.size() > 0) {
                        for (MonthModel mMonthModelItem : mMonthModelItems) {
                            if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                                mOptionsItems.add(mMonthModelItem.getMonth_zh());
                            } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                                mOptionsItems.add(mMonthModelItem.getMonth_en());
                            } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                                mOptionsItems.add(mMonthModelItem.getMonth_ms());
                            }
                        }
                    }
                    options2.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    options2.setCurrentItem(0);
                    options2String = "" + mMonthModelItems.get(0).getId();
                }
            });

            options2.setAdapter(new ArrayWheelAdapter(mOptionsItems));
            options2.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    try {
                        //                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                        for (MonthModel mMonthModelItem : mMonthModelItems) {
                            if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                                if (mMonthModelItem.getMonth_zh().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                                if (mMonthModelItem.getMonth_en().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                                if (mMonthModelItem.getMonth_ms().equalsIgnoreCase("" + mOptionsItems.get(index))) {
                                    options2String = "" + mMonthModelItem.getId();
                                    break;
                                }
                            }
                        }

                    }
                    catch (Exception ex){
                        System.out.println("RemindDialogUtil expectation" + ex);
                    }
                }
            });
            options3.setAdapter(new ArrayWheelAdapter(serviceList));
            options3.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
//                    Toast.makeText(activity, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
                    try{
                        options3String = "" + serviceList.get(index);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Exception RemindDialogUtil" + ex);
                    }
//                    options3String = "" + serviceList.get(index);
                }
            });
            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                //点击空白处取消dialog\
                dialog_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                    }
                });

                //month
                by_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isListener != 1) {
                            isListener = 1;
                            by_month.setBackgroundResource(R.drawable.a_cashback_blue_five);
                            by_month.setTextColor(activity.getResources().getColor(R.color.color_000000));
                            by_service.setBackgroundResource(R.drawable.add_button_grey);
                            by_service.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                            options1.setVisibility(View.VISIBLE);
                            options2.setVisibility(View.VISIBLE);
                            options3.setVisibility(View.GONE);

                        }
                    }
                });
                //service
                by_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isListener != 2) {
                            isListener = 2;
                            by_month.setBackgroundResource(R.drawable.add_button_grey);
                            by_month.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                            by_service.setBackgroundResource(R.drawable.a_cashback_blue_five);
                            by_service.setTextColor(activity.getResources().getColor(R.color.color_000000));
                            options1.setVisibility(View.GONE);
                            options2.setVisibility(View.GONE);
                            options3.setVisibility(View.VISIBLE);

                        }
                    }
                });
                //确认
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;

                        if (isListener == 1) {
                            if (options1String.equalsIgnoreCase("")) {
                                if (time_log.size() > 0) {
                                    options1String = time_log.get(0);
                                } else {
                                    options1String = "";
                                }
                            }
                            if (options2String.equalsIgnoreCase("")) {
                                if (mMonthModelItems.size() > 0) {
                                    options2String = mMonthModelItems.get(0).getId();
                                } else {
                                    options2String = "";
                                }
                            }
//                            Toast.makeText(activity, options1String + "-" + options2String, Toast.LENGTH_SHORT).show();
                            if (options1String.toString().trim().length() > 0 && options2String.toString().trim().length() > 0) {
                                listener.onClickListener(options1String + "-" + options2String, isListener);
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            } else {
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            }

                        } else {
                           *//* if (options3String.equalsIgnoreCase("")) {
                                *//**//*if (type_log.size() > 0) {
                                    options3String = type_log.get(0).getKey();
                                } else {
                                    options3String = "";
                                }*//**//*
                            } else {
                                for (TransactionModel.DataBean.TypeLogBean typeLogBean : type_log) {
                                    if (typeLogBean.getValue().equalsIgnoreCase(options3String)) {
                                        options3String = typeLogBean.getKey();
                                        break;
                                    }
                                }
                            }*//*
//                            Toast.makeText(activity, options3String, Toast.LENGTH_SHORT).show();
                            if (options3String.toString().trim().length() > 0) {
                                listener.onClickListener(options3String, isListener);
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            } else {
                                isShow = false;
                                com.linktu.linktuwallet.util.RemindDialogUtil.cancel();
                            }

                        }
                    }
                });
                dialog.show();
            }
        }
    }*/

    //这里为了适配大多数点击是为了关闭这个dialog而已的操作
    public static void showSpinEasy(Activity activity, String title, String connent) {
        if (activity != null) {
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_remind_view, null);
                dialog = new DialogUtil(activity, view, true);
                RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.ok_button);
                TextView textView = (TextView) view.findViewById(R.id.connent);
                TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
                title_tv.setText(title);
                textView.setText(connent);
                dialog.show();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        cancel();
                    }
                });

            }
        }
    }

    //这个是需要监听点击确定后处理点击事件的
    public static void showMessage(Activity activity, String connent, final View.OnClickListener listener) {
        if (activity != null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_remind_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            TextView textView = (TextView) view.findViewById(R.id.connent);
            textView.setText(connent);
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();

                    }
                });
                dialog.show();
            }
        }
    }

    //这里为了适配大多数点击是为了关闭这个dialog而已的操作
    public static void showEasySpin(Activity activity, String connent) {
        try {

            if (activity != null) {
                if (!isShow) {
                    if (dialog != null) {
                        cancel();
                    }
                    isShow = true;
                    LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_remind_view_spin, null);
                    dialog = new DialogUtil(activity, view, true);
                    RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.ok_button);
                    TextView textView = (TextView) view.findViewById(R.id.connent);
//                    textView.setText(connent);
                    textView.setText(Html.fromHtml(connent));
                    dialog.show();
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {

                                isShow = false;
                                //返回true禁止返回键关掉dialog
                                return false;
                            } else {
                                isShow = false;
                                return false;
                            }
                        }

                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            cancel();
                        }
                    });

                }
            }

        }
        catch (Exception ex){

            System.out.println("Expectation for spin pop up" + ex);
        }
    }

    //这里为了适配大多数点击是为了关闭这个dialog而已的操作
    /*public static void showBusEasy(Activity activity, String address, String time, String connent) {
        if (activity != null) {
            if (!isShow) {
                if (dialog != null) {
                    cancel();
                }
                isShow = true;
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_bus_view, null);
                dialog = new DialogUtil(activity, view, true);
                RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.ok_button);
                TextView textView = (TextView) view.findViewById(R.id.connent);
                FontTextView address_tv = (FontTextView) view.findViewById(R.id.address_tv);
                TextView time_tv = (TextView) view.findViewById(R.id.time_tv);
                textView.setText(connent);
                address_tv.setText(address);
                time_tv.setText(time);
                dialog.show();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        cancel();
                    }
                });

            }
        }
    }*/

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
//        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        Pattern pattern = Pattern.compile("[1-9]*");
        return pattern.matcher(str).matches();
    }
}
