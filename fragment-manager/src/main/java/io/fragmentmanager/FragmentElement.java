package io.fragmentmanager;

import android.support.v4.app.Fragment;

public class FragmentElement {
    private String name;
    private Fragment fragment;
    private boolean addtoBackStack;
    private boolean popped;

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

    public boolean isPopped() {
        return popped;
    }

    public void setPopped(boolean popped) {
        this.popped = popped;
    }
}
