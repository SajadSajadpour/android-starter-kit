package com.food.iotsensor.ViewUtility.tree;

import android.util.SparseArray;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class ItemConfig {

    private static SparseArray<Class<? extends TreeItem>> treeviewHolderTypes;

    static {
        treeviewHolderTypes = new SparseArray<>();
    }

    public static Class<? extends TreeItem> getTreeViewHolderType(int type) {
        return treeviewHolderTypes.get(type);
    }

    public static void addTreeHolderType(int type, Class<? extends TreeItem> clazz) {
        if (null == clazz) {
            return;
        }
        treeviewHolderTypes.put(type, clazz);
    }
}