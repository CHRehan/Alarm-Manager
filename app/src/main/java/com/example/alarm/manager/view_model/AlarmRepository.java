package com.example.alarm.manager.view_model;

import android.app.Application;
import android.os.AsyncTask;

import com.example.alarm.manager.database.AlarmDao;
import com.example.alarm.manager.database.AlarmDatabase;
import com.example.alarm.manager.model.AlarmEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<AlarmEntity>> allAlarms;

    AlarmRepository(Application application) {
        AlarmDatabase database = AlarmDatabase.getInstance(application);
        alarmDao = database.alarmDao();
        allAlarms = alarmDao.getAllAlarms();
    }

    void insert(AlarmEntity note) {
        new InsertAlarmAsyncTask(alarmDao).execute(note);
    }

    void update(AlarmEntity note) {
        new UpdateAlarmAsyncTask(alarmDao).execute(note);
    }

    void delete(AlarmEntity note) {
        new DeleteAlarmAsyncTask(alarmDao).execute(note);
    }

    void deleteAllNotes() {
        new DeleteAlarmAsyncTask(alarmDao).execute();
    }

    LiveData<List<AlarmEntity>> getAllAlarms() {
        return allAlarms;
    }

    private static class InsertAlarmAsyncTask extends AsyncTask<AlarmEntity, Void, Void> {
        private AlarmDao alarmDao;

        private InsertAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(AlarmEntity... alarmEntities) {
            alarmDao.insert(alarmEntities[0]);
            return null;
        }
    }
    private static class UpdateAlarmAsyncTask extends AsyncTask<AlarmEntity, Void, Void> {
        private AlarmDao alarmDao;

        private UpdateAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(AlarmEntity... alarmEntities) {
            alarmDao.update(alarmEntities[0]);
            return null;
        }
    }
    private static class DeleteAlarmAsyncTask extends AsyncTask<AlarmEntity, Void, Void> {
        private AlarmDao alarmDao;

        private DeleteAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(AlarmEntity... alarmEntities) {
            alarmDao.delete(alarmEntities[0]);
            return null;
        }
    }
    private static class DeleteAllAlarmAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao alarmDao;

        private DeleteAllAlarmAsyncTask(AlarmDao alarmDao) {
            this.alarmDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            alarmDao.deleteAllAlarms();
            return null;
        }
    }


}
