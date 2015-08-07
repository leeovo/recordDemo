package com.example.leeovo.recorddemo;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.leeovo.imageviewtest.R;

import java.io.File;

public class MainActivity extends Activity implements OnClickListener {


    File soundFile ;
    MediaRecorder mRecorder;

    private static final int STATUS_WORKING = 0;

    private static final int STATUS_STOP = 1;

    private ImageView imageView;

    private int status = STATUS_WORKING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.iv_btn_record);

        imageView.setOnClickListener(this);
    }



    @Override
    public void onDestroy()
    {
        Log.d("leeovojohn","destory1");
        if (soundFile != null && soundFile.exists())
        {
            // 停止录音
            mRecorder.stop();
            Log.d("leeovojohn", "destory2");
            // 释放资源
            mRecorder.release();
            Log.d("leeovojohn", "destory3");
            mRecorder = null;
        }
        super.onDestroy();
        Log.d("leeovojohn", "destory4");
    }

    public void recordStart(){

        if (!Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(MainActivity.this
                    , "SD卡不存在，请插入SD卡！"
                    , Toast.LENGTH_SHORT)
                    .show();
            Log.d("leeovojohn", "start1");
            return;
        }
        try
        {
            // 创建保存录音的音频文件
            soundFile = new File(Environment
                    .getExternalStorageDirectory()
                    .getCanonicalFile() + "/sound.amr"
                    + Log.d("leeovojohn", "start1.5"));
            Log.d("leeovojohn", "start2");
            mRecorder = new MediaRecorder();
            Log.d("leeovojohn", "start3");
            // 设置录音的声音来源
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC + Log.d("leeovojohn", "start4"));

            // 设置录制的声音的输出格式（必须在设置声音编码格式之前设置）
            mRecorder.setOutputFormat(MediaRecorder
                    .OutputFormat.THREE_GPP + Log.d("leeovojohn", "start5"));

            // 设置声音编码的格式
            mRecorder.setAudioEncoder(MediaRecorder
                    .AudioEncoder.AMR_NB + Log.d("leeovojohn", "start6"));
            mRecorder.setOutputFile(soundFile.getAbsolutePath());
            Log.d("leeovojohn", "start7");
            mRecorder.prepare();
            Log.d("leeovojohn", "start8");
            // 开始录音
            mRecorder.start();
            Log.d("leeovojohn", "start9");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("leeovojohn", "start10");
        }
    }




    public void recordSound(){

        switch (status){

            case STATUS_WORKING:

                recordStart();
                Log.d("leeovojohn", "click1");

                imageView.setBackgroundResource(R.drawable.record_round_red_bg);

                status = STATUS_STOP;

                break;
            case STATUS_STOP:

                if (soundFile != null && soundFile.exists())
                {
                    // 停止录音
                    mRecorder.stop();
                    Log.d("leeovojohn", "stop1");
                    // 释放资源
                    mRecorder.release();
                    mRecorder = null;
                }
                Log.d("leeovojohn", "click2");

                imageView.setBackgroundResource(R.drawable.record_round_blue_bg);

                status = STATUS_WORKING;

                break;

        }

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.iv_btn_record:

                recordSound();

                break;
            default:
                break;
        }
    }

}