#!/usr/bin/env python3
# coding: utf-8

from typing import Optional, List
import fastapi
from fastapi import Header, Response
from db import CustomDbConnection
from result import GameResultDAO
from pydantic import BaseModel

app = fastapi.FastAPI()
db: CustomDbConnection


def create_table(db: CustomDbConnection, db_name: str, scheme: str):
    db.execute(
        f'CREATE TABLE IF NOT EXISTS {db_name} ({scheme});')


class ResponseModel(BaseModel):
    items: List[GameResultDAO]


@app.on_event("startup")
async def startup_event():
    global db
    db = CustomDbConnection('test.db')

    create_table(db, 'userdata',
                 'username TEXT PRIMARY KEY, password_hash TEXT NOT NULL')
    create_table(db, 'result', 'username TEXT NOT NULL, nummberOfQuestions INTEGER NOT NULL, gameLength INTEGER NOT NULL, helpsRemainings INTEGER NOT NULL, theme TEXT NOT NULL')


@app.on_event("shutdown")
def shutdown_event():
    global db
    db.close()


@app.post('/leaderboard')
async def post_leaderboard(result: GameResultDAO):
    global db

    try:
        db.post_result(result)
    except:
        return {"status": "Failed"}

    return {'status': 'Successful'}


@app.get('/leaderboard', )
async def get_leaderboard(n: Optional[int] = Header(None)):
    global db

    n = n if n else 10

    return [GameResultDAO.of(u, n, g, h, t) for (u, n, g, h, t) in db.get_best_n(n)]


@app.get('/leaderboard/{username}')
async def get_user_leaderboard(username: str, n: Optional[int] = Header(None)):
    global db

    n = n if n else 10

    return [GameResultDAO.of(u, n, g, h, t) for (u, n, g, h, t) in db.get_of_user_last_n(username, n)]


@app.get('/register')
async def register(username: Optional[str] = Header(None),
                   password: Optional[str] = Header(None)):
    if username is None or password is None:
        return {'status': 'Invalid request'}

    if db.exists(username):
        return {'status': 'User already exists'}

    db.put_user_data(username, password)

    return {'status': 'Successful'}


@app.get('/auth')
async def auth(username: Optional[str] = Header(None),
               password: Optional[str] = Header(None)):

    if username is None or password is None:
        return {'status': 'Invalid request'}

    global db

    if not db.exists(username):
        return {'status': 'User not found'}

    if password != db.get_password(username):
        return {'status': 'Failed'}

    return {'status': 'Successful'}


def main():
    pass


if __name__ == '__main__':
    main()
