package com.example.inf133;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class INF_133Activity extends Activity {
	
	private TextView mTextViewRotationLabel;
	private SensorManager mSensorManager;
	private SensorEventListener mEventListenerRotation;
	private float yawRotationValue;
	private float pitchRotationValue;
	private MediaPlayer mp;
	private AssetFileDescriptor afd;
	
	private void updateUI() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTextViewRotationLabel.setText(" "+yawRotationValue);
				if (yawRotationValue < -4) {
					afd = getApplicationContext().getResources().openRawResourceFd(R.raw.button1);
					mTextViewRotationLabel.setText("Looking down " + (int)yawRotationValue + " " + (int) pitchRotationValue);
				}
				else if (yawRotationValue  >= -4 && yawRotationValue < 4) {
					if (pitchRotationValue < -4) {
						afd = getApplicationContext().getResources().openRawResourceFd(R.raw.button3);
						mTextViewRotationLabel.setText("Looking right " + (int)yawRotationValue + " " + (int) pitchRotationValue);
					}
					else if (pitchRotationValue >= -4 && pitchRotationValue < 4) {
						afd = getApplicationContext().getResources().openRawResourceFd(R.raw.button4);
						mTextViewRotationLabel.setText("Looking straight " + (int)yawRotationValue + " " + (int) pitchRotationValue);
					}
					else if (pitchRotationValue >= 4) {
						afd = getApplicationContext().getResources().openRawResourceFd(R.raw.button5);
						mTextViewRotationLabel.setText("Looking left  " + (int)yawRotationValue + " " + (int) pitchRotationValue);
					}
				}
				else if (yawRotationValue >= 4) {
					afd = getApplicationContext().getResources().openRawResourceFd(R.raw.button7);
					mTextViewRotationLabel.setText("Looking up " + (int)yawRotationValue + " " + (int) pitchRotationValue);
				}
			}});
	}
	
	synchronized void playAudio(AssetFileDescriptor afd) {
		if (mp.isPlaying()) {
			return;
		}
		else {
			try {
				mp.reset();
				mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				mp.prepare();
				mp.start();
			} catch (IllegalArgumentException e) {
				Log.d("playAudio",""+e+"\n afd:"+afd.toString());
			} catch (IllegalStateException e) {
				Log.d("playAudio",""+e+"\n afd:"+afd.toString());
			} catch (IOException e) {
				Log.d("playAudio",""+e+"\n afd:"+afd.toString());
			}
			
		}
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_133);
        mTextViewRotationLabel = (TextView) findViewById(R.id.editText1);
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        yawRotationValue = 0;
		pitchRotationValue = 0;
        
		mEventListenerRotation = new SensorEventListener()	{
			
			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
			}
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				float [] values = event.values;
				yawRotationValue += values[0];
				pitchRotationValue += values[1];
				updateUI();
				
				playAudio(afd);
			}
			
			
		};
        
        mp = new MediaPlayer();
    }
	
	@Override
	public void onResume() {
		super.onResume();
		
		mSensorManager.registerListener(mEventListenerRotation, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mEventListenerRotation);
	}
	
	@Override
    public void onStop() {
		mSensorManager.unregisterListener(mEventListenerRotation);
		super.onStop();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inf_133, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
