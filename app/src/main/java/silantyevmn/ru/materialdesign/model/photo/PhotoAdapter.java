package silantyevmn.ru.materialdesign.model.photo;

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

/**
 * Created by silan on 25.08.2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private final OnClickAdapter listener;
    private List<Photo> photos;

    public interface OnClickAdapter {
        void onClickPhoto(int position);

        void onClickMenuDelete(int position);

        void onClickMenuFavorite(int position);
    }

    public PhotoAdapter(List<Photo> photos, OnClickAdapter listener) {
        this.photos = photos;
        this.listener = listener;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private ImageView imagePhoto;
        private ImageView imageFavorite;

        MyViewHolder(final View itemView) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.card_image_photo);
            //textPhoto = itemView.findViewById(R.id.card_text_photo);
            imageFavorite = itemView.findViewById(R.id.card_favorite);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);

        }

        void bind(Photo photo) {
            Picasso.get()
                    .load(photo.getUri())
                    .placeholder(R.drawable.ic_autorenew_black)
                    .error(R.drawable.ic_crop_original_black)
                    .resize(0, 150)
                    .centerCrop()
                    .into(imagePhoto);
            if (photo.isFavorite()) {
                imageFavorite.setImageResource(R.drawable.ic_favorite_red);
            } else {
                imageFavorite.setImageResource(0);
            }
            //textPhoto.setText(photo.getName());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = new MenuInflater(view.getContext());
            inflater.inflate(R.menu.context_menu, contextMenu);
            contextMenu.findItem(R.id.item_delete).setOnMenuItemClickListener(this);
            contextMenu.findItem(R.id.item_favourite).setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            switch (item.getItemId()) {
                case R.id.item_delete: {
                    listener.onClickMenuDelete(position);
                    break;
                }
                case R.id.item_favourite: {
                    listener.onClickMenuFavorite(position);
                    break;
                }
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            listener.onClickPhoto(getAdapterPosition());
        }
    }
}

