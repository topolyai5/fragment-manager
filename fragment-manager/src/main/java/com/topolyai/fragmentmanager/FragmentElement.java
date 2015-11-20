package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;

public class FragmentElement {
    private String name;
    private Fragment fragment;
    private boolean addtoBackStack;


    public FragmentElement(String name, Fragment fragment, boolean addtoBackStack) {
        this.name = name;
        this.fragment = fragment;
        this.addtoBackStack = addtoBackStack;
    }

    public String getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddtoBackStack() {
        return addtoBackStack;
    }

    public void setAddtoBackStack(boolean addtoBackStack) {
        this.addtoBackStack = addtoBackStack;
    }
}
