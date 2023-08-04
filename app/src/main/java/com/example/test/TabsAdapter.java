package com.example.test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHolder> {
    private int selectedPosition = -1;
    private List<String> tabs;
    private Context context;
    private OnTabSelectedListener listener;

    public TabsAdapter(Context context, List<String> tabs, OnTabSelectedListener listener) {
        this.context = context;
        this.tabs = tabs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(tabs.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTabSelected(position);
            }
        });
        if (position == selectedPosition) {
            holder.textView.setTextColor(Color.RED); // 當選中時改變顏色
        } else {
            holder.textView.setTextColor(Color.BLACK); // 正常的顏色
        }
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tab_text_view);
        }
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(selectedPosition);
    }
}
