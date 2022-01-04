package jp.ceed.android.mylapslogger.network;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 *
 */
public class RetrofitCacheUtil {

    public static final String CACHE_CONTROL = "Cache-Control";

    public static final String CACHE_CONTROL_FORMAT = "max-age=%d, only-if-cached, max-stale=%d";

    public static final String CACHE_DIR = "pkg_bundle_responses";

    public static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB

    private static Interceptor createInterCepter(final int cacheTimeSec) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .header(CACHE_CONTROL, String.format(Locale.getDefault(), CACHE_CONTROL_FORMAT, cacheTimeSec, 0))
                        .build();
            }
        };
    }

    /**
     * @param context
     * @param okHttpClient
     * @return
     */
    public static OkHttpClient setCache(Context context, OkHttpClient okHttpClient, final int cacheTimeSec) {
        if (context == null || okHttpClient == null) {
            return null;
        }
        okHttpClient.networkInterceptors().add(createInterCepter(cacheTimeSec));
        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_DIR);
        int cacheSize = CACHE_SIZE;
        Cache cache;
        cache = new Cache(httpCacheDirectory, cacheSize);
        okHttpClient.setCache(cache);
        return okHttpClient;
    }

}
