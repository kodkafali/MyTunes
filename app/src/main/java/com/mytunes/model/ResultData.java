package com.mytunes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultData {

    @SerializedName("resultCount")
    @Expose
    public Integer resultCount;

    @SerializedName("results")
    @Expose
    public List<SearchData> result = new ArrayList<>();
}
