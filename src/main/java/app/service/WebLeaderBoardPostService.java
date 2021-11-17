package app.service;

import app.model.GameResultDao;
import app.util.HttpUtil;
import app.util.JacksonUtil;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WebLeaderBoardPostService extends AbstractWebService implements LeaderBoardPostService{
    public WebLeaderBoardPostService(String endpoint) {
        super(endpoint);
    }

    @Override
    public void post(GameResultDao gameResult) {

        try {
            Request request = new Request.Builder()
                    .url(HttpUtil.getUrl(endpoint))
                    .post(RequestBody.create(JacksonUtil.writeValue(gameResult).getBytes(StandardCharsets.UTF_8)))
                    .build();

            Response response = HttpUtil.getOkHttpClient().newCall(request).execute();


        } catch (IOException | NullPointerException e) {
            Logger.error("leaderboard post service is unavailable");
            e.printStackTrace();
        }
    }
}
