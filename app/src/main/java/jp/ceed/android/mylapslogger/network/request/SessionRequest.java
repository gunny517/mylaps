package jp.ceed.android.mylapslogger.network.request;

import android.content.Context;

import java.util.Locale;
import java.util.Map;

import jp.ceed.android.mylapslogger.network.AbstractRetrofitGsonRequest;
import jp.ceed.android.mylapslogger.network.RequestParameter;
import jp.ceed.android.mylapslogger.network.response.SessionsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class SessionRequest extends AbstractRetrofitGsonRequest<SessionsResponse> {

    private static final String END_POINT = "https://practice-api.speedhive.com";

    private static final String PATH_FORMAT = "api/v1/training/activities/%s/sessions";

    private static final String AUTH_FORMAT = "bearer %s";

    @RequestParameter(serialiseName = "optimized")
    private String optimized = "false";

    public String sessionId;

    public String authorization;

    @Override
    public void executeRequest(Context context, Callback<SessionsResponse> callback) {
        String path = String.format(Locale.JAPAN, PATH_FORMAT, sessionId);
        String auth = String.format(Locale.JAPAN, AUTH_FORMAT, authorization);
        getRestAdapter(context, END_POINT).create(SessionService.class)
                .getSessionResults(auth, API_KEY, path, toParamMap(), callback);

    }

    private interface SessionService {

        @GET("/{path}")
        void getSessionResults(
                @Header("Authorization") String auth,
                @Header("ApiKey") String apiKey,
                @Path(value = "path", encode = false) String path,
                @QueryMap Map<String, String> map,
                Callback<SessionsResponse> callback);
    }

}
