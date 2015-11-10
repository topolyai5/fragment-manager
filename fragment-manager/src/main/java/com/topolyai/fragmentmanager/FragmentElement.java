package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;

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
