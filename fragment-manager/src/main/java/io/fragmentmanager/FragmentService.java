package io.fragmentmanager;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

public class FragmentService {

    private FragmentManager fm;
    private Stack<FragmentElement> fragments = new Stack<>();

    public FragmentService(FragmentManager fm) {
        this.fm = fm;
    }
    public FragmentService() {
    }

    public void hideLastAndShow(Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        show(fragment, tag, addToBackStack);
    }

    public void hideLastAndAdd(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        add(containerViewId, fragment, tag, addToBackStack);
    }

    public void show(Fragment fragment) {
        show(fragment, null, false);
    }

    public void show(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        fragments.push(new FragmentElement(tag, fragment, addToBackStack));
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    public FragmentElement pop() {
        FragmentElement last = lastShownFragment();
        FragmentElement lastFragment = popInternal();
        while ((lastFragment != null && !lastFragment.isAddtoBackStack()) || (lastFragment != null && lastFragment.equals(last))) {
            lastFragment = popInternal();
        }

        if (lastFragment != null) {
            lastFragment.setPopped(true);
        }

        return lastFragment;
    }

    @Nullable
    private FragmentElement popInternal() {
        return (fragments.empty()) ? null : fragments.pop();
    }

    public FragmentElement peek() {
        FragmentElement lastFragment = fragments.isEmpty() ? null : fragments.elementAt(0);
        int i = 1;
        int max = fragments.size();
        while (i < max && (lastFragment != null && !lastFragment.isAddtoBackStack())) {
            lastFragment = fragments.isEmpty() ? null : fragments.elementAt(i);
            i++;
        }
        return i < max ? lastFragment : null;
    }

    private FragmentElement peekInternal() {
        return (fragments.empty()) ? null : fragments.peek();
    }

    public void replace(int containerViewId, Fragment fragment, String tag) {
        replace(containerViewId, fragment, tag, false);
    }

    public void add(int containerViewId, Fragment fragment, String tag) {
        add(containerViewId, fragment, tag, false);
    }

    public void add(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
//        fragments.push(new FragmentElement(tag, fragment, addToBackStack));
        transaction.add(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public void hideLastAndReplace(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        hide();
        replace(containerViewId, fragment, tag, addToBackStack);
    }

    public void replace(int containerViewId, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        fragments.push(new FragmentElement(tag, fragment, addToBackStack));
        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

    public void hide() {
        FragmentElement lastFragment = lastShownFragment();
        if (lastFragment != null) {
            hide(lastFragment.getFragment());
        }
    }

    public FragmentElement popAndShow() {
        hide();
        FragmentElement pop = pop();
        if (pop != null) {
            show(pop.getFragment(), pop.getName(), pop.isAddtoBackStack());
        }
        return pop;
    }

    public FragmentElement popAndReplace(@IdRes int place) {
        hide();
        FragmentElement pop = pop();
        if (pop != null) {
            replace(place, pop.getFragment(), pop.getName(), pop.isAddtoBackStack());
        }
        return pop;
    }

    public void remove() {
        FragmentElement lastFragment = popInternal();
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(lastFragment.getFragment());
            transaction.commitAllowingStateLoss();
        }
    }

    public void removeLastAndShowPrevoius() {
        FragmentElement lastFragment = popInternal();
        if (lastFragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(lastFragment.getFragment());
            transaction.commitAllowingStateLoss();
        }
        onResume();
    }

    public void hide(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.hide(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void destroy() {
        clear();
    }

    public void clear() {
        FragmentElement fragmentElement = popInternal();
        while (fragmentElement != null) {
            hide(fragmentElement.getFragment());
            fragmentElement = popInternal();
        }

        int backStackSize = fm.getBackStackEntryCount();
        for (int i = 0; i < backStackSize; i++) {
            fm.popBackStack();
        }
    }

    public void onResume() {
        FragmentElement fragmentElement = popInternal();
        if (fragmentElement != null) {
            show(fragmentElement.getFragment(), fragmentElement.getName(), fragmentElement.isAddtoBackStack());
        }
    }

    public FragmentElement lastShownFragment() {
        return peekInternal();
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }
}
