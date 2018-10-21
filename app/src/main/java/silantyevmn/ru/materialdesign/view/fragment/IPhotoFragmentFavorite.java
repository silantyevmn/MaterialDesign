package silantyevmn.ru.materialdesign.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import silantyevmn.ru.materialdesign.model.photo.Photo;

/**
 * Created by silan on 24.08.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface IPhotoFragmentFavorite extends MvpView, IPhotoFragmentUpdateAdapter {

    void showLog(String title, String value);

    void init(List<Photo> photos, int gridLayoutManagerSpan);

    void setAdapter(List<Photo> photos);

}
