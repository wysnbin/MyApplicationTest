package com.example.administrator.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class AnswerActivity extends AppCompatActivity {
    private TextView mAnswerTextView;
    private Button mAboutbtn;
    private ImageView mImageView;
    private static final String EXTRA_ANSWER_SHOWN = "answer_shown";//建值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setContentView(R.layout.activity_answer);
        mAnswerTextView=findViewById(R.id.AnswerTextView);
        mImageView = findViewById(R.id.imageView);
        mAboutbtn = findViewById(R.id.AboutButton);
        Intent data = getIntent();
        String answer = data.getStringExtra("msg");//取出msg对应的数据
        mAnswerTextView.setText(answer);//显示到组件

        data.putExtra(EXTRA_ANSWER_SHOWN,"您已查看了答案");//保存要返回的值
        setResult(Activity.RESULT_OK,data); //返回响应
        mImageView.findViewById(R.id.imageView);

        Animator anim = AnimatorInflater.loadAnimator(this,R.animator.animator_alpha);
        anim.setTarget(mImageView);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //TODO 动画开始前
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TODO 动画结束时
                ValueAnimator moneryAnimator = ValueAnimator.ofFloat(0f,223823f);//float估值器
                moneryAnimator.setInterpolator(new LinearInterpolator());//插件器
                moneryAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float monery= (float) animation.getAnimatedValue();
                        mAnswerTextView.setText(String.format("%.2f元",monery));
                    }
                });
                //moneryAnimator.start();
                int startcolor = Color.parseColor("#ffdeaf");
                int endcolor = Color.parseColor("#ff4500");
                ValueAnimator colorAnimator = ValueAnimator.ofArgb(startcolor,endcolor);
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int color = (int) animation.getAnimatedValue();
                        mAnswerTextView.setTextColor(color);
                    }
                });
                AnimatorSet set = new AnimatorSet();
                set.playTogether(moneryAnimator,colorAnimator);
                set.setDuration(5000);
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //TODO 动画取消时

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //TODO 动画重复时
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//       mImageView.setImageResource(R.drawable.animation_fram);//将帧文件绑定到控件的图片文件中;
//        AnimationDrawable AnimationDrawable = (AnimationDrawable) mImageView.getDrawable(); //将图片资源文件传给动画对象
//        AnimationDrawable.start();//启动动画

    }
}
