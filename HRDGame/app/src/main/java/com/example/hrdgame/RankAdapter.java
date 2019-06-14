package com.example.hrdgame;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RankAdapter extends  RecyclerView.Adapter<RankAdapter.recipeViewHolder> {
    private static List<Rank> rankList;
    private LayoutInflater mInflater;
    static class recipeViewHolder extends RecyclerView.ViewHolder{
        public final TextView player;
        public final TextView time;
        public final TextView stage;
        public final TextView date;
        public final TextView step;
        final RankAdapter mAdapter;
        public recipeViewHolder(View itemView, RankAdapter adapter) {
            super(itemView);
            player = itemView.findViewById(R.id.item_player);
            time = itemView.findViewById(R.id.item_time);
            stage = itemView.findViewById(R.id.item_stage);
            date = itemView.findViewById(R.id.item_date);
            step = itemView.findViewById(R.id.item_step);
            this.mAdapter = adapter;
        }
    }
    public RankAdapter(Context context,
                       List<Rank> reList) {
        mInflater = LayoutInflater.from(context);
        this.rankList= reList;
    }

    @Override
    public recipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.rank_list_item,
                parent, false);
        return new recipeViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(recipeViewHolder holder, int position) {
        Rank mCurrent = rankList.get(position);
        holder.player.setText(mCurrent.getPlayer());
        holder.time.setText(String.valueOf(mCurrent.getTime()));
        holder.stage.setText(String.valueOf(mCurrent.getStage()));
        holder.date.setText(mCurrent.getCreated_at());
        holder.step.setText(String.valueOf(mCurrent.getSteps()));
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }
}
