package com.example.alarm.manager.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_alarms")
public class AlarmEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String alarmLabel;
    private String alarmTime;
    private  int alarmID;
    private boolean isAlarmActive;


    public AlarmEntity(String alarmLabel, String alarmTime, boolean isAlarmActive) {
        this.alarmLabel = alarmLabel;
        this.alarmTime = alarmTime;
        this.isAlarmActive = isAlarmActive;
    }


    protected AlarmEntity(Parcel in) {
        id = in.readInt();
        alarmLabel = in.readString();
        alarmTime = in.readString();
        alarmID = in.readInt();
        isAlarmActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(alarmLabel);
        dest.writeString(alarmTime);
        dest.writeInt(alarmID);
        dest.writeByte((byte) (isAlarmActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlarmEntity> CREATOR = new Creator<AlarmEntity>() {
        @Override
        public AlarmEntity createFromParcel(Parcel in) {
            return new AlarmEntity(in);
        }

        @Override
        public AlarmEntity[] newArray(int size) {
            return new AlarmEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isAlarmActive() {
        return isAlarmActive;
    }

    public void setAlarmActive(boolean alarmActive) {
        isAlarmActive = alarmActive;
    }


    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }
}
