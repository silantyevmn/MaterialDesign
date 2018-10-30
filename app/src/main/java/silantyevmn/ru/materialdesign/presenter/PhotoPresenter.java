package silantyevmn.ru.materialdesign.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.IPhotoModel;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoModel;
import silantyevmn.ru.materialdesign.view.DialogView;
import silantyevmn.ru.materialdesign.view.activity.IGaleryView;
import silantyevmn.ru.materialdesign.view.fragment.IPhotoFragment;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<IPhotoFragment> {
    private final IPhotoModel model;
    private Scheduler mainScheduler;
    private List<Photo> photos;

    public PhotoPresenter(Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
        model = PhotoModel.getInstance();
    }

    public void init(Context context) {
        getPhotos().observeOn(mainScheduler)
                .subscribe(list -> {
                    photos = list;
                    getViewState().init(list, model.getGridLayoutManagerSpan(context.getResources().getConfiguration().orientation));
                });
    }

    private Observable<List<Photo>> getPhotos() {
        return model.getList();
    }

    public void updateAdapter() {
        getPhotos().observeOn(mainScheduler)
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
            Photo photo = photos.get(position);
            model.delete(photo)
                    .observeOn(mainScheduler)
                    .subscribe(()->{
                        updateAdapter();
                        getViewState().showLog("delete", photo.getName() + " удалено из базы.");
                    });
        });
    }

    public void favorite(int position) {
        Photo photo = photos.get(position);
        photo.setFavorite(!photo.isFavorite());
        model.update(photo).observeOn(mainScheduler)
                .subscribe(() -> {
                    updateAdapter();
                    if (photo.isFavorite()) {
                        getViewState().showLog("favourites", photo.getName() + " добавлено в избранное.");
                    } else {
                        getViewState().showLog("favourites", photo.getName() + " удалено из избранного.");
                    }
                });
    }

    public void onClickImportCamera(IGaleryView galeryView) {
        //todo test import camera
        galeryView.showImportCamera();
    }

    public void onClickPhoto(IGaleryView galeryView, int adapterPosition) {
        galeryView.showFullPhoto(photos.get(adapterPosition));
    }

    public int getGridLayoutManagerSpan(int orientation) {
        return model.getGridLayoutManagerSpan(orientation);
    }

    public void onClickImportGalery(IGaleryView galeryView) {
        //todo test import galery
        galeryView.showImportGalery();
    }

    public void onClickBottonMenuHome() {
        getViewState().showBottonHome();
    }

    public void onClickBottonMenuDatabase() {
        getViewState().showBottonDatabase();
    }

    public void onClickBottonMenuNetwork() {
        getViewState().showBottonNetwork();
    }
}
