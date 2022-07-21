package jp.ceed.android.mylapslogger.network.request;

import android.content.Context;

import java.util.Locale;

import jp.ceed.android.mylapslogger.R;
import jp.ceed.android.mylapslogger.network.AbstractRetrofitGsonRequest;
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class ActivitiesRequest extends AbstractRetrofitGsonRequest<ActivitiesResponse> {

    public String userId;

    @Override
    public void executeRequest(Context context, Callback<ActivitiesResponse> callback) {
        String path = String.format(Locale.JAPAN, context.getString(R.string.activities_request_path), userId);
        getRestAdapter(context, context.getString(R.string.practice_api_end_point))
                .create(ActivitiesService.class)
                .getActivities(API_KEY, path, callback);
    }

    private interface ActivitiesService {

        @GET("/{path}")
        void getActivities(@Header("apiKey") String apiKey, @Path(value = "path", encode = false) String path, Callback<ActivitiesResponse> callback);
    }

}
