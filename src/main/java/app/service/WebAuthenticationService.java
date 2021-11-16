package app.service;


import app.util.HttpUtil;
import app.util.JacksonUtil;
import okhttp3.Request;
import okhttp3.Response;
import org.tinylog.Logger;

import java.io.IOException;

public class WebAuthenticationService extends AbstractWebService implements AuthenticationService {
    private static EncryptionService encryptionService = new Sha256EncryptionService();

    public WebAuthenticationService(String endpoint) {
        super(endpoint);
    }

    @Override
    public AuthenticationResponse authenticate(String username, String password) {
        Request request = new Request.Builder()
                .url(HttpUtil.getUrl(endpoint))
                .addHeader("Username", username)
                .addHeader("Password", encryptionService.encrypt(password))
                .get()
                .build();

        try {
            Response response = HttpUtil.getOkHttpClient().newCall(request).execute();
            StatusWrapper wrapper = JacksonUtil.readValue(response.body().string(), StatusWrapper.class);

            return switch (wrapper.getStatus()) {
                case "User not found" -> AuthenticationResponse.USER_NOT_FOUND;
                case "Failed" -> AuthenticationResponse.FAILED;
                case "Successful" -> AuthenticationResponse.SUCCESSFUL;
                default -> AuthenticationResponse.SERVER_UNAVAILABLE;
            };

        } catch (IOException | NullPointerException e) {
            Logger.error("Authentication service is unavailable");
            Logger.error(request.url());
            e.printStackTrace();
            return AuthenticationResponse.SERVER_UNAVAILABLE;
        }
    }
}
