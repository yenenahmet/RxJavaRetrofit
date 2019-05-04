package com.journaldev.rxjavaretrofit.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {

    private List<T> items;

    public BaseRecyclerViewAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    public List<T> getItems() {
        return items;
    }

    @Nullable
    public T getItem(final int pos) {
        if (!items.isEmpty()) {
            return items.get(pos);
        }
        return null;
    }

    public void clear() {
        items.clear();
    }

    public void setData(final List<T> data) {
        if (data != null && !data.isEmpty()) {
            this.items = data;
            notifyDataSetChanged();
        }
    }

    public void addItems(final List<T> data) {
        if (items != null && data != null && !data.isEmpty()) {
            final int x = items.size() - 1;
            items.addAll(data);
            final int itemCount = items.size() - 1;
            notifyItemRangeInserted(x, itemCount);
        }
    }

    protected LayoutInflater getInflater(@NonNull final ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }

}
