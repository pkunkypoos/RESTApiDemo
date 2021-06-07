package com.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "postcode"
})
public class NamePostcodePair {

    @JsonProperty("name")
    private String name;
    @JsonProperty("postcode")
    private String postcode;

    public NamePostcodePair() {
    }

    public NamePostcodePair(String name, String postcode) {
        this.name = name;
        this.postcode = postcode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "NamePostcodePair{" +
                "name='" + name + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
