package jp.ceed.android.mylapslogger.network;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Listener for API's Service
 *
 * @param <T> parsing model type
 */
public interface ApiListener<T> {

	void onResponse(T t, Response response);

	void onError(RetrofitError error);
}