package com.yagiyagi21.android.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerListViewHolder> {

    private List<Map<String, String>> _memoList;

    public RecyclerListAdapter(List<Map<String, String>> memoList) {
        _memoList = memoList;
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
        Map<String, String> item = _memoList.get(position);
        String memo = item.get("memo");
        String formula = item.get("formula") ;
        holder._tvMemo.setText(memo);
        holder._tvFormula.setText(formula);
    }

    @Override
    public int getItemCount() {
        return _memoList.size();
    }

    public class RecyclerListViewHolder extends RecyclerView.ViewHolder {

        public TextView _tvMemo;
        public TextView _tvFormula;

        public RecyclerListViewHolder(View itemView) {
            super(itemView);

            _tvMemo = itemView.findViewById(R.id.tvMemo);
            _tvFormula = itemView.findViewById(R.id.tvMemoFormula);
        }
    }

    public void removeAt(int position) {
        _memoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, _memoList.size());
    }

    public List<Map<String, String>> getMemoList() {
        return _memoList;
    }

    public Map<String, String> getMemoByPosition (int position) {
        return _memoList.get(position);
    }
}