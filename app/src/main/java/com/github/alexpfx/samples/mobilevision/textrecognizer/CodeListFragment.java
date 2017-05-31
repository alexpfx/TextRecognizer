package com.github.alexpfx.samples.mobilevision.textrecognizer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CodeListFragment extends Fragment {


    @BindView(R.id.btn_scan)
    Button mBtnScan;
    @BindView(R.id.recycler_codes)
    RecyclerView mRecyclerCodes;
    Unbinder unbinder;

    private CodesAdapter mCodesAdapter;

    public CodeListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecycler ();
        return view;
    }

    private void setupRecycler() {
        mCodesAdapter = new CodesAdapter(getContext());
        mRecyclerCodes.setAdapter(mCodesAdapter);
        mCodesAdapter.swapItemList(createList());
        mRecyclerCodes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private List<Code> createList() {
        ArrayList<Code> arrayList = new ArrayList<>();
        return arrayList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btn_scan)
    void scanOnClick (){
        Intent intent = new Intent(getActivity(), ScanTextActivity.class);
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonStatusCodes.SUCCESS && requestCode == 1001){
            String code = data.getStringExtra(ScanTextActivity.DETECTED_TEXT);
            mCodesAdapter.addItem(new Code(code));
        }


    }
}
