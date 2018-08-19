package silantyevmn.ru.materialdesign.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.entity.Theme;

public class SettingsActivity extends AppCompatActivity {
    public static final String LIST_THEMES = "list_themes";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(DataSharedPreference.getInstance().getCurrentTheme());
        setContentView(R.layout.activity_settings);

        //добавляем в АппБар кнопку назад
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        List<Theme> themes = (List<Theme>) getIntent().getSerializableExtra(LIST_THEMES);
        init(themes);
    }

    private void init(final List<Theme> themes) {
        final Spinner spinner=findViewById(R.id.spinner);

        Button bt=findViewById(R.id.button_action);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition=spinner.getSelectedItemPosition();
                switchTheme(themes.get(itemPosition).getId());
            }
        });
       /* LinearLayout linearLayout = findViewById(R.id.line_setting);
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
            linearLayout.addView(button);

        }*/

    }

    private void switchTheme(int theme_id) {
        DataSharedPreference.getInstance().setCurrentTheme(theme_id);
        recreate();
    }

   /* //обработка клавиши назад
    @Override
    public void onBackPressed() {
        SettingsActivity.this.finish();
    }*/
}
