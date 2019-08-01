package com.agungpermanaputra.bir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TableLayout;


public class jadwal extends Fragment {

    public WebView hepatitis;
    public WebView bcg;
    public WebView polio;
    public WebView dpt;
    public WebView campak;
    Button btnKeluar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_jadwal, container, false);

        hepatitis = (WebView) v.findViewById(R.id.txt_hb);
        String hb = getString(R.string.hepatitis);
        hepatitis.loadData("<p style =\"text-align: justify\">" + hb +"</p>", "text/html", "UTF-8");

        bcg = (WebView) v.findViewById(R.id.txt_bcg);
        String bc = getString(R.string.bcg);
        bcg.loadData("<p style =\"text-align: justify\">" + bc +"</p>", "text/html", "UTF-8");

        polio = (WebView) v.findViewById(R.id.txt_polio);
        String pl = getString(R.string.polio);
        polio.loadData("<p style =\"text-align: justify\">" + pl +"</p>", "text/html", "UTF-8");

        dpt = (WebView) v.findViewById(R.id.txt_dpt);
        String dp = getString(R.string.dpt);
        dpt.loadData("<p style =\"text-align: justify\">" + dp +"</p>", "text/html", "UTF-8");

        campak = (WebView) v.findViewById(R.id.txt_campak);
        String cm = getString(R.string.campak);
        campak.loadData("<p style =\"text-align: justify\">" + cm +"</p>", "text/html", "UTF-8");

        Button btn_hb = v.findViewById(R.id.btn_hepatitis_a);
        Button btn_bcg = v.findViewById(R.id.btn_bcg);
        Button btn_polio = v.findViewById(R.id.btn_polio);
        Button btn_dpt = v.findViewById(R.id.btn_dpt);
        Button btn_campak = v.findViewById(R.id.btn_campak);

        final TableLayout tbl_hb = v.findViewById(R.id.tbl_hb);
        final TableLayout tbl_bcg = v.findViewById(R.id.tbl_bcg);
        final TableLayout tbl_polio = v.findViewById(R.id.tbl_polio);
        final TableLayout tbl_dpt = v.findViewById(R.id.tbl_dpt);
        final TableLayout tbl_campak = v.findViewById(R.id.tbl_campak);


        btn_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hepatitis.setVisibility(View.VISIBLE);
                tbl_hb.setVisibility(View.VISIBLE);
                bcg.setVisibility(View.GONE);
                tbl_bcg.setVisibility(View.GONE);
                polio.setVisibility(View.GONE);
                tbl_polio.setVisibility(View.GONE);
                dpt.setVisibility(View.GONE);
                tbl_dpt.setVisibility(View.GONE);
                campak.setVisibility(View.GONE);
                tbl_campak.setVisibility(View.GONE);
            }
        });

        btn_bcg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hepatitis.setVisibility(View.GONE);
                tbl_hb.setVisibility(View.GONE);
                bcg.setVisibility(View.VISIBLE);
                tbl_bcg.setVisibility(View.VISIBLE);
                polio.setVisibility(View.GONE);
                tbl_polio.setVisibility(View.GONE);
                dpt.setVisibility(View.GONE);
                tbl_dpt.setVisibility(View.GONE);
                campak.setVisibility(View.GONE);
                tbl_campak.setVisibility(View.GONE);
            }
        });

        btn_polio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hepatitis.setVisibility(View.GONE);
                tbl_hb.setVisibility(View.GONE);
                bcg.setVisibility(View.GONE);
                tbl_bcg.setVisibility(View.GONE);
                polio.setVisibility(View.VISIBLE);
                tbl_polio.setVisibility(View.VISIBLE);
                dpt.setVisibility(View.GONE);
                tbl_dpt.setVisibility(View.GONE);
                campak.setVisibility(View.GONE);
                tbl_campak.setVisibility(View.GONE);
            }
        });

        btn_dpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hepatitis.setVisibility(View.GONE);
                tbl_hb.setVisibility(View.GONE);
                bcg.setVisibility(View.GONE);
                tbl_bcg.setVisibility(View.GONE);
                polio.setVisibility(View.GONE);
                tbl_polio.setVisibility(View.GONE);
                dpt.setVisibility(View.VISIBLE);
                tbl_dpt.setVisibility(View.VISIBLE);
                campak.setVisibility(View.GONE);
                tbl_campak.setVisibility(View.GONE);
            }
        });

        btn_campak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hepatitis.setVisibility(View.GONE);
                tbl_hb.setVisibility(View.GONE);
                bcg.setVisibility(View.GONE);
                tbl_bcg.setVisibility(View.GONE);
                polio.setVisibility(View.GONE);
                tbl_polio.setVisibility(View.GONE);
                dpt.setVisibility(View.GONE);
                tbl_dpt.setVisibility(View.GONE);
                campak.setVisibility(View.VISIBLE);
                tbl_campak.setVisibility(View.VISIBLE);
            }
        });

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
}