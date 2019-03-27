package com.example.administrator.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private Button mButtonTreu;//正确按钮
    private Button mButtonFalse;//错误按钮
    private Button mButtonNext;//下一题按钮
    private Button mButtonAnswer;//查看答案按钮
    private TextView mQuestionTextView;
    private int mQuestionIndex;//下标
    private Question[] mQuestions=new Question[]{
            new Question(R.string.Q1,false),
            new Question(R.string.Q2,true),
            new Question(R.string.Q3,true),
            new Question(R.string.Q4,true),
            new Question(R.string.Q5,true),
            new Question(R.string.Q6,true),
            new Question(R.string.Q7,true),
            new Question(R.string.Q8,true)
    };
    private static final String TAG = "MainActivity";//日志来源
    private static final String KEY_INDEX="index";//索引键
    private static final int REQUEST_CODE_ANSWER = 10;//请求参数
    private TranslateAnimation mTranslateAnimation;//平移动画
    private void updateQueston(){
        int i = mQuestions[mQuestionIndex].getTextId();
        mQuestionTextView.setText(i);
        Animation set = AnimationUtils.loadAnimation(this,R.anim.animation_list);
        mQuestionTextView.startAnimation(set);
//        mTranslateAnimation = new TranslateAnimation(0,0,0,0);
//        mTranslateAnimation.setDuration(2000);
//        mTranslateAnimation.setRepeatCount(1);
//        mTranslateAnimation.setRepeatMode(Animation.REVERSE);
//        mQuestionTextView.startAnimation(mTranslateAnimation);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate创建界面");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            mQuestionIndex=savedInstanceState.getInt(KEY_INDEX);
            Log.d(TAG,"恢复当前KEY_INDEX="+savedInstanceState);

        }

        mButtonTreu = findViewById(R.id.buttonTrue);
        mButtonFalse = findViewById(R.id.buttonFalse);
        mButtonNext = findViewById(R.id.button_next);
        mButtonAnswer = findViewById(R.id.button_answer);
        mQuestionTextView = findViewById(R.id.QuestionTextView);
        updateQueston();
        mButtonTreu.setOnClickListener(new View.OnClickListener() {
            @Override
            //正确按钮控制器
            public void onClick(View v) {
                checkQuestion(true);
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            //错误按钮控制器
            public void onClick(View v) {
                checkQuestion(false);
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            //下一题按钮控制器
            public void onClick(View v) {
                mQuestionIndex = (mQuestionIndex+1)%mQuestions.length;
                updateQueston();
                mButtonNext.setEnabled(false);
                if(mQuestionIndex>=mQuestions.length-1){
                    mButtonNext.setText(R.string.buttonContinue);
                    updateDrawable(R.drawable.ic_reset);
                }
                if(mQuestionIndex==0){
                    mButtonNext.setText(R.string.buttonNext);
                    updateDrawable(R.drawable.ic_next);
                }
            }
        });
        mButtonAnswer = findViewById(R.id.button_answer);
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;
                if(mQuestions[mQuestionIndex].isAnswer()){//解析答案
                temp = "正确";
                }else{
                temp = "错误";
                }
                Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("msg",temp);
                startActivityForResult(intent,REQUEST_CODE_ANSWER);

             /*   if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},PackageManager.CERT_INPUT_SHA256);
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13822158160"));
                startActivity(intent);*/
            }
        });
    }
    private void checkQuestion(boolean userAnswer){
        boolean trueAnswer = mQuestions[mQuestionIndex].isAnswer();
        int message;
        if(userAnswer==trueAnswer){
            message=R.string.yes;
            mButtonNext.setEnabled(true);
        }else{
            message=R.string.no;
            mButtonNext.setEnabled(false);
        }
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }
        private void updateDrawable(int ImageID){
        Drawable d = getDrawable(ImageID);
        d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
        mButtonNext.setCompoundDrawables(null,null,d,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onCreates使界面可见");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onCreates界面离开前台");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onCreates界面不可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onCreates前台显示");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onCreates销毁界面可见");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onCreates界面回来可见");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX,mQuestionIndex);//形成建值对
        Log.d(TAG,"onSaveInstanceState()保存状态，当前KEY_INDEX="+outState.getInt(KEY_INDEX));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ANSWER && resultCode == Activity.RESULT_OK){
            String temp = data.getStringExtra("answer_shown");//取出answer_shown对应的数据
                Toast.makeText(MainActivity.this,temp,Toast.LENGTH_LONG).show();
        }
    }
}
