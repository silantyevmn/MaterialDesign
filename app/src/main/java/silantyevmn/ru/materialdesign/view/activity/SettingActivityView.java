package silantyevmn.ru.materialdesign.view.activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by silan on 11.09.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface SettingActivityView extends MvpView {
    void init(int currentThemePosition, int currentSpanPosition);
}
