package silantyevmn.ru.materialdesign.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import silantyevmn.ru.materialdesign.R;
import silantyevmn.ru.materialdesign.model.entity.Photo;

/**
 * Created by silan on 20.08.2018.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder> {
    private List<Photo> photos;
    private MainRecyclerAdapter.onClick listener;

    public interface onClick {
        void onClickRecyclerAdapter(int position);
    }

    public MainRecyclerAdapter(List<Photo> photos, MainRecyclerAdapter.onClick listener) {
        this.photos = photos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePhoto;
        TextView textPhoto;

        MyViewHolder(final View itemView) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.card_image_photo);
            textPhoto = itemView.findViewById(R.id.card_text_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickRecyclerAdapter(getAdapterPosition());
                    Log.i("position-", "" + getAdapterPosition());
                }
            });
        }

        void bind(Photo photo) {
            //imagePhoto.setImageResource(photo.getId());
            textPhoto.setText(photo.getName());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
