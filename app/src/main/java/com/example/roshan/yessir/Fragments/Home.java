package com.example.roshan.yessir.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import com.example.roshan.yessir.R;

public class Home extends Fragment  {


    Animation Fade_in,Fade_out;
    ViewFlipper viewFlipper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  myView = inflater.inflate(R.layout.fragment_home,container,false);
        viewFlipper=(ViewFlipper) myView.findViewById(R.id.ViewFlipper);
        setHasOptionsMenu(true);
        viewFlipper.setAnimation(Fade_in);
        viewFlipper.setAnimation(Fade_out);
        viewFlipper.setAutoStart(true);
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(2000);
      //  getActivity().setTitle("Home");
        return myView;


    }




}
