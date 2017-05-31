package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alexandre on 29/05/2017.
 */

public class CodesAdapter extends BaseAdapter<Code, CodesViewHolder> {

    protected CodesAdapter(Context context) {
        super(context);
    }

    @Override
    public CodesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_code, viewGroup, false);

        return new CodesViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(CodesViewHolder holder, int position) {
        Code code = getItemAt(position);
        holder.bind(code);
    }


}
