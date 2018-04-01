package android.workshop.dmii.playlistspotifygenerator.adapters;

        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.workshop.dmii.playlistspotifygenerator.R;
        import android.workshop.dmii.playlistspotifygenerator.models.Artist;

        import java.util.ArrayList;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private ArrayList<Artist> listArtist;

    public ArtistListAdapter(ArrayList<Artist> list) {
        this.listArtist = list;
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
        Artist music = listArtist.get(position);
        holder.music_id.setText(String.valueOf(position));
        holder.music_title.setText(music.getName());
        holder.music_delete.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return listArtist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView music_id;
        private final TextView music_title;
        private final Button music_delete;

        ViewHolder(View itemView) {
            super(itemView);

            music_id = (TextView) itemView.findViewById(R.id.item_id);
            music_title = (TextView) itemView.findViewById(R.id.item_title);
            music_delete = (Button) itemView.findViewById(R.id.item_delete);

        }
    }

    public interface ArtistListAdapterListener {
        void onItemClick(int position);
    }
}
