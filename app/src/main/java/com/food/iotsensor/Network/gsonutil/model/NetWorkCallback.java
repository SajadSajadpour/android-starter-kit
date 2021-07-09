package com.food.iotsensor.Network.gsonutil.model;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 *
 */

public interface NetWorkCallback<T extends Object> {
    void onResponse(T response);

    void onFailure(String message);
}
