package app.util;

import app.App;
import lombok.Getter;
import okhttp3.OkHttpClient;

public class HttpUtil {
    private static @Getter
    OkHttpClient okHttpClient = new OkHttpClient();

    public static String getUrl(String endpoint) {
        return String.format("%s/%s", App.getBackendBaseUrl(), endpoint);
    }
}