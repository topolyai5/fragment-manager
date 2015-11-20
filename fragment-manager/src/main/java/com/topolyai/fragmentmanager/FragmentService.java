package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class FragmentService {

    private FragmentManager fm;
    private Stack<FragmentElement> fragments = new Stack<>();

    private FragmentElement lastFragment;

    public FragmentService(FragmentManager fm) {
        this.fm = fm;
    }

    public void show(Fragment fragment) {
        show(fragment, null, false);
    }

    public void show(Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        FragmentTransaction transaction = fm.beginTransaction();
        if (lastFragment != null && lastFragment.isAddtoBackStack()) {
            fragments.push(lastFragment);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.show(fragment);
        lastFragment = new FragmentElement(fragment.getTag(), fragment, addToBackStack);
        transaction.commitAllowingStateLoss();
    }

    public FragmentElement pop() {
        lastFragment = (fragments.empty()) ? null : fragments.pop();
        if (lastFragment != null) {
            lastFragment.setAddtoBackStack(false);
        }
        return lastFragment;
    }

    public FragmentElement peek() {
        return lastFragment;
    }

    public void replace(int containerViewId, Fragment fragment, String tag) {
        replace(containerViewId, fragment, tag, false);
    }

    public void replace(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        FragmentTransaction transaction = fm.beginTransaction();
        if (lastFragment != null && lastFragment.isAddtoBackStack()) {
            fragments.push(lastFragment);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.replace(containerViewId, fragment, tag);
        lastFragment = new FragmentElement(fragment.getTag(), fragment, addToBackStack);
        transaction.commitAllowingStateLoss();
    }

    public void hide() {
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.hide(lastFragment.getFragment());
            transaction.commitAllowingStateLoss();
        }
    }

    public void remove() {
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(lastFragment.getFragment());
            transaction.commitAllowingStateLoss();
        }
    }

    public void hide(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    public FragmentElement getLastFragment() {
        return lastFragment;
    }

    public void destroy() {

    }
}
