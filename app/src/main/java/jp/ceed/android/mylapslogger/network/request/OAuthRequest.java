package jp.ceed.android.mylapslogger.network.request;

import android.content.Context;
import android.util.Base64;

import jp.ceed.android.mylapslogger.R;
import jp.ceed.android.mylapslogger.network.AbstractRetrofitGsonRequest;
import jp.ceed.android.mylapslogger.network.response.OAuthResponse;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by ARAKI on 2017/05/01.
 */

public class OAuthRequest extends AbstractRetrofitGsonRequest<OAuthResponse> {

    private static final String GRANT_TYPE = "password";

    public String userName;

    public String password;

    @Override
    public void executeRequest(Context context, Callback<OAuthResponse> callback) {
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(
                context.getString(R.string.oauth_basic_auth_user),
                context.getString(R.string.oauth_basic_auth_pass)
        );
        RestAdapter restAdapter = getRestAdapter(context,
                context.getString(R.string.oauth_end_point), null, interceptor);
        restAdapter.create(OAuthService.class).getToken(GRANT_TYPE, userName, password, callback);
    }

    private interface OAuthService {

        @FormUrlEncoded
        @POST("/oauth/token")
        void getToken(
                @Field("grant_type") String grantType,
                @Field("username") String userName,
                @Field("password") String password,
                Callback<OAuthResponse> callback);
    }

    private static class BasicAuthInterceptor implements RequestInterceptor {

        private String basic;

        public BasicAuthInterceptor(String userName, String password) {
            String credencials = userName + ":" + password;
            basic = "Basic " + Base64.encodeToString(credencials.getBytes(), Base64.NO_WRAP);
        }

        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Authorization", basic);
        }
    }

}
