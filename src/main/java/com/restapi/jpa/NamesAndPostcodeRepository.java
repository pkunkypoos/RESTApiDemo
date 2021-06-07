package com.restapi.jpa;

import com.restapi.model.Suburb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.stream.Stream;

public interface NamesAndPostcodeRepository extends PagingAndSortingRepository<Suburb, Long> {

    @Query("FROM Suburb WHERE postcode BETWEEN :lower AND :upper")
    Stream<Suburb> findAllNamesForRange(int lower, int upper);
}
