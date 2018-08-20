package silantyevmn.ru.materialdesign.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.MainRecyclerAdapter;
import silantyevmn.ru.materialdesign.model.entity.Photo;

public class MainFragment extends Fragment implements MainRecyclerAdapter.onClick {
    private static String ARG_PHOTOS = "photos";
    private final int COUNT_SPAN = 3; //количество фото в строке
    private List<Photo> photos;
    private RecyclerView recyclerView;
    private MainRecyclerAdapter adapter;

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
        recyclerView = rootView.findViewById(R.id.recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initAdapter(photos);
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COUNT_SPAN);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initAdapter(List<Photo> photos) {
        adapter = new MainRecyclerAdapter(photos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickRecyclerAdapter(int position) {
        //todo заглушка
        Snackbar mSnackbar = Snackbar.make(getView(), "position-" + position, Snackbar.LENGTH_LONG);
        mSnackbar.show();
    }
}
