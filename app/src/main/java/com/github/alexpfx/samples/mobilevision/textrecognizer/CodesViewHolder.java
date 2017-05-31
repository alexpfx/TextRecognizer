package com.github.alexpfx.samples.mobilevision.textrecognizer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 29/05/2017.
 */

public class CodesViewHolder extends BaseViewHolder<Code> {
    @BindView(R.id.text_item_code)
    TextView mTextItemCode;

    public CodesViewHolder(View view, Context context) {
        super(view, context);

        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Code code) {
        mTextItemCode.setText(code.getText());
    }
}
