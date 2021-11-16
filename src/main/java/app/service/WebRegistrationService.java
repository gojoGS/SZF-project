package app.service;

import app.util.HttpUtil;
import app.util.JacksonUtil;
import okhttp3.Request;
import okhttp3.Response;
import org.tinylog.Logger;

import java.io.IOException;

public class WebRegistrationService extends AbstractWebService implements RegistrationService {

    private static EncryptionService encryptionService = new Sha256EncryptionService();

    public WebRegistrationService(String endpoint) {
        super(endpoint);
    }

    @Override
    public RegistrationResponse register(String username, String password) {
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
                case "User already exists" -> RegistrationResponse.USER_ALREADY_EXISTS;
                case "Failed" -> RegistrationResponse.SUCCESSFUL;
                case "Successful" -> RegistrationResponse.SUCCESSFUL;
                default -> RegistrationResponse.SERVER_UNAVAILABLE;
            };

        } catch (IOException | NullPointerException e) {
            Logger.error("Registration service is unavailable");
            Logger.error(request.url());
            e.printStackTrace();
            return RegistrationResponse.SERVER_UNAVAILABLE;
        }
    }
}
