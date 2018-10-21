package silantyevmn.ru.materialdesign.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoAdapter;
import silantyevmn.ru.materialdesign.presenter.GaleryPresenter;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenterFavorite;

public class PhotoFragmentFavorite extends MvpAppCompatFragment implements IPhotoFragmentFavorite, PhotoAdapter.OnClickAdapter {
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;

    @InjectPresenter
    PhotoPresenterFavorite presenter;

    @ProvidePresenter
    public PhotoPresenterFavorite provideSettingPresenter() {
        presenter = new PhotoPresenterFavorite(this);
        //TO SOMETHING WITH PRESENTER
        return presenter;
    }

    public static PhotoFragmentFavorite newInstance(Bundle bundle) {
        PhotoFragmentFavorite currentFragment = new PhotoFragmentFavorite();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("PhotoFragmentFavorite", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_photo_favorite, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getContext());

    }


    @Override
    public void init(List<Photo> photos, int gridLayoutManagerSpan) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), gridLayoutManagerSpan);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<Photo> photos) {
        if (adapter != null) {
            adapter.setPhotos(photos);
        }
    }

    @Override
    public void showLog(String title, String value) {
        Snackbar mSnackbar = Snackbar.make(getView(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();
        Log.i(title, "-->" + value);
    }

    @Override
    public void onClickPhoto(int position) {
        presenter.onClickPhoto(position);
    }

    @Override
    public void onClickMenuDelete(int position) {
        presenter.delete(position, getActivity());
    }

    @Override
    public void onClickMenuFavorite(int position) {
        presenter.favorite(position);
    }

    @Override
    public void updateAdapter() {
        presenter.updateAdapter();
    }

}
