package com.agungpermanaputra.bir.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface AlarmDAO {
    //CAMPAK
    @Query("SELECT * FROM alarmCampak")
    CampakEntity[] getAllCampak();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCampak(CampakEntity campakEntity);

    @Delete
    void deleteCampak(CampakEntity campakEntity);

    //DPT
    @Query("SELECT * FROM alarmDpt")
    DptEntity[] getAllDpt();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDpt(DptEntity dptEntity);

    @Delete
    void deleteDpt (DptEntity dptEntity);

    //POLIO
    @Query("SELECT * FROM alarmPolio")
    PolioEntity[] getAllPolio();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPolio(PolioEntity polioEntity);

    @Delete
    void deletePolio (PolioEntity polioEntity);

    //BCG
    @Query("SELECT * FROM alarmBcg")
    BcgEntity[] getAllBcg();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBcg(BcgEntity bcgEntity);

    @Delete
    void deleteBcg (BcgEntity bcgEntity);

    //HEPATITIS
    @Query("SELECT * FROM alarmHepatitis")
    HepatitisEntity[] getAllHepatitis();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHepatitis (HepatitisEntity hepatitisEntity);

    @Delete
    void deleteHepatitis (HepatitisEntity hepatitisEntity);
}
