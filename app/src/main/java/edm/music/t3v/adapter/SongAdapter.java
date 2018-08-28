package edm.music.t3v.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edm.music.t3v.R;
import edm.music.t3v.model.Song;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context context;
    private List<Song> songList;

    private OnItemClick onItemClick;


    public SongAdapter(Context context, List<Song> songList, OnItemClick onItemClick) {
        this.context = context;
        this.songList = songList;
        this.onItemClick = onItemClick;
    }

    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_song, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.tvSongName.setText(song.getName());
        holder.tvUploader.setText(song.getPoster());
        holder.tvView.setText(song.getView());

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSongName)
        TextView tvSongName;
        @BindView(R.id.tvUploader)
        TextView tvUploader;
        @BindView(R.id.tvView)
        TextView tvView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClickItem(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClick {
        void onClickItem(int position);
    }
}
