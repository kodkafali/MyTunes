package com.mytunes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mytunes.model.ResultData;
import com.mytunes.model.SearchData;
import com.mytunes.repository.MasterRepository;

import org.jetbrains.annotations.NotNull;

public class MasterViewModel extends AndroidViewModel {

    private MasterRepository mMasterRepos;

    public MasterViewModel(@NonNull @NotNull Application application) {
        super(application);

        mMasterRepos = new MasterRepository(application);
    }

    public MutableLiveData<ResultData> getSearchLiveData(String term, int offset){
        return mMasterRepos.getSearchData(term, offset);
    }


}