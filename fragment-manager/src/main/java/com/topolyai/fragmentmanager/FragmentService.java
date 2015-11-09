package com.topolyai.fragmentmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentService {

    private FragmentManager fm;
    private Map<String, Fragment> fragments = new HashMap<>();

    public FragmentService(FragmentManager fm, List<FragmentElement> fragments) {
        this.fm = fm;
        for (FragmentElement fragment : fragments) {
            this.fragments.put(fragment.getName(), fragment.getFragment());
        }
        hideAll();
    }

    public FragmentService(FragmentManager fm, Map<String, Fragment> fragments) {
        this.fm = fm;
        this.fragments = fragments;
        hideAll();
    }

    public void show(String fragmentName, boolean addToBackStack) {
        show(getFragment(fragmentName), fragmentName, addToBackStack);
    }

    public void show(Fragment fragment, String fragmentName, boolean addToBackStack) {
        hideAll();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragmentName);
        }
        transaction.commit();
    }

    public void pop() {
        fm.popBackStack();
    }

    public void replace(int containerViewId, Fragment fragment) {
        hideAll();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.commit();
    }

    private void hideAll() {
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fr : fragments.values()) {
            transaction.hide(fr);
        }
        transaction.commit();

    }

    public Fragment getFragment(String fragmentName) {
        return fragments.get(fragmentName);
    }

}
