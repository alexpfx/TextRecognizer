package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by alexandre on 28/05/2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private Context mContext;

    public BaseViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public BaseViewHolder(View view, Context context){
        this(view);
        this.mContext = context;
    }

    public void setOnClickListener (View.OnClickListener onClickListener, Object tag){
        itemView.setOnClickListener(onClickListener);
        itemView.setTag(tag);
    }
    public abstract void bind (T object);

    protected Context getContext() {
        return mContext;
    }

}
