package silantyevmn.ru.materialdesign.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    private final TabFragmentFactory fragmentFactory;
    private List<Fragment> fragments = new ArrayList<>();

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager, TabFragmentFactory fragmentFactory) {
        super(fragmentManager);
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return fragmentFactory.getFragmentsCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentFactory.getFragmentTitle(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        fragments.add(createdFragment);
        return createdFragment;
    }

    public void updateAllFragmentsToAdapter() {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                IPhotoFragmentUpdateAdapter adapter = (IPhotoFragmentUpdateAdapter) fragment;
                if (adapter != null) {
                    adapter.updateAdapter();
                }
            }
        }
    }

}
