package silantyevmn.ru.materialdesign.view.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.DataSharedPreference;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.model.photo.PhotoAdapter;
import silantyevmn.ru.materialdesign.model.photo.PhotoDataFile;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenter;
import silantyevmn.ru.materialdesign.view.activity.PhotoFullActivity;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends Fragment implements IPhotoFragment, PhotoAdapter.OnClickAdapter {
    private static final int GALLERY_REQUEST = 1;
    private static final int REQUEST_TAKE_PHOTO = 3;
    private int COUNT_SPAN = 3; //количество фото в строке
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FloatingActionButton fabCamera, fabGalery;
    private PhotoPresenter presenter;
    private PhotoAdapter adapter;
    private boolean isFABOpen = false;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoPresenter(this);
        Log.i("PhotoFragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        fab = rootView.findViewById(R.id.fab);
        fabCamera = rootView.findViewById(R.id.fabCamera);
        fabGalery = rootView.findViewById(R.id.fabGalery);
        fab.setOnClickListener(view -> {
            if (!isFABOpen) {
                showFABMenu();
            } else {
                closeFABMenu();
            }
        });
        return rootView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabCamera.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabGalery.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabCamera.setOnClickListener(view -> presenter.onClickCamera());
        fabGalery.setOnClickListener(view -> presenter.onClickGalery());
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabCamera.animate().translationY(0);
        fabGalery.animate().translationY(0);
        fabCamera.setOnClickListener(null);
        fabGalery.setOnClickListener(null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            COUNT_SPAN = 2;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            COUNT_SPAN = 3;
        }
        presenter.onViewCreated(COUNT_SPAN);

    }


    @Override
    public void init(List<Photo> photos, int span) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(List<Photo> photos) {
        adapter.setPhotos(photos);
    }

    @Override
    public void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = PhotoDataFile.getInstance().createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = PhotoDataFile.getInstance().getUriToFileProvider(getContext(), photoFile);
                DataSharedPreference.getInstance().setUriCamera(photoURI.toString());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {
                        Uri uri = imageReturnedIntent.getData();
                        Log.i("onActivityResult", "Uri :" + uri.toString());
                        //presenter.insertGalery(uri.toString());
                        //тест на запись файла
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Uri newUri = writePhoto(uri);
                                    presenter.insertGalery(newUri.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    presenter.insertCamera(DataSharedPreference.getInstance().getUriCamera());
                    Log.i("onActivityResult", "Uri :" + DataSharedPreference.getInstance().getUriCamera());
                } else {
                    presenter.delete(-1);
                }
                break;
        }
    }

    private Uri writePhoto(Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
        if (bitmap == null) {
            return null;
        }

        File file = PhotoDataFile.getInstance().createImageFile();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes.toByteArray());
        fos.close();
        return Uri.fromFile(file);
    }

    @Override
    public void showLog(String title, String value) {
        Snackbar mSnackbar = Snackbar.make(getView(), title + "-->" + value, Snackbar.LENGTH_LONG);
        mSnackbar.show();
        Log.i(title, "-->" + value);
    }

    @Override
    public void showGalery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void showFullPhoto(Photo photo) {
        //заглушка
        Intent intent = new Intent(getActivity(), PhotoFullActivity.class);
        intent.putExtra("image_full", photo.getUri());
        startActivity(intent);
        showLog("showPhoto->", String.valueOf(photo.getName()));

    }

    @Override
    public void onClickPhoto(int position) {
        presenter.onClickPhoto(position);
    }

    @Override
    public void onClickMenuDelete(int position) {
        presenter.delete(position);
    }

    @Override
    public void onClickMenuFavorite(int position) {
        presenter.favorite(position);
    }
}
