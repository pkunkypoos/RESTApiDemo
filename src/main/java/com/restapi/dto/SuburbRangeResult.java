package com.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({
        "orderedNames",
        "characterCount"
})
public class SuburbRangeResult {

    @JsonProperty("orderedNames")
    private final List<String> suburbs;
    @JsonProperty("characterCount")
    private int charCount;
    @JsonProperty("statusMessage")
    private String suburbRangeResultMessage;

    public SuburbRangeResult(List<String> suburbs, int charCount, String msg) {
        this.suburbs = suburbs;
        this.charCount = charCount;
        this.suburbRangeResultMessage = msg;
    }

}
