#!/usr/bin/env python3
# coding: utf-8
import sqlite3 as sql
from result import GameResultDAO


class DbConnection():
    def __init__(self, path: str) -> None:
        self._path = path
        self._connection = sql.connect(path)
        self._cursor = self._connection.cursor()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb) -> None:
        self.close()

    def close(self, commit: bool = True):
        if commit:
            self.commit()

        self._connection.close()

    def commit(self):
        self._connection.commit()

    def execute(self, code: str, params=None):
        self._cursor.execute(code, params or ())

    def fetch_all(self):
        return self._cursor.fetchall()

    def fetch_one(self):
        return self._cursor.fetchone()

    def fetch_many(self, size: int):
        return self._cursor.fetchmany(size)

    def query(self, code: str, params=None):
        self.execute(code, params)
        return self.fetch_all()


class CustomDbConnection(DbConnection):
    def __init__(self, path: str) -> None:
        super().__init__(path)

    def put_user_data(self, username: str, password: str):
        self.execute(
            'INSERT INTO userdata (username, password_hash) VALUES (?,?);', (username, password))

    def get_password(self, username: str):
        self.execute(
            'SELECT password_hash FROM userdata WHERE username = ?', (username,))

        result = self.fetch_one()

        if result is not None:
            return result[0]
        else:
            return None

    def post_result(self, result: GameResultDAO):
        params = (result.username, result.numberOfQuestions,
                  result.gameLength, result.helpsRemaining, result.theme)

        self.execute(
            'INSERT INTO result(username, nummberOfQuestions, gameLength, helpsRemainings, theme) VALUES (?,?,?,?,?)', params)

    def get_best_n(self, n: int):
        self.execute('select * from result order by gameLength desc;')
        return self.fetch_many(n)

    def get_of_user_last_n(self, username: str, n: int):
        self.execute(
            'select * from result where username = ? order by ROWID DESC;', (username,))
        return self.fetch_many(n)

    def exists(self, username: str):
        result = self.query(
            'SELECT EXISTS(SELECT 1 FROM userdata WHERE username=? LIMIT 1);', (username,))

        return result[0][0] == 1


def main():
    pass


if __name__ == '__main__':
    main()
