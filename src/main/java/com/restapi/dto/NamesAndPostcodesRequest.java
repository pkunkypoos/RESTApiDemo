package com.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({
        "namesAndPostCodes"
})
public class NamesAndPostcodesRequest {

    @JsonProperty("namesAndPostCodes")
    public List<NamePostcodePair> namesAndPostcodes;

    @JsonProperty("namesAndPostCodes")
    public List<NamePostcodePair> getNamesAndPostcodes() {
        return namesAndPostcodes;
    }

    @JsonProperty("namesAndPostCodes")
    public void setNamesAndPostcodes(List<NamePostcodePair> namesAndPostcodes) {
        this.namesAndPostcodes = namesAndPostcodes;
    }

    @Override
    public String toString() {
        return "NamesAndPostcodesRequest{" +
                "namesAndPostcodes=" + namesAndPostcodes +
                '}';
    }
}
