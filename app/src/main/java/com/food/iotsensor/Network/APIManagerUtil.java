package com.food.iotsensor.Network;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.food.iotsensor.Constant;
import com.food.iotsensor.DataManager;
import com.food.iotsensor.Network.gsonutil.NullBooleanToEmptyAdapterFactory;
import com.food.iotsensor.Network.gsonutil.NullDoubleToEmptyAdapterFactory;
import com.food.iotsensor.Network.gsonutil.NullIntgerToEmptyAdapterFactory;
import com.food.iotsensor.Network.gsonutil.NullStringToEmptyAdapterFactory;
import com.food.iotsensor.Network.gsonutil.model.NetWorkCallback;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.ACacheUtil;
import com.food.iotsensor.Utility.ActivityUtil;
import com.food.iotsensor.Utility.MapSortUtil;
import com.food.iotsensor.Utility.MyReceiverUtil;
import com.food.iotsensor.Utility.RemindDialogUtil;
import com.food.iotsensor.Utility.SharedPreferencesUtils;
import com.food.iotsensor.Utility.TimeUtil;
import com.food.iotsensor.Utility.WaitingDialogUtil;
import com.food.iotsensor.ViewUtility.OopsViewDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 * api
 */

public class APIManagerUtil {

    private static APIManagerUtil apiManagerUtil;


    public static APIManagerUtil getInstance() {

        if (apiManagerUtil == null) {
            apiManagerUtil = new APIManagerUtil();
        }
        return apiManagerUtil;
    }

    private static boolean isFinsh = false;
    private static boolean isCustom = false;


    //params
    public void notDisposeResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        isCustom = true;
        isFinsh = false;
        try {

            NetWorkUtil.getInstance().startPostResponse(url, params, getHeader(), new NetWorkCallback() {
                @Override
                public void onResponse(Object response) {
                    WaitingDialogUtil.cancel();
                    if (response != null) {

                        JSONObject json;
                        try {
                            json = new JSONObject((String) response);
                            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                            callback.onResponse(gson.fromJson(json.toString(), requestModel));

                        } catch (Exception e) {
                            callback.onFailure(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    WaitingDialogUtil.cancel();
                    callback.onFailure(message);
                }

            });
        } catch (Exception e) {
            WaitingDialogUtil.cancel();
            e.printStackTrace();
            callback.onFailure(e.getLocalizedMessage());
        }
    }


    public void customPostResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        isCustom = true;
        isFinsh = false;

        greenPostResponse(url, params, callback, requestModel);
    }

    //activity
    public void errorFinishPostResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        isCustom = false;
        isFinsh = true;

        greenPostResponse(url, params, callback, requestModel);

    }


    public void startPostResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        isCustom = false;
        isFinsh = false;

        greenPostResponse(url, params, callback, requestModel);

    }


    public void simplePostResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {


        greenPostResponse(url, params, callback, requestModel);

    }

    //map
    public void noSignPostResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        isCustom = false;
        isFinsh = true;

         greenPostResponse(url, params, callback, requestModel);

    }

    //Details
    public void noDetailsPostResponse(String url, Map<String, String> params, String details, int position, final NetWorkCallback callback, final Class requestModel) {
        isCustom = false;
        isFinsh = true;
        WaitingDialogUtil.show(ActivityUtil.getActivity());
        try {
            if (position == 1) {
                params = noDetailsGetMap(params, details);
            } else {
                params = noQrcodeGetMap(params, details);
            }

            NetWorkUtil.getInstance().startPostResponse(url, params, getHeader(), new NetWorkCallback() {
                @Override
                public void onResponse(Object response) {
                    WaitingDialogUtil.cancel();
                    if (response != null) {
                        try {
                            JSONObject json;
                            json = new JSONObject((String) response);
                            if (!json.isNull("result")) {
                                if (isSuccess(ActivityUtil.getActivity(), json.optString("result"))) {
                                    if (!json.isNull("status")) {
                                        if (isRight(ActivityUtil.getActivity(), json.optString("status"), json.optString("message"))) {
                                            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                            callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                        }
                                    } else {
                                        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                        callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                    }
                                }
                            } else if (!json.isNull("code")) {
                                if (isRight(ActivityUtil.getActivity(), json.optString("code"), json.optString("msg"))) {
                                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                    callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                }

                            } else {
                                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                callback.onResponse(gson.fromJson(json.toString(), requestModel));
                            }
                        } catch (Exception e) {
                            callback.onFailure(e.getLocalizedMessage());

                            e.printStackTrace();

                        }
                    } else {
                        callback.onFailure("Data is Empty");
                    }
                }

                @Override
                public void onFailure(String message) {
                    WaitingDialogUtil.cancel();
                    // callback.onFailure(message);
                    OopToast();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            //callback.onFailure("data is wrong");
            OopToast();
        }

    }

    //api 200
    private boolean isRight(final Activity activity, String status, String message) {
        if (isFinsh) {
            if (activity != null) {
                if (status != null && status.equalsIgnoreCase("200")) {
                    return true;
                } else if (status != null && status.equalsIgnoreCase("1")) {
                    return true;
                } else if (status != null && status.equalsIgnoreCase("3")) {
                    if (!isOpenDialog) {
                        isOpenDialog = true;
                        SharedPreferencesUtils.getInstance(activity).setData(Constant.authToken, "");
                        DataManager.AuthTokenHolder = "";
                       // CheckUserDialog.getInstance().cancel();
                        SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerAuthToken, "");
                        ACacheUtil.get(activity).put(Constant.authToken, "");
                        RemindDialogUtil.show(activity, activity.getString(R.string.invalid_token_msg), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isOpenDialog = false;
                                //SplashActivity.actionStart(activity);
                                activity.finish();
                            }
                        });
                    }
                    return false;
                } else {
                    if (!isOpenDialog) {
                        isOpenDialog = true;
                        RemindDialogUtil.show(activity, message, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isOpenDialog = false;

                                activity.finish();
                            }
                        });
                    }
                    return false;
                }

            } else {
                return false;
            }

        } else {
            if (activity != null) {
                if (status != null && status.equalsIgnoreCase("200")) {
                    return true;
                } else if (status != null && status.equalsIgnoreCase("1")) {
                    return true;
                } else if (status != null && status.equalsIgnoreCase("3")) {
                    if (!isOpenDialog) {
                        isOpenDialog = true;
                        SharedPreferencesUtils.getInstance(activity).setData(Constant.authToken, "");
                        DataManager.AuthTokenHolder = "";
                       // CheckUserDialog.getInstance().cancel();
                        SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerAuthToken, "");
                        ACacheUtil.get(activity).put(Constant.authToken, "");
                        RemindDialogUtil.show(activity, activity.getString(R.string.invalid_token_msg), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isOpenDialog = false;
                               // SplashActivity.actionStart(activity);
                                activity.finish();
                            }
                        });
                    }
                    return false;
                } else {
                    if (!isOpenDialog && "2".equalsIgnoreCase(status)) {
                        return false;
                    }
                    if (!isOpenDialog) {
                        isOpenDialog = true;
                        RemindDialogUtil.show(activity, message, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isOpenDialog = false;
                                Intent intent = new Intent();
                                intent.setAction(MyReceiverUtil.SWIPELAYOUT_CLOSE);
                                activity.sendBroadcast(intent);
                            }
                        });
                    }
                    return false;
                }

            } else {
                return false;
            }
        }

    }


    private boolean isOpenDialog = false;

    public boolean isSuccess(final Activity activity, String data) {
        if (activity != null) {
            if (data != null && (data.equalsIgnoreCase(Constant.success) || "succeed".equalsIgnoreCase(data))) {
                return true;
            } else {
                if (!isOpenDialog) {
                    isOpenDialog = true;
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.authToken, "");
                    DataManager.AuthTokenHolder = "";
                   // CheckUserDialog.getInstance().cancel();
                    SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerAuthToken, "");
                    ACacheUtil.get(activity).put(Constant.authToken, "");
                    RemindDialogUtil.show(activity, activity.getString(R.string.invalid_token_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isOpenDialog = false;
                         //   SplashActivity.actionStart(activity);
                            activity.finish();
                        }
                    });
                }
                return false;
            }
        } else {
            return false;
        }
    }

    public void OopToast() {
        if (isFinsh) {
            OopsViewDialog.getInstance().showFinishDialog(ActivityUtil.getActivity(), ActivityUtil.getActivity().getString(R.string.oops_network_content), ActivityUtil.getActivity().getString(R.string.oops_network_second_content));

        } else {
            OopsViewDialog.getInstance().show(ActivityUtil.getActivity(), ActivityUtil.getActivity().getString(R.string.oops_network_content), ActivityUtil.getActivity().getString(R.string.oops_network_second_content));

        }

    }


    private Map<String, String> getMap(Map<String, String> map) {
        if (!map.containsKey("sign")) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getValue() != null && !entry.getValue().equals("")) {
                    if (entry.getValue().contains("&")) {
                        entry.setValue(URLEncoder.encode(entry.getValue()));
                    }
                } else {
                    entry.setValue("");
                }

            }
            String time = TimeUtil.getTimeMillis();
            String sign = MapSortUtil.getInstance().getNetWorkMd5String(map, time);
            map.put("time", time);
            map.put("sign", sign);
        }
        return map;
    }

    private Map<String, String> checkLocalgetMap(Map<String, String> map) {
        map.put("_token", DataManager.AuthTokenHolder);
        if (!map.containsKey("sign")) {
            String time = TimeUtil.getTimeMillis();
            String sign = MapSortUtil.getInstance().getNetWorkMd5String(time);
            map.put("time", time);
            map.put("sign", sign);
        }
        return map;
    }

    private Map<String, String> noDetailsGetMap(Map<String, String> map, String details) {
        if (DataManager.AuthTokenHolder == null || "".equalsIgnoreCase(DataManager.AuthTokenHolder)) {
            if (ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken) == null || ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken).equalsIgnoreCase("")) {
                DataManager.AuthTokenHolder = SharedPreferencesUtils.getInstance(ActivityUtil.getActivity()).getData(Constant.fingerAuthToken);
            } else {
                DataManager.AuthTokenHolder = ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken);
            }
        }
        map.put("_token", DataManager.AuthTokenHolder);
        map.put("details", details);
        if (!map.containsKey("sign")) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getValue() != null && !entry.getValue().equals("")) {
                    if (entry.getValue().contains("&")) {
                        entry.setValue(URLEncoder.encode(entry.getValue()));
                    }
                } else {
                    entry.setValue("");
                }

            }
            String time = TimeUtil.getTimeMillis();
            String sign = MapSortUtil.getInstance().getNetWorkMd5String(map, time);
            map.put("time", time);
            map.put("sign", sign);
        }
        return map;
    }

    public void greenPostResponse(final String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        params = getGreenMap(params);
        if (!isCustom) {
            WaitingDialogUtil.show(ActivityUtil.getActivity());
        }
        try {

            NetWorkUtil.getInstance().startPostResponse(url, params, getHeader(), new NetWorkCallback() {
                @Override
                public void onResponse(Object response) {
                    if (!isCustom) {
                        WaitingDialogUtil.cancel();
                    }
                    if (response != null) {
                        try {
                            JSONObject json;
                            json = new JSONObject((String) response);
                            if (!json.isNull("result")) {
                                if (isSuccess(ActivityUtil.getActivity(), json.optString("result"))) {
                                    if (!json.isNull("status")) {
                                        if (isRight(ActivityUtil.getActivity(), json.optString("status"), json.optString("message"))) {

                                            try {
                                                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                                callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                            }
                                            catch (Exception ex){
                                                System.out.println("APIManagerUtil exception " + ex);
                                            }
                                        }
                                    } else {
                                        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                        callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                    }
                                }
                            } else if (!json.isNull("code")) {
                                if (isRight(ActivityUtil.getActivity(), json.optString("code"), json.optString("msg"))) {
                                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                    callback.onResponse(gson.fromJson(json.toString(), requestModel));
                                } else {
                                    callback.onFailure(json.optString("msg"));
                                }

                            } else {
                                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                                callback.onResponse(gson.fromJson(json.toString(), requestModel));
                            }
                        } catch (Exception e) {
                            callback.onFailure(e.getLocalizedMessage());

                            e.printStackTrace();

                        }
                    } else {
                        callback.onFailure("Data is Empty");
                    }
                }

                @Override
                public void onFailure(String message) {
                    WaitingDialogUtil.cancel();
                    callback.onFailure(message);
                }

            });
        } catch (Exception e) {
            WaitingDialogUtil.cancel();
            e.printStackTrace();
            callback.onFailure(e.getLocalizedMessage());
        }

    }

    public void greenGetResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        params = getGreenMap(params);
        WaitingDialogUtil.show(ActivityUtil.getActivity());
        try {

            Map<String, String> header = new HashMap<>();
            header.put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            header.put("Key", Constant.GREEN_APP_KEY);
            //parameter need to be add here for language ..
            NetWorkUtil.getInstance().startGetResponse(url, params, header, new NetWorkCallback() {
                @Override
                public void onResponse(Object response) {
                    WaitingDialogUtil.cancel();
                    if (response != null) {
                        try {
                            JSONObject json = new JSONObject((String) response);
                            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                            callback.onResponse(gson.fromJson(json.toString(), requestModel));
                        } catch (Exception e) {
                            callback.onFailure(e.getLocalizedMessage());

                            e.printStackTrace();

                        }
                    } else {
                        callback.onFailure("data is empty");
                    }
                }

                @Override
                public void onFailure(String message) {
                    WaitingDialogUtil.cancel();
                    callback.onFailure(message);
                }

            });
        } catch (Exception e) {
            WaitingDialogUtil.cancel();
            e.printStackTrace();
            callback.onFailure(e.getLocalizedMessage());
        }

    }

    public void geoGetResponse(String url, Map<String, String> params, final NetWorkCallback callback, final Class requestModel) {
        params = getGreenMap(params);
        WaitingDialogUtil.show(ActivityUtil.getActivity());
        try {
            Map<String, String> header = new HashMap<>();
            header.put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            header.put("Key", Constant.GREEN_APP_KEY);
//            header.put("Auth-Token", DataManager.AuthTokenHolder);
            NetWorkUtil.getInstance().startGetResponse(url, params, header, new NetWorkCallback() {
                @Override
                public void onResponse(Object response) {
                    WaitingDialogUtil.cancel();
                    if (response != null) {
                        try {
                            JSONObject json = new JSONObject((String) response);
                            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullDoubleToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullBooleanToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullIntgerToEmptyAdapterFactory()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                            callback.onResponse(gson.fromJson(json.toString(), requestModel));
                        } catch (Exception e) {
                            callback.onFailure(e.getLocalizedMessage());

                            e.printStackTrace();

                        }
                    } else {
                        callback.onFailure("data is empty");
                    }
                }

                @Override
                public void onFailure(String message) {
                    WaitingDialogUtil.cancel();
                    callback.onFailure(message);
                }

            });
        } catch (Exception e) {
            WaitingDialogUtil.cancel();
            e.printStackTrace();
            callback.onFailure(e.getLocalizedMessage());
        }

    }

    private Map<String, String> noQrcodeGetMap(Map<String, String> map, String qrcode) {
        if (DataManager.AuthTokenHolder == null || "".equalsIgnoreCase(DataManager.AuthTokenHolder)) {
            if (ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken) == null || ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken).equalsIgnoreCase("")) {
                DataManager.AuthTokenHolder = SharedPreferencesUtils.getInstance(ActivityUtil.getActivity()).getData(Constant.fingerAuthToken);
            } else {
                DataManager.AuthTokenHolder = ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken);
            }
        }
        map.put("_token", DataManager.AuthTokenHolder);
        map.put("qrcode", qrcode);
        if (!map.containsKey("sign")) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getValue() != null && !entry.getValue().equals("")) {
                    if (entry.getValue().contains("&")) {
                        entry.setValue(URLEncoder.encode(entry.getValue()));
                    }
                } else {
                    entry.setValue("");
                }

            }
            String time = TimeUtil.getTimeMillis();
            String sign = MapSortUtil.getInstance().getNetWorkMd5String(map, time);
            map.put("time", time);
            map.put("sign", sign);
        }
        return map;
    }

    public static Map<String, String> getGreenMap(Map<String, String> map) {
        if (!map.containsKey("sign")) {
            String time = TimeUtil.getTimeMillis();
            if (DataManager.AuthTokenHolder == null || "".equalsIgnoreCase(DataManager.AuthTokenHolder)) {
                if (ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken) == null || ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken).equalsIgnoreCase("")) {
                    DataManager.AuthTokenHolder = SharedPreferencesUtils.getInstance(ActivityUtil.getActivity()).getData(Constant.fingerAuthToken);
                } else {
                    DataManager.AuthTokenHolder = ACacheUtil.get(ActivityUtil.getActivity()).getAsString(Constant.authToken);
                }
            }
            map.put("_token", DataManager.AuthTokenHolder);
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getValue() != null && !entry.getValue().equals("")) {
                    if (entry.getValue().contains("&")) {
                        entry.setValue(URLEncoder.encode(entry.getValue()));
                    }
                } else {
                    entry.setValue("");
                }

            }
            String sign = MapSortUtil.getInstance().getNetWorkMd5String(map, time);
            map.put("time", time);
            map.put("sign", sign);
        }
        return map;
    }



    public static Map<String, String> getHeader() {

        String languageToLoad = DataManager.UserLocale; // your language
         String currentlanguage;
        if (languageToLoad.equals("en")) {
            currentlanguage = "en";
        }
        else if (languageToLoad.equals("zh")) {
            currentlanguage = "cn";
        }
        else {
            currentlanguage = "my";
        }


        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("Key", Constant.GREEN_APP_KEY);
        //    need to add localization here
        header.put("Localization",currentlanguage);
        return header;
    }
}
