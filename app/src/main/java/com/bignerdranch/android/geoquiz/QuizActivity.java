package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class QuizActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat(".##");
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERS = "answers";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]
    {
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mCorrectAnswers = savedInstanceState.getInt(KEY_ANSWERS, 0);
        }

         mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mQuestionBank.length + (mCurrentIndex - 1)) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.wtf(TAG,"onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.wtf(TAG,"onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.wtf(TAG,"onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.wtf(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.wtf(TAG,"onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG,"onDestroy() called");
    }

    private void toggleButtons(boolean showButtons) {
        mTrueButton.setEnabled(showButtons);
        mFalseButton.setEnabled(showButtons);
    }

    private void isAnswered() {
        boolean answered = mQuestionBank[mCurrentIndex].isAnswered();

        if (answered) {
            toggleButtons(false);
        } else {
            toggleButtons(true);
        }
    }

    private void updateQuestion() {

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        isAnswered();
    }

    private void checkAnswer(boolean userPressedTrue) {

        toggleButtons(false);

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = R.string.incorrect_toast;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCorrectAnswers++;
        }

        mQuestionBank[mCurrentIndex].setAnsweredTrue(true);

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        calculateScore();
    }

    private void calculateScore() {

        if (isQuizComplete()) {

            double score = ((double)mCorrectAnswers / mQuestionBank.length) * 100;
            Toast.makeText(this, "Your Score is: " + df2.format(score) + "%", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isQuizComplete() {

        for(int i = 0; i < mQuestionBank.length; i++){

            if (mQuestionBank[i].isAnswered() == false) {
                return false;
            }
        }
        return true;
    }
}
