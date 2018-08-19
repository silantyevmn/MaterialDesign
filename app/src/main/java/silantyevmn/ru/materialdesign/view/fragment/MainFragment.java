package silantyevmn.ru.materialdesign.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.entity.Enity;
import silantyevmn.ru.materialdesign.model.entity.Photo;

public class MainFragment extends Fragment {
    private static String ARG_PHOTOS = "photos";
    private List<Photo> photos;
    private TextView container;

    public static MainFragment newInstance(List<Photo> photos) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTOS, (Serializable) photos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.photos = (List<Photo>) getArguments().getSerializable(ARG_PHOTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.container = rootView.findViewById(R.id.container);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showView();
    }

    private void showView() {
        for (Enity photo : photos) {
            container.append(photo.getName() + "\n");
        }
    }
}
