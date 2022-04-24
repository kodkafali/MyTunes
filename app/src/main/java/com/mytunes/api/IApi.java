package com.mytunes.api;

import com.mytunes.model.ResultData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApi {

    @GET("/search")
    Call<ResultData> getSearchResult(@Query(value = "term") String term,
                                     @Query(value = "offset") int offset,
                                     @Query(value = "limit") int limit);
}
