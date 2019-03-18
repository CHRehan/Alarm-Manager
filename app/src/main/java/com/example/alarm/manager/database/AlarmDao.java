package com.example.alarm.manager.database;

import com.example.alarm.manager.model.AlarmEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AlarmDao {
    @Insert
    void insert(AlarmEntity entity);

    @Update
    void update(AlarmEntity entity);

    @Delete
    void delete(AlarmEntity entity);

    @Query("DELETE FROM user_alarms")
    void deleteAllAlarms();

    @Query("SELECT * FROM user_alarms")
    LiveData<List<AlarmEntity>> getAllAlarms();
}
