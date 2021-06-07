package com.restapi.service;

import com.restapi.model.Suburb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NamesAndPostcodeService {

    /**
     * Just a proxy for saveAll
     *
     * @param suburb
     * @return a list of saved suburbs
     */
    Iterable<Suburb> addSuburbs(List<Suburb> suburb);

    /**
     * Retrieve all saved suburbs in a postcode range - sorted by name
     *
     * @param lower
     * @param upper
     * @return sorted list of suburbs by name
     */
    List<Suburb> findAllSortedSuburbsInRange(int lower, int upper);

}
