package com.bignerdranch.android.geoquiz;

/**
 * Created by TheMi on 11/29/2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;

    public Question(int textResId, boolean answerTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnsweredTrue(boolean answered) {
        mAnswered = answered;
    }
}
