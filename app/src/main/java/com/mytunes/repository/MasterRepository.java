package com.mytunes.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.mytunes.api.ApiClient;
import com.mytunes.api.IApi;
import com.mytunes.model.ResultData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterRepository {

    private Application mApplication;
    private IApi mApiClient;

    public MasterRepository(Application application){
        this.mApplication = application;
        mApiClient = ApiClient.getClient().create(IApi.class);
    }

    public MutableLiveData<ResultData> getSearchData(String term, int offset){
        final MutableLiveData<ResultData> mutableLiveData = new MutableLiveData<>();

        mApiClient.getSearchResult(term, offset, 10).enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                call.cancel();
                mutableLiveData.postValue(null);
            }
        });

        return mutableLiveData;
    }
}
