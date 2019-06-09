package com.example.zadanie3_kuladecyzyjna;


import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    int start;
    int end;
    int diff;
    TextView textView;
    ImageView imageView;
    String []odpowiedzi;
    SensorManager sensorManager;
    Sensor sensor;


    final Animation.AnimationListener animationListener = new Animation.AnimationListener()
    {
        @Override
        public void onAnimationStart(Animation animation)
        {
            imageView.setImageResource(R.drawable.hw3ball_empty);
        }


        @Override
        public void onAnimationEnd(Animation animation) {
            imageView.setImageResource(R.drawable.hw3ball_empty);
            textView.setText(odpowiedzi[diff]);
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        odpowiedzi = getResources().getStringArray(R.array.answers);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor. TYPE_LIGHT);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        final Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cover);
        anim.setAnimationListener(animationListener);

        if (event.values[0] < 10)
        {
            textView.setText("");
            imageView.startAnimation(anim);
            start = (int) (event.timestamp * (1.0f / 1000000000.0f));
        }
        if ((event.values[0] > 10))
        {
            imageView.clearAnimation();
            end = (int) (event.timestamp * (1.0f / 1000000000.0f));
            diff = end-start;
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

}