package com.example.alarm.manager.view_model;

import android.app.Application;

import com.example.alarm.manager.model.AlarmEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AlarmViewModel extends AndroidViewModel {
    private AlarmRepository repository;
    private LiveData<List<AlarmEntity>> allAlarms;
    public AlarmViewModel(@NonNull Application application) {
        super(application);

        repository=new AlarmRepository(application);
        allAlarms=repository.getAllAlarms();
    }

    public void insert(AlarmEntity entity) {
        repository.insert(entity);
    }

    public void update(AlarmEntity entity) {
        repository.update(entity);
    }

    public void delete(AlarmEntity entity) {
        repository.delete(entity);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<AlarmEntity>> getAllAlarms() {
        return allAlarms;
    }
}
