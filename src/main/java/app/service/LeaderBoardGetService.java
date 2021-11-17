package app.service;

import app.model.GameResultDao;

import java.util.List;

public interface LeaderBoardGetService {
    List<GameResultDao> get(int firstN);

    List<GameResultDao> get(String username, int firstN);
}
