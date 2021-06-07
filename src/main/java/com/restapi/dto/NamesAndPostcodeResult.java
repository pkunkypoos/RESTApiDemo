package com.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({
        "newNamesAndPostcodes",
        "statusMessage"
})
public class NamesAndPostcodeResult {

    @JsonProperty("newNamesAndPostcodes")
    private List<Long> newNamesAndPostcodes;
    @JsonProperty("statusMessage")
    private String namesAndPostcodeStatusMessage;

    public NamesAndPostcodeResult(List<Long> addedEntities, String msg) {
        this.newNamesAndPostcodes = addedEntities;
        this.namesAndPostcodeStatusMessage = msg;
    }
}
