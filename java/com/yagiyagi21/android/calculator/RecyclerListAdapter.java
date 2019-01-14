package com.yagiyagi21.android.calculator;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerListViewHolder> {

    private List<Map<String, Object>> _listData;

    public RecyclerListAdapter(List<Map<String, Object>> listData) {
        _listData = listData;
    }

    @Override
    public RecyclerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.memo_row, parent, false);
        RecyclerListViewHolder holder = new RecyclerListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerListViewHolder holder, int position) {
        Map<String, Object> item = _listData.get(position);
        String memo = (String) item.get("memo");
        holder._tvMemo.setText(memo);
    }

    @Override
    public int getItemCount() {
        return _listData.size();
    }

    public class RecyclerListViewHolder extends RecyclerView.ViewHolder {

        public TextView _tvMemo;

        public RecyclerListViewHolder(View itemView) {
            super(itemView);

            _tvMemo = itemView.findViewById(R.id.tvMemo);
        }
    }

    public void removeAt(int position) {
        _listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, _listData.size());
    }
}