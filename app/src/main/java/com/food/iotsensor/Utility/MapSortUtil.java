package com.food.iotsensor.Utility;


import com.food.iotsensor.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class MapSortUtil {
    private static MapSortUtil mapSortUtil;

    public static MapSortUtil getInstance() {
        if (mapSortUtil == null) {
            mapSortUtil = new MapSortUtil();
        }
        return mapSortUtil;
    }

    /**
     * @Title: sortMap
     * @Description:  key
     */
    public List<Map.Entry<String, String>> sortMap(final Map<String, String> map) {
        final List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(map.entrySet());

         Collections.sort(infos, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(final Map.Entry<String, String> o1, final Map.Entry<String, String> o2) {

                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });


        return infos;
    }

    //
    public String getNetWorkMd5String(final Map<String, String> map, final String time) {
        List<Map.Entry<String, String>> list = new MapSortUtil().sortMap(map);
        StringBuilder stringBuilder = new StringBuilder();
        for (final Map.Entry<String, String> m : list) {
            stringBuilder.append(m.getValue());
        }
        stringBuilder.append(time);
        stringBuilder.append(Constant.md5Key);

        return Constant.MD5(stringBuilder.toString());
    }

    public String getNetWorkMd5String(final String time) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(time);
        stringBuilder.append(Constant.md5Key);

        return Constant.MD5(stringBuilder.toString());
    }
}
