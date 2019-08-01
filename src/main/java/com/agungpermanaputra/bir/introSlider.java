package com.agungpermanaputra.bir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class introSlider extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment slide1 = new slide1();
        Fragment slide2 = new slide2();
        Fragment slide3 = new slide3();

        addSlide(slide1);
        addSlide(slide2);
        addSlide(slide3);

    }

    @Override
    public void onSkipPressed (Fragment currentFragment) {
        super.onSkipPressed (currentFragment);
        Intent intent = new Intent(introSlider.this, login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed (Fragment currentFragment) {
        super.onDonePressed (currentFragment);
        Intent intent = new Intent(introSlider.this, login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
