package com.example.alarm.manager.views;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm.manager.model.AlarmEntity;
import com.example.alarm.manager.view_model.AlarmViewModel;
import com.example.alarm.manager.R;
import com.example.alarm.manager.util.DateOperations;
import com.example.alarm.manager.util.TimerManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class AddAlarmActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button delete;
    EditText editText;
    Toolbar toolbar;
    AlarmViewModel alarmViewModel;

    TimerManager timerManager;
    int alarmEntityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        toolbar = findViewById(R.id.toolbar);
        editText = findViewById(R.id.editText);
        timePicker = findViewById(R.id.time_picker);

        delete = findViewById(R.id.delete);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().hasExtra("data")) {
            AlarmEntity alarmEntity = getIntent().getParcelableExtra("data");
            setDataOnView(alarmEntity);
            alarmEntityId = alarmEntity.getId();
        }
        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
        timerManager = new TimerManager(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_alarm_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:

                setAlarm(timePicker, alarmEntityId);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setAlarm(TimePicker timePicker, int id) {
        Calendar calSet = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            calSet.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        } else {
            calSet.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            calSet.set(Calendar.MINUTE, timePicker.getMinute());
        } else {
            calSet.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        }

        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.getTimeInMillis() < now.getTimeInMillis()) {
            calSet.add(Calendar.DATE, 1);
        }
        AlarmEntity alarmEntity = new AlarmEntity(String.valueOf(editText.getText()), String.valueOf(calSet.getTime()), true);
        if (id != -1) {
            alarmEntity.setId(id);
            timerManager.startTimer(calSet, alarmEntity.getId());//update Alarm
            alarmViewModel.update(alarmEntity);
            Toast.makeText(this, "Alarm Update...", Toast.LENGTH_SHORT).show();

        } else {
            alarmEntity.setAlarmID(getUniqueID());
            alarmViewModel.insert(alarmEntity);
            Toast.makeText(this, "Alarm Set...", Toast.LENGTH_SHORT).show();

        }


        finish();

    }


    private void setDataOnView(final AlarmEntity alarmEntity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(Integer.parseInt(DateOperations.getTime(alarmEntity.getAlarmTime()).split(":")[0]));
        } else {
            timePicker.setCurrentHour(Integer.parseInt(DateOperations.getTime(alarmEntity.getAlarmTime()).split(":")[0]));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(Integer.parseInt(DateOperations.getTime(alarmEntity.getAlarmTime()).split(":")[1]));
        } else {
            timePicker.setCurrentMinute(Integer.parseInt(DateOperations.getTime(alarmEntity.getAlarmTime()).split(":")[1]));

        }

        if (!"".equals(alarmEntity.getAlarmLabel()))
            editText.setText(alarmEntity.getAlarmLabel());
        else
            editText.setText(getString(R.string.label));
        delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmViewModel.delete(alarmEntity);
                timerManager.cancelTimer(alarmEntity.getAlarmID());
                finish();
            }
        });
    }


    private int getUniqueID() {
        Date now = new Date();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));

    }
}
