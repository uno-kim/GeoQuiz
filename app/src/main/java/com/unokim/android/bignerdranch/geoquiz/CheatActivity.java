package com.unokim.android.bignerdranch.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = CheatActivity.class.getSimpleName();

    private static final String EXTRA_ANSWER_IS_TRUE = "com.unokim.android.bignerdranch.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.unokim.android.bignerdranch.geoquiz.answer_shown";
    private static final String KEY_ANSWER_SHOWN = "answer_shown";

    private boolean mAnswerIsTrue;
    private boolean mWasAnswerShown;
    private TextView mAnswerTextView;
    private TextView mApiLevelTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerISTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerISTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mApiLevelTextView = (TextView) findViewById(R.id.apiLevelTextView);
        mApiLevelTextView.setText("APL Level " + Build.VERSION.SDK_INT);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
            }
        });
        if (savedInstanceState != null) {
            mWasAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN);
            showAnswer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
        outState.putBoolean(KEY_ANSWER_SHOWN, mWasAnswerShown);
    }

    private void showAnswer() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Log.d(TAG, "setAnswerShownResult() = " + isAnswerShown);
        mWasAnswerShown = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
