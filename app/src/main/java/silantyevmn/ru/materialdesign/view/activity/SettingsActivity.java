package silantyevmn.ru.materialdesign.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.theme.Theme;

public class SettingsActivity extends AppCompatActivity {
    public static final String LIST_THEMES = "list_themes";
    private final int margin_default = 10;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //добавляем в АппБар кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Theme> themes = (List<Theme>) getIntent().getSerializableExtra(LIST_THEMES);
        init(themes);
    }

    private void init(final List<Theme> themes) {

        LinearLayout linearLayout = findViewById(R.id.line_setting);
        LinearLayout.LayoutParams marginParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        marginParam.setMargins(margin_default, margin_default, margin_default, margin_default);

        Button[] buttons = new Button[themes.size()];
        for (int i = 0; i < buttons.length; i++) {
            Button button = new Button(this);
            button.setText(themes.get(i).getName());
            button.setBackgroundResource(themes.get(i).getColor());
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchTheme(themes.get(finalI).getId());
                }
            });
            linearLayout.addView(button, marginParam);

        }

    }

    private void switchTheme(int theme_id) {
        DataSharedPreference.getInstance().setCurrentTheme(theme_id);
        recreate();
    }

    //обработка клавиши назад
    @Override
    public void onBackPressed() {
        SettingsActivity.this.finish();
    }
}
