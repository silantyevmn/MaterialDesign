package silantyevmn.ru.materialdesign.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.presenter.SettingPresenter;

public class SettingsActivity extends MvpAppCompatActivity {
    @BindView(R.id.button_setting_action)
    Button button;
    @BindView(R.id.spinner_item_theme)
    Spinner spinnerTheme;
    @BindView(R.id.spinner_item_span)
    Spinner spinnerSpan;

    @InjectPresenter
    SettingPresenter presenter;

    @ProvidePresenter
    public SettingPresenter provideSettingPresenter() {
        presenter = new SettingPresenter(this);
        //TO SOMETHING WITH PRESENTER
        return presenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //добавляем в АппБар кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = provideSettingPresenter();
    }

    @OnClick(R.id.button_setting_action)
    public void onClick(Button button) {
        presenter.onClick(spinnerTheme.getSelectedItemPosition(), spinnerSpan.getSelectedItemPosition());
    }

    public void initSetting(int positionTheme, int positionSpan) {
        spinnerTheme.setSelection(positionTheme);
        spinnerSpan.setSelection(positionSpan);
    }

    //обработка клавиши назад
    @Override
    public void onBackPressed() {
        SettingsActivity.this.finish();
    }

    public void recreateSetting() {
        onBackPressed();
    }
}
