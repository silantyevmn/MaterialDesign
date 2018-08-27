package silantyevmn.ru.materialdesign.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.photo.Photo;
import silantyevmn.ru.materialdesign.presenter.PhotoPresenter;

/**
 * Created by silan on 25.08.2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private final PhotoPresenter presenter;
    private List<Photo> photos;

    public PhotoAdapter(List<Photo> photos, PhotoPresenter presenter) {
        this.photos = photos;
        this.presenter=presenter;
    }

    public void setPhotos(List<Photo> photos){
        this.photos=photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
        MyViewHolder pvh = new MyViewHolder(rootView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bind(photos.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickPhoto(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener{
        private ImageView imagePhoto;
        private ImageView imageFavorite;

        MyViewHolder(final View itemView) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.card_image_photo);
            //textPhoto = itemView.findViewById(R.id.card_text_photo);
            imageFavorite = itemView.findViewById(R.id.card_favorite);
            itemView.setOnCreateContextMenuListener(this);

        }

        void bind(Photo photo) {
            if (photo.getUri() != null) {
                Picasso.get()
                        .load(photo.getUri())
                        .resize(0,150)
                        .centerCrop()
                        .into(imagePhoto);
            } else {
                imagePhoto.setImageResource(R.drawable.ic_crop_original_black);
            }
            if (photo.isFavorite()) {
                imageFavorite.setImageResource(R.drawable.ic_favorite_red);
            } else {
                imageFavorite.setImageResource(0);
            }
            //textPhoto.setText(photo.getName());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = presenter.getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, contextMenu);
            contextMenu.findItem(R.id.item_add).setOnMenuItemClickListener(this);
            contextMenu.findItem(R.id.item_delete).setOnMenuItemClickListener(this);
            contextMenu.findItem(R.id.item_favourite).setOnMenuItemClickListener(this);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            switch (item.getItemId()) {
                case R.id.item_add: {
                    presenter.add();
                    break;
                }
                case R.id.item_delete: {
                    presenter.delete(position);
                    break;
                }
                case R.id.item_favourite: {
                    presenter.favorite(position);
                    break;
                }
            }
            return false;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

