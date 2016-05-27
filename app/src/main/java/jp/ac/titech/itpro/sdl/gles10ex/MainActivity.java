package jp.ac.titech.itpro.sdl.gles10ex;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
//import android.widget.SeekBar;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;


//public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final static String TAG = "MainActivity";

    private GLSurfaceView glView;
    private SimpleRenderer renderer;
//    private SeekBar rotationBarX, rotationBarY, rotationBarZ;

    private SensorManager sensorMgr;
    private Sensor accelerometer;

//    private final static long GRAPH_REFRESH_WAIT_MS = 20;

    private final int N = 5;
    private int dim = 3;
    private float [] [] ax = new float [dim] [N];
    private int [] idx = new int [dim];
    private float [] vl = new float[dim]; //vx, vy, vz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        glView = (GLSurfaceView) findViewById(R.id.glview);

/*
        rotationBarX = (SeekBar) findViewById(R.id.rotation_bar_x);
        rotationBarY = (SeekBar) findViewById(R.id.rotation_bar_y);
        rotationBarZ = (SeekBar) findViewById(R.id.rotation_bar_z);
        rotationBarX.setOnSeekBarChangeListener(this);
        rotationBarY.setOnSeekBarChangeListener(this);
        rotationBarZ.setOnSeekBarChangeListener(this);
*/ //seekbar

        //accelmove
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Toast.makeText(this, getString(R.string.toast_no_accel_error),
                   Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        for(int i=0;i<dim;i++) {
            idx[i] = 0;
        }

        renderer = new SimpleRenderer();
        renderer.addObj(new Cube(0.5f, 0, 0.2f, -3));
        renderer.addObj(new Pyramid(0.5f, 0, 0, 0));
        glView.setRenderer(renderer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
    }

    /* //seekbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == rotationBarX)
            renderer.setRotationX(progress);
        else if (seekBar == rotationBarY)
            renderer.setRotationY(progress);
        else if (seekBar == rotationBarZ)
            renderer.setRotationZ(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "onSensorChanged: ");
///*
        //x axis
        for(int i=0;i<dim;i++) {
            ax[i][idx[i]] = 10 * event.values[i];
            float sx = 0;
            for (int j = 0; j < N; j++)
                sx += ax[i][j];
            vl[i] = sx / N;
            idx[i] = (idx[i] + 1) % N;
        }
//        vx = 10 * event.values[0]; //default
//        vy = 10 * event.values[1];
//        vz = 10 * event.values[2];
        renderer.setRotationY(vl[0]);
        renderer.setRotationZ(vl[1]);
        renderer.setRotationX(vl[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accurary) {
        Log.i(TAG, "onAccuracyChanged: ");
    }


}
