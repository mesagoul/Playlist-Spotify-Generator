package android.workshop.dmii.playlistspotifygenerator.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import java.util.ArrayList;

public class MusiclistAdapter extends RecyclerView.Adapter<MusiclistAdapter.ViewHolder> {

    private ArrayList<Music> listMusic;
    private boolean isCreateList;
    private static MusiclistAdapterListener listener;

    public MusiclistAdapter(ArrayList<Music> list, boolean btnIsCreate) {
        this.listMusic = list;
        this.isCreateList = btnIsCreate;
    }

    public void setListener(MusiclistAdapterListener adapterListener) {
        listener = adapterListener;
    }

    public void setListMusic(ArrayList<Music> list){
        this.listMusic = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = listMusic.get(position);
        holder.music_id.setText(String.valueOf(position));
        holder.music_title.setText(music.getName());
        holder.music_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(position, holder.music_container, listMusic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView music_id;
        private final TextView music_title;
        private final Button music_delete;
        private final LinearLayout music_container;

        ViewHolder(View itemView) {
            super(itemView);

            music_id = (TextView) itemView.findViewById(R.id.item_id);
            music_title = (TextView) itemView.findViewById(R.id.item_title);
            music_delete = (Button) itemView.findViewById(R.id.item_delete);
            music_container = (LinearLayout) itemView.findViewById(R.id.item_container);

        }
    }

    public interface MusiclistAdapterListener {
        void onItemClick(int position, LinearLayout music_container, ArrayList<Music> listMusic);
    }
}
