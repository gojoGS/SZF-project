package app.service;

import app.model.GameResultDao;

public interface LeaderBoardPostService {
    void post(GameResultDao gameResult);
}
