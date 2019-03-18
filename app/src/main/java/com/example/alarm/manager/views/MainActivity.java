package com.example.alarm.manager.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.alarm.manager.model.AlarmEntity;
import com.example.alarm.manager.adapters.AlarmListAdapter;
import com.example.alarm.manager.services.AlarmService;
import com.example.alarm.manager.util.ServiceUtil;
import com.example.alarm.manager.view_model.AlarmViewModel;
import com.example.alarm.manager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    AlarmViewModel alarmViewModel;
    AlarmListAdapter adapter;
    RecyclerView alarmRV;
    List<AlarmEntity> dataList;
    FloatingActionButton addAlarm;
    TextView noAlarmMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmRV = findViewById(R.id.recycler_view);
        addAlarm = findViewById(R.id.add_alarm);
        noAlarmMessage = findViewById(R.id.no_alarm_message);
        dataList = new ArrayList<>();
        adapter = new AlarmListAdapter(this, dataList);
        alarmRV.setLayoutManager(new LinearLayoutManager(this));
        alarmRV.setAdapter(adapter);

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, new Observer<List<AlarmEntity>>() {
            @Override
            public void onChanged(List<AlarmEntity> alarmEntities) {

                dataList.clear();
                dataList.addAll(alarmEntities);
                adapter.notifyDataSetChanged();
                if (dataList.size() > 0) {
                    alarmRV.setVisibility(View.VISIBLE);
                    noAlarmMessage.setVisibility(View.GONE);
                } else {
                    alarmRV.setVisibility(View.GONE);
                    noAlarmMessage.setVisibility(View.VISIBLE);
                }
            }
        });
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
                startActivity(intent);
            }
        });


        adapter.setItemClick(new AlarmListAdapter.OnItemClick() {
            @Override
            public void onItemClicked(AlarmEntity alarmEntity) {
                Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
                intent.putExtra("data", alarmEntity);

                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (ServiceUtil.isMyServiceRunning(this, AlarmService.class)) {
            Intent intent = new Intent(this, AlarmService.class);
            stopService(intent);
        }
        super.onDestroy();


    }
}
