package silantyevmn.ru.materialdesign.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoModel;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.activity.GaleryActivity;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragmentFavorite;
import silantyevmn.ru.materialdesign.view.fragment.PhotoFragmentFavorite;


@InjectViewState
public class PhotoPresenterFavorite extends MvpPresenter<IPhotoFragmentFavorite> {
    private final IPhotoModel model;
    private final IGaleryView mainActivity;
    private Scheduler mainSheduler;
    private List<Photo> photos;

    public PhotoPresenterFavorite(PhotoFragmentFavorite photoFragment, Scheduler mainScheduler) {
        this.mainActivity = (GaleryActivity) photoFragment.getActivity();
        model = PhotoModel.getInstance();
        this.mainSheduler = mainScheduler;
    }

    //создание View
    public void init(Context context) {
        getPhotos().observeOn(mainSheduler)
                .subscribe(list -> {
                    photos = list;
                    getViewState().init(list, model.getGridLayoutManagerSpan(context.getResources().getConfiguration().orientation));
                });

    }

    private Observable<List<Photo>> getPhotos() {
        return model.getListFavorite().subscribeOn(Schedulers.io());
    }

    public void updateAdapter() {
        getPhotos().observeOn(mainSheduler)
                .subscribe(list -> {
                    photos = list;
                    getViewState().setAdapter(list);
                });

    }

    public void delete(int position, Activity activity) {
        if (position == -1) {
            Uri uri = Uri.parse(DataSharedPreference.getInstance().getUriCamera());
            Photo photo = new Photo(uri.getLastPathSegment(), uri.toString());
            model.delete(photo);
            return;
        }
        new DialogView(activity, activity.getString(R.string.dialog_title_delete), () -> {
            model.delete(photos.get(position))
                    .observeOn(mainSheduler)
                    .subscribe(p -> {
                        updateAdapter();
                        getViewState().showLog("delete", String.valueOf(position));
                    });
        });

    }

    public void favorite(int position) {
        Photo photo = photos.get(position);
        photo.setFavorite(!photo.isFavorite());
        model.update(photo)
                .observeOn(mainSheduler)
                .subscribe(p -> {
                    updateAdapter();
                    getViewState().showLog("favourites", String.valueOf(position));
                });
    }

    public void onClickPhoto(int adapterPosition) {
        //запускаем в главной активити
        mainActivity.showFullPhoto(photos.get(adapterPosition));
    }
}
