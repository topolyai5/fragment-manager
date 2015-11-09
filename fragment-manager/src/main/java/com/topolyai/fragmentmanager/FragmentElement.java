package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;

/**
 * Created by geri on 7/9/2015.
 */
public class FragmentElement {
    private String name;
    private Fragment fragment;


    public FragmentElement(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
