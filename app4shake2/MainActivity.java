package com.example.serdar.app4shake2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;

    private float sens_mevcut_deger; // mevcut sensor (ivme) degeri
    private float sensor_son_deger; // son sensor (ivme)degeri
    private float shake; // iki degerin yerçekiminden farkı (z ekseni - gravity)

    MediaPlayer media=null;
    int sure;

    boolean durum=false;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///////////////////////////////////////
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        sens_mevcut_deger=SensorManager.GRAVITY_EARTH;
        sensor_son_deger =SensorManager.GRAVITY_EARTH;
        shake=0.00f;
    }


    private final SensorEventListener sensorListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x= event.values[0];
            float y= event.values[1];
            float z= event.values[2];

            sensor_son_deger =sens_mevcut_deger;
            sens_mevcut_deger=(float) Math.sqrt( (double) (x*x + y*y + z*z) );
            float delta=(sens_mevcut_deger ) - (sensor_son_deger);
            shake=shake * 0.9f + delta;


            if (shake>11){
                Toast m_baslat=Toast.makeText(getApplicationContext(), "BAŞLATILDI...", Toast.LENGTH_SHORT);
                Toast m_durdur=Toast.makeText(getApplicationContext(), "durduruldu...", Toast.LENGTH_SHORT);
                if (media==null){

                    media=MediaPlayer.create(MainActivity.this,R.raw.deneme3);
                    media.start();
                    m_baslat.show();

                }
                else if(!media.isPlaying()){
                    media.seekTo(sure);
                    media.start();
                    m_baslat.show();
                }
                else {
                        media.pause();
                        sure=media.getCurrentPosition();
                        m_durdur.show();
                    }

                }
            }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}
