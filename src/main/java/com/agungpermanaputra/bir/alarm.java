package com.agungpermanaputra.bir;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.agungpermanaputra.bir.db.AlarmDB;
import com.agungpermanaputra.bir.db.BcgEntity;
import com.agungpermanaputra.bir.db.CampakEntity;
import com.agungpermanaputra.bir.db.DptEntity;
import com.agungpermanaputra.bir.db.HepatitisEntity;
import com.agungpermanaputra.bir.db.PolioEntity;

import java.util.Calendar;
import java.util.Objects;


public class alarm extends Fragment {

    TextView tglHepatitis, jamHepatitis, tglBcg, jamBcg, tglPolio, jamPolio, tglDpt, jamDpt, tglcampak, jamCampak;
    Button addHepatitis,hapusHepatitis, hapusBcg, addBcg, addPolio, hapusPolio ,addDpt, hapusDpt, addCampak, hapusCampak, btnKeluar;
    private AlarmDB db;

    private int campakYear, campakDay, campakMonth, campakHour, campakMinute;
    private int dptYear, dptMonth, dptDay, dptHour, dptMinute;
    private int polioYear, polioMonth, polioDay, polioHour, polioMinute;
    private int bcgYear, bcgMonth, bcgDay, bcgHour, bcgMinute;
    private int hepatitisYear, hepatitisMonth, hepatitisDay, hepatitisHour, hepatitisMinute;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        //DATABASE
        db = Room.databaseBuilder(getContext(), AlarmDB.class, "alarmdb").allowMainThreadQueries().build();

        //ADD CAMPAK
        tglcampak = v.findViewById(R.id.detail_tanggal_campak);
        jamCampak = v.findViewById(R.id.detail_jam_campak);
        hapusCampak = v.findViewById(R.id.btn_hapus_campak);
        addCampak = v.findViewById(R.id.add_campak);
        addCampak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //SET DATE CAMPAK
                Calendar tanggal = Calendar.getInstance();
                campakYear = tanggal.get(Calendar.YEAR);
                campakMonth = tanggal.get(Calendar.MONTH);
                campakDay = tanggal.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                        String stringOfDate = days + "/" + (months + 1) + "/" + years;
                        campakDay = days;
                        campakMonth = months;
                        campakYear = years;
                        tglcampak.setText(tglcampak.getText() + "Tanggal : " + stringOfDate);
                    }
                }, campakYear, campakMonth, campakDay);
                datePickerDialog.setTitle("Date Picker");

                //SET TIME CAMPAK
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = selectedHour + ":" + selectedMinute;
                        campakHour = selectedHour;
                        campakMinute = selectedMinute;
                        jamCampak.setText(jamCampak.getText() + "Waktu : " + jam);

                        //INSERT DATABASE CAMPAK
                        CampakEntity campakEntity = new CampakEntity();
                        campakEntity.setJam(jamCampak.getText().toString());
                        campakEntity.setTgl(tglcampak.getText().toString());
                        insertData(campakEntity);

                        //NOTIFIKASI CAMPAK
                        Calendar current = Calendar.getInstance();
                        Calendar cal = Calendar.getInstance();
                        cal.set(campakYear,
                                campakMonth,
                                campakDay,
                                campakHour,
                                campakMinute,
                                00);

                        if (cal.compareTo(current) <= 0) {
                            //The set Date/Time already passed
                            Toast.makeText(getContext(),
                                    "Invalid Date/Time",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            setAlarmCampak(cal, "Campak");
                        }

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Time Picker");
                mTimePicker.show();
                datePickerDialog.show();

                hapusCampak.setVisibility(View.VISIBLE);
                addCampak.setVisibility(View.GONE);
                tglcampak.setVisibility(View.VISIBLE);
                jamCampak.setVisibility(View.VISIBLE);

            }

            //INSERT DATABASE CAMPAK
            private void insertData(final CampakEntity campakEntity) {
                new AsyncTask<Void, Void, Long>() {
                    @Override
                    protected Long doInBackground(Void... voids) {
                        long status = db.getAlarmDao().insertCampak(campakEntity);
                        return status;

                    }
                }.execute();
            }

        });

        //SHOW DATABASE CAMPAK
        CampakEntity[] alarms = db.getAlarmDao().getAllCampak();
        if (alarms.length > 0) {
            final CampakEntity campakEntity = alarms[0];
            if (campakEntity != null) {
                jamCampak.setText(campakEntity.getJam());
                tglcampak.setText(campakEntity.getTgl());

                hapusCampak.setVisibility(View.VISIBLE);
                addCampak.setVisibility(View.GONE);
                tglcampak.setVisibility(View.VISIBLE);
                jamCampak.setVisibility(View.VISIBLE);

                hapusCampak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAlarmDao().deleteCampak(campakEntity);
                        hapusCampak.setVisibility(View.GONE);
                        addCampak.setVisibility(View.VISIBLE);
                        tglcampak.setVisibility(View.GONE);
                        jamCampak.setVisibility(View.GONE);
                        tglcampak.setText("");
                        jamCampak.setText("");
                    }
                });
            }
        }

        //ADD DPT
        tglDpt = v.findViewById(R.id.detail_tanggal_dpt);
        jamDpt = v.findViewById(R.id.detail_jam_dpt);
        hapusDpt = v.findViewById(R.id.btn_hapus_dpt);
        addDpt = v.findViewById(R.id.add_dpt);
        addDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //SET DATE DPT
                Calendar tanggal = Calendar.getInstance();
                dptYear = tanggal.get(Calendar.YEAR);
                dptMonth = tanggal.get(Calendar.MONTH);
                dptDay = tanggal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                        String stringOfDate = days + "/" + (months+1) + "/" + years;
                        dptDay = days;
                        dptMonth = months;
                        dptYear = years;
                        tglDpt.setText(tglDpt.getText() + "Tanggal : " + stringOfDate);
                    }
                }, dptYear, dptMonth, dptDay);
                datePickerDialog.setTitle("Date Picker");

                //SET TIME DPT
                Calendar mcurrentTime = Calendar.getInstance();
                dptHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                dptMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = selectedHour + ":" + selectedMinute;
                        dptHour = selectedHour;
                        dptMinute = selectedMinute;
                        jamDpt.setText(jamDpt.getText() + "Waktu : " + jam);

                        //INSERT DATABASE DPT
                        DptEntity dptEntity = new DptEntity();
                        dptEntity.setJam(jamDpt.getText().toString());
                        dptEntity.setTgl(tglDpt.getText().toString());
                        insertDatadpt(dptEntity);

                        //NOTIFIKASI DPT
                        Calendar current = Calendar.getInstance();
                        Calendar cal = Calendar.getInstance();
                        cal.set(dptYear,
                                dptMonth,
                                dptDay,
                                dptHour,
                                dptMinute,
                                00);

                        if (cal.compareTo(current) <= 0) {
                            //The set Date/Time already passed
                            Toast.makeText(getContext(),
                                    "Invalid Date/Time",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            setAlarmDpt(cal, "DPT");
                        }
                    }
                }, dptHour, dptMinute, true);
                mTimePicker.setTitle("Time Picker");
                mTimePicker.show();
                datePickerDialog.show();

                hapusDpt.setVisibility(View.VISIBLE);
                addDpt.setVisibility(View.GONE);
                tglDpt.setVisibility(View.VISIBLE);
                jamDpt.setVisibility(View.VISIBLE);
            }

            //INSERT DATABASE CAMPAK
            private void insertDatadpt(final DptEntity dptEntity) {
                new AsyncTask<Void, Void, Long>() {
                    @Override
                    protected Long doInBackground(Void... voids) {
                        long status = db.getAlarmDao().insertDpt(dptEntity);
                        return status;

                    }
                }.execute();
            }

        });

        //SHOW DATABASE DPT
        DptEntity[] alarmdpt = db.getAlarmDao().getAllDpt();
        if (alarmdpt.length > 0) {
            final DptEntity dptEntity = alarmdpt[0];
            if (dptEntity != null) {
                jamDpt.setText(dptEntity.getJam());
                tglDpt.setText(dptEntity.getTgl());

                hapusDpt.setVisibility(View.VISIBLE);
                addDpt.setVisibility(View.GONE);
                tglDpt.setVisibility(View.VISIBLE);
                jamDpt.setVisibility(View.VISIBLE);

                hapusDpt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAlarmDao().deleteDpt(dptEntity);
                        hapusDpt.setVisibility(View.GONE);
                        addDpt.setVisibility(View.VISIBLE);
                        tglDpt.setVisibility(View.GONE);
                        jamDpt.setVisibility(View.GONE);
                        tglDpt.setText("");
                        jamDpt.setText("");
                    }
                });
            }
        }

            //ADD POLIO
            tglPolio = v.findViewById(R.id.detail_tanggal_polio);
            jamPolio = v.findViewById(R.id.detail_jam_polio);
            hapusPolio = v.findViewById(R.id.btn_hapus_polio);
            addPolio = v.findViewById(R.id.add_polio);
            addPolio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //SET DATE POLIO
                    Calendar tanggal = Calendar.getInstance();
                    polioYear = tanggal.get(Calendar.YEAR);
                    polioMonth = tanggal.get(Calendar.MONTH);
                    polioDay = tanggal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                            String stringOfDate = days + "/" + (months+1) + "/" + years;
                            polioDay = days;
                            polioMonth = months;
                            polioYear = years;
                            tglPolio.setText(tglPolio.getText() + "Tanggal : " + stringOfDate);
                        }
                    }, polioYear, polioMonth, polioDay);
                    datePickerDialog.setTitle("Date Picker");

                    //SET TIME POLIO
                    Calendar mcurrentTime = Calendar.getInstance();
                    polioHour= mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    polioMinute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String jam = selectedHour + ":" + selectedMinute;
                            polioHour = selectedHour;
                            polioMinute = selectedMinute;
                            jamPolio.setText(jamPolio.getText() + "Waktu : " + jam);

                            //INSERT DATABASE POLIO
                            PolioEntity polioEntity = new PolioEntity();
                            polioEntity.setJam(jamPolio.getText().toString());
                            polioEntity.setTgl(tglPolio.getText().toString());
                            insertDatapolio(polioEntity);

                            //NOTIFIKASI DPT
                            Calendar current = Calendar.getInstance();
                            Calendar cal = Calendar.getInstance();
                            cal.set(polioYear,
                                    polioMonth,
                                    polioDay,
                                    polioHour,
                                    polioMinute,
                                    00);

                            if (cal.compareTo(current) <= 0) {
                                //The set Date/Time already passed
                                Toast.makeText(getContext(),
                                        "Invalid Date/Time",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                setAlarmPolio(cal, "Polio");
                            }
                        }
                    }, polioHour, polioMinute, true);
                    mTimePicker.setTitle("Time Picker");
                    mTimePicker.show();
                    datePickerDialog.show();

                    hapusPolio.setVisibility(View.VISIBLE);
                    addPolio.setVisibility(View.GONE);
                    tglPolio.setVisibility(View.VISIBLE);
                    jamPolio.setVisibility(View.VISIBLE);
                }
                //INSERT DATABASE POLIO
                private void insertDatapolio(final PolioEntity polioEntity) {
                    new AsyncTask<Void, Void, Long>() {
                        @Override
                        protected Long doInBackground(Void... voids) {
                            long status = db.getAlarmDao().insertPolio(polioEntity);
                            return status;

                        }
                    }.execute();
                }
            });

        //SHOW DATABASE POLIO
        PolioEntity[] alarmPolio = db.getAlarmDao().getAllPolio();
        if (alarmPolio.length > 0) {
            final PolioEntity polioEntity = alarmPolio[0];
            if (polioEntity != null) {
                jamPolio.setText(polioEntity.getJam());
                tglPolio.setText(polioEntity.getTgl());

                hapusPolio.setVisibility(View.VISIBLE);
                addPolio.setVisibility(View.GONE);
                tglPolio.setVisibility(View.VISIBLE);
                jamPolio.setVisibility(View.VISIBLE);

                hapusPolio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAlarmDao().deletePolio(polioEntity);
                        hapusPolio.setVisibility(View.GONE);
                        addPolio.setVisibility(View.VISIBLE);
                        tglPolio.setVisibility(View.GONE);
                        jamPolio.setVisibility(View.GONE);
                        tglPolio.setText("");
                        jamPolio.setText("");
                    }
                });
            }
        }

            //ADD BCG
            tglBcg = v.findViewById(R.id.detail_tanggal_bcg);
            jamBcg = v.findViewById(R.id.detail_jam_bcg);
            hapusBcg = v.findViewById(R.id.btn_hapus_bcg);
            addBcg = v.findViewById(R.id.add_bcg);
            addBcg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //SET DATE BCG
                    Calendar tanggal = Calendar.getInstance();
                    bcgYear = tanggal.get(Calendar.YEAR);
                    bcgMonth = tanggal.get(Calendar.MONTH);
                    bcgDay = tanggal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                            String stringOfDate = days + "/" + (months+1) + "/" + years;
                            bcgDay = days;
                            bcgMonth = months;
                            bcgYear = years;
                            tglBcg.setText(tglBcg.getText() + "Tanggal : " + stringOfDate);
                        }
                    }, bcgYear, bcgMonth, bcgDay);
                    datePickerDialog.setTitle("Date Picker");

                    //SET TIME BCG
                    Calendar mcurrentTime = Calendar.getInstance();
                    bcgHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    bcgMinute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String jam = selectedHour + ":" + selectedMinute;
                            bcgHour = selectedHour;
                            bcgMinute = selectedMinute;
                            jamBcg.setText(jamBcg.getText() + "Waktu : " + jam);

                            //INSERT DATABASE BCG
                            BcgEntity bcgEntity = new BcgEntity();
                            bcgEntity.setJam(jamBcg.getText().toString());
                            bcgEntity.setTgl(tglBcg.getText().toString());
                            insertDataBcg(bcgEntity);

                            //NOTIFIKASI BCG
                            Calendar current = Calendar.getInstance();
                            Calendar cal = Calendar.getInstance();
                            cal.set(bcgYear,
                                    bcgMonth,
                                    bcgDay,
                                    bcgHour,
                                    bcgMinute,
                                    00);

                            if (cal.compareTo(current) <= 0) {
                                //The set Date/Time already passed
                                Toast.makeText(getContext(),
                                        "Invalid Date/Time",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                setAlarmBcg(cal, "BCG");
                            }
                        }
                    }, bcgHour, bcgMinute, true);
                    mTimePicker.setTitle("Time Picker");
                    mTimePicker.show();
                    datePickerDialog.show();

                    hapusBcg.setVisibility(View.VISIBLE);
                    addBcg.setVisibility(View.GONE);
                    tglBcg.setVisibility(View.VISIBLE);
                    jamBcg.setVisibility(View.VISIBLE);
                }
                //INSERT DATABASE BCG
                private void insertDataBcg(final BcgEntity bcgEntity) {
                    new AsyncTask<Void, Void, Long>() {
                        @Override
                        protected Long doInBackground(Void... voids) {
                            long status = db.getAlarmDao().insertBcg(bcgEntity);
                            return status;

                        }
                    }.execute();
                }
            });
        //SHOW DATABASE BCG
        BcgEntity[] alarmBcg = db.getAlarmDao().getAllBcg();
        if (alarmBcg.length > 0) {
            final BcgEntity bcgEntity = alarmBcg [0];
            if (bcgEntity != null) {
                jamBcg.setText(bcgEntity.getJam());
                tglBcg.setText(bcgEntity.getTgl());

                hapusBcg.setVisibility(View.VISIBLE);
                addBcg.setVisibility(View.GONE);
                tglBcg.setVisibility(View.VISIBLE);
                jamBcg.setVisibility(View.VISIBLE);

                hapusBcg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAlarmDao().deleteBcg(bcgEntity);
                        hapusBcg.setVisibility(View.GONE);
                        addBcg.setVisibility(View.VISIBLE);
                        addBcg.setVisibility(View.GONE);
                        jamBcg.setVisibility(View.GONE);
                        tglBcg.setText("");
                        jamBcg.setText("");
                    }
                });
            }
        }

            //ADD HEPATITIS
            tglHepatitis = v.findViewById(R.id.detail_tanggal_hepatitis);
            jamHepatitis = v.findViewById(R.id.detail_jam_hepatitis);
            hapusHepatitis = v.findViewById(R.id.btn_hapus_hepatitis);
            addHepatitis = v.findViewById(R.id.add_hepatitis);
            addHepatitis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //SET DATE HEPATITIS
                    Calendar tanggal = Calendar.getInstance();
                    hepatitisYear= tanggal.get(Calendar.YEAR);
                    hepatitisMonth = tanggal.get(Calendar.MONTH);
                    hepatitisDay = tanggal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                            String stringOfDate = days + "/" + (months+1) + "/" + years;
                            hepatitisYear = years;
                            hepatitisMonth = months;
                            hepatitisDay = days;
                            tglHepatitis.setText(tglHepatitis.getText() + "Tanggal : " + stringOfDate);
                        }
                    }, hepatitisYear, hepatitisMonth, hepatitisDay);
                    datePickerDialog.setTitle("Date Picker");

                    //SET TIME HEPATITIS
                    Calendar mcurrentTime = Calendar.getInstance();
                    hepatitisHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    hepatitisMinute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String jam = selectedHour + ":" + selectedMinute;
                            hepatitisHour = selectedHour;
                            hepatitisMinute = selectedMinute;
                            jamHepatitis.setText(jamHepatitis.getText() + "Waktu : " + jam);

                            //INSERT DATABASE HEPATITIS
                            HepatitisEntity hepatitisEntity = new HepatitisEntity();
                            hepatitisEntity.setJam(jamHepatitis.getText().toString());
                            hepatitisEntity.setTgl(tglHepatitis.getText().toString());
                            insertDataHepatitis(hepatitisEntity);

                            //NOTIFIKASI HEPATITIS
                            Calendar current = Calendar.getInstance();
                            Calendar cal = Calendar.getInstance();
                            cal.set(hepatitisYear,
                                    hepatitisMonth,
                                    hepatitisDay,
                                    hepatitisHour,
                                    hepatitisMinute,
                                    00);

                            if (cal.compareTo(current) <= 0) {
                                //The set Date/Time already passed
                                Toast.makeText(getContext(),
                                        "Invalid Date/Time",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                setAlarmHepatitis(cal, "Hepatitis");
                            }

                        }
                    }, hepatitisHour, hepatitisMinute, true);
                    mTimePicker.setTitle("Time Picker");
                    mTimePicker.show();
                    datePickerDialog.show();

                    hapusHepatitis.setVisibility(View.VISIBLE);
                    addHepatitis.setVisibility(View.GONE);
                    tglHepatitis.setVisibility(View.VISIBLE);
                    jamHepatitis.setVisibility(View.VISIBLE);

                }

                //INSERT DATABASE HEPATITIS
                private void insertDataHepatitis(final HepatitisEntity hepatitisEntity) {
                    new AsyncTask<Void, Void, Long>() {
                        @Override
                        protected Long doInBackground(Void... voids) {
                            long status = db.getAlarmDao().insertHepatitis(hepatitisEntity);
                            return status;

                        }
                    }.execute();
                }
            });

        //SHOW DATABASE HEPATITIS
        HepatitisEntity[] alarmHepatitis = db.getAlarmDao().getAllHepatitis();
        if (alarmHepatitis.length > 0) {
            final HepatitisEntity hepatitisEntity= alarmHepatitis [0];
            if (hepatitisEntity != null) {
                jamHepatitis.setText(hepatitisEntity.getJam());
                tglHepatitis.setText(hepatitisEntity.getTgl());

                hapusHepatitis.setVisibility(View.VISIBLE);
                addHepatitis.setVisibility(View.GONE);
                tglHepatitis.setVisibility(View.VISIBLE);
                jamHepatitis.setVisibility(View.VISIBLE);

                hapusHepatitis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAlarmDao().deleteHepatitis(hepatitisEntity);
                        hapusHepatitis.setVisibility(View.GONE);
                        addHepatitis.setVisibility(View.VISIBLE);
                        addHepatitis.setVisibility(View.GONE);
                        jamHepatitis.setVisibility(View.GONE);
                        tglHepatitis.setText("");
                        jamHepatitis.setText("");
                    }
                });
            }
        }

            btnKeluar = v.findViewById(R.id.btn_keluar);
            btnKeluar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.menu_logout) {
                                Intent intent = new Intent(getActivity(), login.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });

            return v;
        }


    private void setAlarmCampak (Calendar cal, String nama) {
    Intent intent = new Intent(getContext(), AlarmReceiver.class);
    intent.putExtra("name", nama);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
}
    private void setAlarmDpt (Calendar cal, String nama) {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("name", nama);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
    private void setAlarmPolio (Calendar cal, String nama) {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("name", nama);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 3, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
    private void setAlarmBcg (Calendar cal, String nama) {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("name", nama);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 4, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private void setAlarmHepatitis (Calendar cal, String nama) {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("name", nama);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 5, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
