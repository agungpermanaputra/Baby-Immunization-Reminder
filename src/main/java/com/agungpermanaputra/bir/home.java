package com.agungpermanaputra.bir;

import android.content.Intent;
import android.graphics.Color;
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


public class home extends Fragment {

    public WebView webView1;
    public WebView webView2;
    Button btnKeluar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View v =inflater.inflate(R.layout.fragment_home, container, false);

        webView1 = (WebView) v.findViewById(R.id.txt_imunisasi);
        String txt = getString(R.string.text);
        webView1.setBackgroundColor(Color.parseColor("#66C4BE"));
        webView1.loadData("<p style =\"text-align: justify\">" + txt +"</p>", "text/html", "UTF-8");


        webView2 = (WebView) v.findViewById(R.id.txt_manfaat);
        String txt1 = getString(R.string.text1);
        String txt2 = getString(R.string.text2);
        String txt3 = getString(R.string.text3);
        String txt4 = getString(R.string.text4);
        String txt5 = getString(R.string.text5);
        webView2.setBackgroundColor(Color.parseColor("#66C4BE"));
        webView2.loadData("<ul><li style = \"text-align: justify\">"+ txt1 +"</li>"+
                               "<li style = \"text-align: justify\">"+ txt2 +"</li>" +
                               "<li style = \"text-align: justify\">"+ txt3 +"</li>" +
                               "<li style = \"text-align: justify\">"+ txt4 +"</li>" +
                               "<li style = \"text-align: justify\">"+ txt5 +"</li></ul>",
                "text/html", "UTF-8");

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