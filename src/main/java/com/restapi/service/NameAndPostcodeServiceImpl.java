package com.restapi.service;

import com.restapi.jpa.NamesAndPostcodeRepository;
import com.restapi.model.Suburb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class NameAndPostcodeServiceImpl implements NamesAndPostcodeService {

    @Autowired
    private NamesAndPostcodeRepository repository;

    @Transactional
    public Iterable<Suburb> addSuburbs(List<Suburb> suburbs) {
        return repository.saveAll(suburbs);
    }

    @Transactional
    public List<Suburb> findAllSortedSuburbsInRange(int lower, int upper) {
        return repository.findAllNamesForRange(lower, upper)
                .sorted(Comparator.comparing(Suburb::getName)).toList();
    }
}
