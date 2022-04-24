package com.mytunes.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mytunes.DashboardActivity;
import com.mytunes.R;
import com.mytunes.helper.Utility;
import com.mytunes.model.SearchData;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private DashboardActivity mDashboardActivity;
    private SearchData mSearchData;

    public DetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            mSearchData = (SearchData) bundle.getSerializable("result");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDashboardActivity = (DashboardActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View rootView){

        String desc = !TextUtils.isEmpty(mSearchData.longDescription) ? mSearchData.longDescription : mSearchData.shortDescription;
        if (TextUtils.isEmpty(desc))
            desc = "Description content is here.";

        ImageView imgBack = rootView.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> mDashboardActivity.getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss());

        ImageView imgIcon = rootView.findViewById(R.id.imgIcon);

        TextView txtName = rootView.findViewById(R.id.txtName);
        TextView txtDate = rootView.findViewById(R.id.txtDate);
        TextView txtDesc = rootView.findViewById(R.id.txtDesc);
        TextView txtCollectionPrice = rootView.findViewById(R.id.txtCollectionPrice);
        TextView txtTrackPrice = rootView.findViewById(R.id.txtTrackPrice);
        TextView txtTrackRentalPrice = rootView.findViewById(R.id.txtTrackRentalPrice);
        TextView txtWrapperType = rootView.findViewById(R.id.txtWrapperType);
        TextView txtKind = rootView.findViewById(R.id.txtKind);
        TextView txtCountry = rootView.findViewById(R.id.txtCountry);

        Picasso.get().load(mSearchData.artworkUrl100).into(imgIcon);

        txtName.setText(mSearchData.artistName);
        txtDate.setText(Utility.getInstance(mDashboardActivity).getSimpleDate(mSearchData.releaseDate));
        txtDesc.setText(desc);
        txtCollectionPrice.setText((mSearchData.collectionPrice != null ? mSearchData.collectionPrice : "-") + " " + mSearchData.currency);
        txtTrackPrice.setText((mSearchData.trackPrice != null ? mSearchData.trackPrice : "-") + " " + mSearchData.currency);
        txtTrackRentalPrice.setText((mSearchData.trackRentalPrice != null ? mSearchData.trackRentalPrice : "-") + " " + mSearchData.currency);
        txtWrapperType.setText(mSearchData.wrapperType);
        txtKind.setText(mSearchData.kind);
        txtCountry.setText(mSearchData.country);
    }
}