package com.example.app_teamproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Fragment3 extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private  int stepCount;

    private TextView sensorTextView;
    private Button resetButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment3, container, false);

        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        sensorTextView = rootView.findViewById(R.id.sensor);
        resetButton = rootView.findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStepCount();
            }
        });

        if (stepSensor == null) {
            Toast.makeText(getActivity(), "만보기 기능을 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (stepSensor != null) {
            sensorManager.unregisterListener(this, stepSensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
            updateStepCount();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 가속도계 센서의 정확도가 변경되었을 때 호출되는 콜백 메서드입니다.
    }

    private void updateStepCount() {
        sensorTextView.setText(String.valueOf(stepCount));
    }

    private void resetStepCount() {
        stepCount = 0;
        updateStepCount();
    }
}
