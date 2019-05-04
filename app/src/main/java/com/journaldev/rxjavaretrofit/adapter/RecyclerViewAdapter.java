package com.journaldev.rxjavaretrofit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.journaldev.rxjavaretrofit.base.BaseRecyclerViewAdapter;
import com.journaldev.rxjavaretrofit.databinding.RecyclerviewItemLayoutBinding;
import com.journaldev.rxjavaretrofit.pojo.Crypto;

public class RecyclerViewAdapter extends BaseRecyclerViewAdapter<Crypto.Market,RecyclerViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemLayoutBinding binding = RecyclerviewItemLayoutBinding.inflate(getInflater(parent), parent, false);
        return new RecyclerViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final Crypto.Market market = getItem(position);
        if (market != null) {
            holder.bind(market);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerviewItemLayoutBinding binding;

        ViewHolder(RecyclerviewItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final Crypto.Market model) {
            binding.setViewadapter(model);
            binding.executePendingBindings();
        }
    }
}
