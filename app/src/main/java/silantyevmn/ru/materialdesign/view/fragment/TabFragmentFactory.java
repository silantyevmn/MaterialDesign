package silantyevmn.ru.materialdesign.view.fragment;

import android.support.v4.app.Fragment;

public class TabFragmentFactory {
    private static final String[] TITLES = {"Home", "Favorite"};


    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return PhotoFragment.newInstance(null);
            case 1:
                return PhotoFragmentFavorite.newInstance(null);
            default:
                throw new IllegalArgumentException("Could not create fragment for position " + position);
        }
    }

    public int getFragmentsCount() {
        return TITLES.length;
    }

    public CharSequence getFragmentTitle(int position) {
        return TITLES[position];
    }

}
