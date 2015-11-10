package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentService {

    private FragmentManager fm;
    private Fragment lastFragment;

    public FragmentService(FragmentManager fm) {
        this.fm = fm;
    }

    public void show(Fragment fragment) {
        show(fragment, null, false);
    }

    public void show(Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        FragmentTransaction transaction = fm.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        setLastFragment(fragment);
    }

    public void pop() {
        fm.popBackStack();
    }

    public void replace(int containerViewId, Fragment fragment, String tag) {
        replace(containerViewId, fragment, tag, false);
    }

    public void replace(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        hide();

        FragmentTransaction transaction = fm.beginTransaction();
        /*if (lastFragment != null) {
            transaction.hide(lastFragment);
        }*/
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
        setLastFragment(fragment);
    }

    public void hide() {
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.hide(lastFragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void remove() {
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(lastFragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void hide(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    public Fragment getLastFragment() {
        return lastFragment;
    }

    public void setLastFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            lastFragment = fragment;
        }
    }

    public void destroy() {
        lastFragment = null;
    }
}
