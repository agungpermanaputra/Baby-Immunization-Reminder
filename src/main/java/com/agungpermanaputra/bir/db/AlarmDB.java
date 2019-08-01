package com.agungpermanaputra.bir.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {CampakEntity.class, DptEntity.class, PolioEntity.class, BcgEntity.class, HepatitisEntity.class}, version = 1, exportSchema = false)
public abstract class AlarmDB extends RoomDatabase {
    public abstract AlarmDAO getAlarmDao();

}


