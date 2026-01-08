package com.caching.proxy.weatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class NominatimPlaceDto {

    @JsonProperty("place_id")
    private long placeId;

    @JsonProperty("licence")
    private String licence;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private long osmId;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lon")
    private String lon;

    @JsonProperty("class")
    private String clazz;

    @JsonProperty("type")
    private String type;

    @JsonProperty("place_rank")
    private int placeRank;

    @JsonProperty("importance")
    private double importance;

    @JsonProperty("addresstype")
    private String addresstype;

    @JsonProperty("name")
    private String name;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("boundingbox")
    private List<String> boundingbox;

}

