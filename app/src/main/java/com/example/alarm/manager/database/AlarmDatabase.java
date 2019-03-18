package com.example.alarm.manager.database;

import android.content.Context;

import com.example.alarm.manager.model.AlarmEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AlarmEntity.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {

    private static AlarmDatabase instance;

    public abstract AlarmDao alarmDao();

    public static synchronized AlarmDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AlarmDatabase.class, "alarm_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
