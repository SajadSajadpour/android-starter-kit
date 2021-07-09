package com.food.iotsensor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.food.iotsensor.Activity.BaseActivity;
import com.food.iotsensor.Network.model.WebviewData;
import com.food.iotsensor.ViewUtility.LeavePageDialog;
import com.food.iotsensor.ViewUtility.webview.ObservableScrollViewCallbacks;
import com.food.iotsensor.ViewUtility.webview.ObservableWebView;
import com.food.iotsensor.ViewUtility.webview.ScrollState;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

public class WebPage  extends BaseActivity implements MyWebChromClient.ProgressListener, ObservableScrollViewCallbacks {

        String titleStr, targetURL, token, token_md5;

        ObservableWebView webView;
        LinearLayout second_bar_view;
        SwipeRefreshLayout swipeLayout;
        ImageView second_bar_right;
        String colorStr;
        int colorCode, minimumHeight;

        Button webview_pop_info_ok_btn, webview_pop_info_cancel_btn;

        Context c;

        Boolean allowBack;
        private String loadUrl = "";

        private ProgressDialog progDailog;

        ProgressBar mProgressBar;

        private Activity activity;

        String Trxid,From;

/**
 * ，5
 *
 * @param activity
 * @param webviewData
 */
        public static void actionStart(Activity activity, WebviewData webviewData) {
                if (!webviewData.getUrl().equals("")) {
                final Intent WebviewPage = new Intent(activity, WebPage.class);
                WebviewPage.putExtra("title", webviewData.getTitle());
                WebviewPage.putExtra("loadUrl", webviewData.getUrl());
                activity.startActivity(WebviewPage);
                }
        }

        //jump url for scan qr code
        public static void actionStart(String loadUrl,String Trxid,String From, Activity activity) {
                Intent intent = new Intent(activity, WebPage.class);
                intent.putExtra("loadUrl", loadUrl);
                intent.putExtra("Trxid", Trxid);
                intent.putExtra("From", From);
                activity.startActivity(intent);
                }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_webview_page);

            this.c = getBaseContext();

            allowBack = true;
            activity = this;
            second_bar_view = (LinearLayout) findViewById(R.id.second_bar_view);
            second_bar_right = (ImageView) findViewById(R.id.second_bar_right);
            second_bar_right.setBackgroundResource(R.mipmap.refresh_button);
            second_bar_right.setVisibility(View.VISIBLE);
            second_bar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    webView.loadUrl(loadUrl);

                    }
                    });
            Bundle extras = getIntent().getExtras();
            titleStr = extras.getString("title");
            targetURL = extras.getString("loadUrl");
            loadUrl = targetURL;
            //new
            Trxid=extras.getString("Trxid");
            From=extras.getString("From");

            // here top title
            System.out.println("loadUrl>" + targetURL);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            minimumHeight = displayMetrics.heightPixels;

            initButtons();

            initWebView();

            }



    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    @Override
    public void onStart() {
            super.onStart();

          /*  swipeLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                    new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                          *//*  if (webView.getScrollY() == 0) {
                                second_bar_view.setVisibility(View.VISIBLE);
                                swipeLayout.setEnabled(true);
                            }
                            else {
                                swipeLayout.setEnabled(false);
                                second_bar_view.setVisibility(View.GONE);
                            }*//*

                        }
                    });*/
            }

    @Override
    public void onStop() {
            //swipeLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
            super.onStop();
            }

    public void initButtons() {
            setupToolbar(titleStr, new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            LeavePageDialog.getInstance().show(activity);
            }
            });
          /*  TextView title_bar_title = (TextView)findViewById(R.id.title_bar_title);
            title_bar_title.setText(titleStr);

            ImageView btn_title_bar_back = (ImageView) findViewById(R.id.btn_title_bar_back);
            btn_title_bar_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrameLayout activity_webview_popup_layout = (FrameLayout)findViewById(R.id.activity_webview_popup_layout);
                    activity_webview_popup_layout.setVisibility(View.VISIBLE);
                }
            });

            webview_pop_info_ok_btn = (Button)findViewById(R.id.webview_pop_info_ok_btn);
            webview_pop_info_ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            webview_pop_info_cancel_btn = (Button)findViewById(R.id.webview_pop_info_cancel_btn);
            webview_pop_info_cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrameLayout activity_webview_popup_layout = (FrameLayout)findViewById(R.id.activity_webview_popup_layout);
                    activity_webview_popup_layout.setVisibility(View.GONE);
                }
            });*/
            }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

    @Override
    public void onDownMotionEvent() {

            }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            if (scrollState == ScrollState.UP) {
            if (second_bar_view.isShown()) {
            second_bar_view.setVisibility(View.GONE);
            }
            } else if (scrollState == ScrollState.DOWN) {
            if (!second_bar_view.isShown()) {
            second_bar_view.setVisibility(View.VISIBLE);
            }
            }
            }

    @Override
    protected void onResume() {
            super.onResume();
            if (webView != null) {
            webView.setScrollViewCallbacks(this);
            }
            }
/*public void setupThemeColor(){
        colorStr= DataManager.UserTheme;
        colorCode = getResources().getIdentifier(colorStr,"color", getPackageName());


        webview_pop_info_ok_btn = (Button)findViewById(R.id.webview_pop_info_ok_btn);
        webview_pop_info_ok_btn.getBackground().setColorFilter(ContextCompat.getColor(c, colorCode), PorterDuff.Mode.MULTIPLY);

    }*/

    private class LoadInSame extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, final String url) {
    //                    progDailog.dismiss();

            if (url.contains("/Payment/complete_payment/")) {
                allowBack = false;
            }

            webView.scrollTo(0, 0);
        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webview, WebResourceRequest webrequest) {
        Log.d("test", "shouldInterceptRequest");
        return this.handleRequest(webrequest.getUrl().toString());
    }

    @NonNull
    private WebResourceResponse handleRequest(@NonNull String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            return new WebResourceResponse("text/json", "utf-8", inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

    public void initWebView() {

        second_bar_view = (LinearLayout) findViewById(R.id.second_bar_view);
        webView = (ObservableWebView) findViewById(R.id.webview_page_content);
        webView.setScrollViewCallbacks(this);
        String languageToLoad = DataManager.UserLocale; // your language
        System.out.println("languageToLoad >" + languageToLoad);
        Locale locale = new Locale(languageToLoad);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }
        resources.updateConfiguration(configuration, displayMetrics);

//        webView.setMinimumHeight(minimumHeight);
        System.out.println("type sss" + titleStr);
        if (!targetURL.contains("<p><b>"))
        {
            if (isNetworkAvailable(c)) {



                String fromWhere = From+"/"+Trxid;
                String ua = webView.getSettings().getUserAgentString()+"/"+fromWhere;


                System.out.println("user agent dd" +  ua);

                webView.getSettings().setUserAgentString(ua);

                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setBuiltInZoomControls(true); //不显示webview缩放按钮
                webView.getSettings().setDisplayZoomControls(false);
                String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
                webView.getSettings().setAppCachePath(appCachePath);
                webView.getSettings().setAllowFileAccess(true);
                webView.getSettings().setAppCacheEnabled(true);

                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                webView.getSettings().setGeolocationEnabled(true);
                webView.getSettings().setDatabaseEnabled(true);

                mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
                webView.setWebChromeClient(new MyWebChromClient(this) {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                            mProgressBar.setVisibility(ProgressBar.VISIBLE);
                        }

                        mProgressBar.setProgress(progress);
                        if (progress == 100) {
                            mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    }

                    @Override
                    public void onCloseWindow(WebView window) {
                        super.onCloseWindow(window);
                        finish();
                    }
                });
                webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
                webView.setWebViewClient(new WebViewClient() {


                    @SuppressWarnings("deprecation")
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //                    progDailog.show();
                        if (url == null)
                            return false;

                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);
                                return true;
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                                return false;
                            }
                        } catch (Exception e) { //crash (scheme url APP,  crash)
                            return false;
                        }
                    }

                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        //                    progDailog.show();
                        String url = request.getUrl().toString();

                        try {
                            if (url.startsWith("http:") || url.startsWith("https:")) {
                                view.loadUrl(url);
                                return true;
                            } else {

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }

                    @Override
                    public void onPageFinished(WebView view, final String url) {
                        //                    progDailog.dismiss();
                        loadUrl = url;

                        if (url.contains("/Payment/complete_payment/")) {
                            allowBack = false;
                        }

                        webView.scrollTo(0, 0);
                    }
                });

                webView.loadUrl(targetURL);
                swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
                swipeLayout.setEnabled(false);
                //
                //            webView.addJavascriptInterface(this, "MyApp");
    /*
                swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
                swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                        if (swipeLayout.isRefreshing()) {
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });*/


            }
            else
            {
                showWIFIAlertToUser();
            }
        }
        else
        {




            // show the text if the url is text ..... hh
            webView.loadDataWithBaseURL(null, targetURL, "text/html", "UTF-8", null);

        }



    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void showWIFIAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("No Connection");
        alertDialogBuilder.setMessage("Please check your Internet Connection")
                .setCancelable(false);
        alertDialogBuilder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (allowBack) {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack(); // Go to previous page
                return true;
            } else {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                    return true;
                } else {
                  /*  FrameLayout activity_webview_popup_layout = (FrameLayout)findViewById(R.id.activity_webview_popup_layout);
                    activity_webview_popup_layout.setVisibility(View.VISIBLE);
                  */
                    LeavePageDialog.getInstance().show(activity);
                    return true;
                }

            }
        }
        return false;
        // Use this as else part
//        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onUpdateProgress(int progressValue) {
        mProgressBar.setProgress(progressValue);
        if (progressValue == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @JavascriptInterface
    public void resize(final float height) {
        WebPage.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("screen height>" + height);
                webView.setLayoutParams(new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }
}
