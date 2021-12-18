package jp.ceed.android.mylapslogger.network;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Request cancelable object.
 */
public class CancelableCallback<T> implements Callback<T> {

	ApiListener<T> listener;
	boolean canceled;

	public CancelableCallback(ApiListener<T> listener){
		this.listener = listener;
	}

	@Override
	public void success(T t, Response response) {
		if (listener == null || canceled) return;

		listener.onResponse(t, response);
	}

	@Override
	public void failure(RetrofitError error) {
		if (listener == null || canceled) return;

		listener.onError(error);
	}

	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled(){
		return canceled;
	}
}