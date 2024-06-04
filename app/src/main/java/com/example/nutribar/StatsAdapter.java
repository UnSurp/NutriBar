package com.example.nutribar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {

    private List<Stats> statsList;
    private Context context;

    public StatsAdapter(Context context, List<Stats> statsList) {
        this.statsList = statsList;
        this.context = context;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_item, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        Stats stats = statsList.get(position);
        holder.tvDay.setText("Число: " + stats.getDay());
        holder.tvMonth.setText("Месяц: " + stats.getMonth());
        holder.tvYear.setText("Год: " + stats.getYear());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatInfoActivity.class);
                intent.putExtra("stat", stats);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return statsList.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvMonth, tvYear;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.dateCard);
            tvMonth = itemView.findViewById(R.id.monthCard);
            tvYear = itemView.findViewById(R.id.yearCard);
        }
    }
}
