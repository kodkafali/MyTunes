package com.mytunes.view;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mytunes.DashboardActivity;
import com.mytunes.R;
import com.mytunes.helper.HeaderItemDecoration;
import com.mytunes.helper.Utility;
import com.mytunes.impl.OnItemClick;
import com.mytunes.model.ResultData;
import com.mytunes.model.SearchData;
import com.mytunes.view.adapter.MasterAdapter;
import com.mytunes.viewModel.MasterViewModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MasterFragment extends Fragment implements OnItemClick {

    private DashboardActivity mDashboardActivity;
    private ShimmerFrameLayout mShimmerViewContainer;
    private MasterAdapter mAdapter;
    private MasterViewModel mViewModel;

    private boolean mIsDoubleBackToExitPressedOnce = false;
    private String mQuery;
    private int mPageIndex = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mDashboardActivity = (DashboardActivity) getActivity();

        mViewModel = new ViewModelProvider(mDashboardActivity).get(MasterViewModel.class);

        View rootView = inflater.inflate(R.layout.master_fragment, container, false);

        initUI(rootView);

        setHasOptionsMenu(true);

        Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mDashboardActivity.setSupportActionBar(mToolbar);
        mDashboardActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDashboardActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDashboardActivity.getSupportActionBar().setTitle(mDashboardActivity.getString(R.string.app_name));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.startShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;

                searchView.clearFocus();
                Utility.getInstance(mDashboardActivity).hideKeyboard(getView());

                new Handler().postDelayed(() -> executeData(query),200);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){

            if (mIsDoubleBackToExitPressedOnce) {
                mDashboardActivity.finishAffinity();
            }
            this.mIsDoubleBackToExitPressedOnce = true;
            Utility.getInstance(mDashboardActivity).showToast(getView().findViewById(R.id.coordinator), "Click again to exit.");

            new Handler().postDelayed(() -> mIsDoubleBackToExitPressedOnce = false, 2000);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItem(SearchData searchData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", searchData);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        mDashboardActivity.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, detailFragment).commit();
    }

    private void executeData(String query){
        mAdapter.clear();

        getView().findViewById(R.id.pnlEmpty).setVisibility(View.GONE);
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.VISIBLE);

        String searchText = query.trim().toLowerCase();
        int offset = mPageIndex * 10;

        mViewModel.getSearchLiveData(searchText, offset).removeObservers(mDashboardActivity);
        mViewModel.getSearchLiveData(searchText, offset).observe(mDashboardActivity, resultData -> loadData(resultData));
    }

    private void initUI(View rootView){
        Glide.with(getContext()).load(R.drawable.bg).into((ImageView)rootView.findViewById(R.id.imgEmpty));
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);

        RecyclerView rvSearch = rootView.findViewById(R.id.rvSearch);

        rvSearch.setHasFixedSize(false);

        rvSearch.setLayoutManager(new LinearLayoutManager(mDashboardActivity));

        mAdapter = new MasterAdapter(mDashboardActivity, this, new ArrayList<>());

        rvSearch.setAdapter(mAdapter);

        rvSearch.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));

        setPagination(rootView);
    }

    private void loadData(ResultData resultData){

        if (resultData != null){
            List<SearchData> data = resultData.result;

            if (!data.isEmpty()){

                //Dummy wrapperType
                for (int i = 0; i < 2; i++)
                    data.get(i).wrapperType = "VIDEO";

                mAdapter.updateData(data);

                getView().findViewById(R.id.pnlEmpty).setVisibility(View.GONE);
                getView().findViewById(R.id.pnlPage).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.rvSearch).setVisibility(View.VISIBLE);

            }
            else{
                getView().findViewById(R.id.pnlEmpty).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.pnlPage).setVisibility(View.GONE);
                getView().findViewById(R.id.rvSearch).setVisibility(View.GONE);
            }
        }
        else{
            getView().findViewById(R.id.pnlEmpty).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.pnlPage).setVisibility(View.GONE);
            getView().findViewById(R.id.rvSearch).setVisibility(View.GONE);
        }

        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private void setPagination(View rootView){

        ImageView imgPrevious = rootView.findViewById(R.id.imgPrevious);
        ImageView imgNext = rootView.findViewById(R.id.imgNext);

        TextView txtPage = rootView.findViewById(R.id.txtPage);
        txtPage.setText(String.valueOf(mPageIndex));

        imgPrevious.setOnClickListener(view -> {
            if (mPageIndex > 1){
                mPageIndex--;

                txtPage.setText(String.valueOf(mPageIndex));
                executeData(mQuery);

                if (imgNext.getVisibility() == View.GONE)
                    imgNext.setVisibility(View.VISIBLE);
            }

            if (mPageIndex == 1)
                imgPrevious.setVisibility(View.GONE);
        });

        imgNext.setOnClickListener(view -> {
            mPageIndex++;

            txtPage.setText(String.valueOf(mPageIndex));
            executeData(mQuery);

            if (imgPrevious.getVisibility() == View.GONE)
                imgPrevious.setVisibility(View.VISIBLE);

        });
    }
}