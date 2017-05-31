package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by alexandre on 27/05/2017.
 */

public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> mItemList = Collections.emptyList();
    private Context mContext;

    protected BaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    protected T getItemAt (int position){
        return mItemList.get(position);

    }

    protected void addItem (T item){
        mItemList.add(item);
        notifyDataSetChanged();
    }


    public void swapItemList(List<T> items) {
        mItemList = items;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }




}
