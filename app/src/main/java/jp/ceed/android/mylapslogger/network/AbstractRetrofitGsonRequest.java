package jp.ceed.android.mylapslogger.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jp.ceed.android.mylapslogger.BuildConfig;
import jp.ceed.android.mylapslogger.util.LogUtil;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * RetrofitによるAPI通信処理を実装する基底クラス。
 *
 * @param <R> APIレスポンスを格納するGsonエンティティクラス。
 */
public abstract class AbstractRetrofitGsonRequest<R> implements Serializable {

    /**
     * The constant API_KEY.
     */
    public static final String API_KEY = "SpeedhiveAndroidApp-f3deaaed-2dbb-41be-a469-bb33be4de434";

    private static final int DEFAULT_CACHE_TIME_IN_SEC = 5;

    /**
     * Get connection time out int.
     *
     * @return the int
     */
    protected int getConnectionTimeOut() {
        return 30;
    }

    /**
     * Get read time out int.
     *
     * @return the int
     */
    protected int getReadTimeOut() {
        return 30;
    }

    /**
     * Get write time out int.
     *
     * @return the int
     */
    protected int getWriteTimeOut() {
        return 30;
    }

    /**
     * 実際のリクエストを実行するメソッド
     *
     * @param context  context.
     * @param callback 通信完了時のコールバック
     */
    public abstract void executeRequest(Context context, Callback<R> callback);

    /**
     * キャッシュ時間を返す。（必要に応じて実装クラスでオーバーライドする）
     *
     * @return cache time in second.
     */
    public int getCaCheTimeInSec() {
        return DEFAULT_CACHE_TIME_IN_SEC;
    }

    /**
     * 通信を行うためのRestAdapterを生成して返す。
     *
     * @param context  the context
     * @param endPoint the end point
     * @return rest adapter
     */
    public RestAdapter getRestAdapter(Context context, String endPoint) {
        return getRestAdapter(context, endPoint, null);
    }

    /**
     * 通信を行うためのRestAdapterを生成して返す。
     *
     * @param context       the context
     * @param endPoint      the end point
     * @param gsonConverter the gson converter
     * @return rest adapter
     */
    public RestAdapter getRestAdapter(Context context, String endPoint, GsonConverter gsonConverter) {
        return getRestAdapter(context, endPoint, gsonConverter, null);
    }

    /**
     * 通信を行うためのRestAdapterを生成して返す。
     *
     * @param context            the context
     * @param endPoint           the end point
     * @param gsonConverter      the gson converter
     * @param requestInterceptor the request interceptor
     * @return rest adapter
     */
    public RestAdapter getRestAdapter(Context context, String endPoint, GsonConverter gsonConverter, RequestInterceptor requestInterceptor) {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            try {
                // ホスト名検証をスキップする
                okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                // 証明書検証スキップする空の TrustManager
                final TrustManager[] manager = {new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        // do nothing
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        // do nothing
                    }
                }};
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, manager, null);
                okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new AndroidRuntimeException(e);
            }
        }
        okHttpClient = RetrofitCacheUtil.setCache(context, okHttpClient, getCaCheTimeInSec());
        okHttpClient.setConnectTimeout(getConnectionTimeOut(), TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(getReadTimeOut(), TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(getWriteTimeOut(), TimeUnit.SECONDS);
        OkClient okClient = new OkClient(okHttpClient);
        RestAdapter restAdapter;
        restAdapter = createRestAdapter(okClient, endPoint, gsonConverter, requestInterceptor);
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(LogLevel.FULL);
        }
        return restAdapter;
    }

    private RestAdapter createRestAdapter(OkClient okClient, String endPoint, GsonConverter gsonConverter, RequestInterceptor requestInterceptor) {
        GsonConverter converter = gsonConverter == null ? new GsonConverter(new GsonBuilder().create()) : gsonConverter;
        RestAdapter.Builder builer = new RestAdapter.Builder();
        builer.setEndpoint(endPoint);
        builer.setConverter(converter);
        builer.setClient(okClient);
        if (requestInterceptor != null) {
            builer.setRequestInterceptor(requestInterceptor);
        }
        return builer.build();
    }

    /**
     * To param map map.
     *
     * @param encode the encode
     * @return map
     */
    public Map<String, String> toParamMap(String encode) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                RequestParameter annotation = field.getAnnotation(RequestParameter.class);
                if (annotation == null) {
                    continue;
                }
                if (field.get(this) == null) {
                    continue;
                }
                String key = annotation.serialiseName();
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(this);
                    if (encode != null) {
                        value = new String(value.getBytes(encode), encode);
                    }
                    map.put(key, value);
                } else if (field.get(this) instanceof List) {
                    List<String> list = new ArrayList<>();
                    for (Object obj : (List) field.get(this)) {
                        list.add(String.valueOf(obj));
                    }
                    String value = TextUtils.join(",", list);
                    if (encode != null) {
                        value = new String(value.getBytes(encode), encode);
                    }
                    map.put(key, value);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return map;
    }

    /**
     * To param map map.
     *
     * @return パラメータのマップを生成して返す 。
     */
    public Map<String, String> toParamMap() {
        return toParamMap(null);
    }

    /**
     * To body string.
     *
     * @param encode the encode
     * @return string
     */
    public String toBody(String encode) {
        List<String> params = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                RequestParameter annotation = field.getAnnotation(RequestParameter.class);
                if (annotation == null) {
                    continue;
                }
                if (field.get(this) == null) {
                    continue;
                }
                String key = annotation.serialiseName();
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(this);
                    if (encode != null) {
                        value = new String(value.getBytes(encode), encode);
                    }
                    params.add(key + "=" + value);
                } else if (field.get(this) instanceof List) {
                    List<String> list = new ArrayList<>();
                    for (Object obj : (List) field.get(this)) {
                        list.add(String.valueOf(obj));
                    }
                    String value = TextUtils.join(",", list);
                    if (encode != null) {
                        value = new String(value.getBytes(encode), encode);
                    }
                    params.add(key + "=" + value);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return TextUtils.join("&", params);
    }
}
