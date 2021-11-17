package app.service;

import app.model.GameResultDao;
import app.util.HttpUtil;
import app.util.JacksonUtil;
import okhttp3.Request;
import okhttp3.Response;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebLeaderBoardGetService extends AbstractWebService implements  LeaderBoardGetService{

    public WebLeaderBoardGetService(String endpoint) {
        super(endpoint);
    }

    @Override
    public List<GameResultDao> get(int firstN) {
        Request request = new Request.Builder()
                .url(HttpUtil.getUrl(endpoint))
                .addHeader("N", String.valueOf(firstN))
                .get()
                .build();

        try {
            Response response = HttpUtil.getOkHttpClient().newCall(request).execute();
            GameResultDao[] results = JacksonUtil.readValue(response.body().string(), GameResultDao[].class);

            return List.of(results);

        } catch (IOException | NullPointerException e) {
            Logger.error("leaderboard get service is unavailable");
            Logger.error(request.url());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<GameResultDao> get(String username, int firstN) {
        Request request = new Request.Builder()
                .url(HttpUtil.getUrl(String.format("%s/%s", endpoint, username)))
                .addHeader("N", String.valueOf(firstN))
                .get()
                .build();

        try {
            Response response = HttpUtil.getOkHttpClient().newCall(request).execute();
            GameResultDao[] results = JacksonUtil.readValue(response.body().string(), GameResultDao[].class);

            return List.of(results);

        } catch (IOException | NullPointerException e) {
            Logger.error("leaderboard get service is unavailable");
            Logger.error(request.url());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
