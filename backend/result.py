#!/usr/bin/env python3
# coding: utf-8
from pydantic import BaseModel


class GameResultDAO(BaseModel):
    username: str
    numberOfQuestions: int
    gameLength: int
    helpsRemaining: int
    theme: str

    @staticmethod
    def of(username: str, numberOfQuestions: int, gameLength: int, helpsRemaining: int, theme: str):

        return GameResultDAO(username=username, numberOfQuestions=numberOfQuestions,
                             gameLength=gameLength, helpsRemaining=helpsRemaining, theme=theme)


def main():
    pass


if __name__ == '__main__':
    main()
