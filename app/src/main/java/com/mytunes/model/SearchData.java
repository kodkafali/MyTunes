package com.mytunes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchData implements Serializable {

    @SerializedName("wrapperType")
    @Expose
    public String wrapperType;

    @SerializedName("kind")
    @Expose
    public String kind;

    @SerializedName("artistId")
    @Expose
    public Integer artistId;

    @SerializedName("artistName")
    @Expose
    public String artistName;

    @SerializedName("trackName")
    @Expose
    public String trackName;

    @SerializedName("artworkUrl100")
    @Expose
    public String artworkUrl100;

    @SerializedName("collectionPrice")
    @Expose
    public Double collectionPrice;

    @SerializedName("trackPrice")
    @Expose
    public Double trackPrice;

    @SerializedName("trackRentalPrice")
    @Expose
    public Double trackRentalPrice;

    @SerializedName("releaseDate")
    @Expose
    public String releaseDate;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("currency")
    @Expose
    public String currency;

    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;

    @SerializedName("longDescription")
    @Expose
    public String longDescription;
}
