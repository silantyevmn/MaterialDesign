package silantyevmn.ru.materialdesign.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.entity.Photo;
import silantyevmn.ru.materialdesign.presenter.PresenterMainFragment;
import silantyevmn.ru.materialdesign.view.recycler.PhotoAdapter;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment implements MainFragmentView {
    private static final int GALLERY_REQUEST = 1;
    private static String ARG_PHOTOS = "photos";
    private final int COUNT_SPAN = 3; //количество фото в строке
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private PresenterMainFragment presenter;

    public static MainFragment newInstance(List<Photo> photos) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        //args.putSerializable(ARG_PHOTOS, (Serializable) photos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new PresenterMainFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        fab = rootView.findViewById(R.id.fab);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COUNT_SPAN);
        recyclerView.setLayoutManager(gridLayoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickFabButton();
            }
        });
    }

    @Override
    public void setAdapter(PhotoAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    presenter.insertPhoto(imageReturnedIntent);
                }
        }
    }

    @Override
    public void showLog(String title, String value) {
        Snackbar mSnackbar = Snackbar.make(getView(), title+"-->"+value, Snackbar.LENGTH_LONG);
        mSnackbar.show();
        Log.i(title, "-->" + value);
    }

    @Override
    public void startActivityLoadPhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void showViewPhoto(int adapterPosition) {
        //заглушка
    }
}
