package com.restapi.service;

import com.restapi.jpa.NamesAndPostcodeRepository;
import com.restapi.model.Suburb;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class NamesAndPostcodeServiceTests {

    private NamesAndPostcodeService namesAndPostcodeService = Mockito.mock(NamesAndPostcodeService.class);
    @MockBean
    private NamesAndPostcodeRepository namesAndPostcodeRepository;

    @Test
    public void whenAddSuburbs_returnsResults() throws Exception {
        List<Suburb> testList = new ArrayList<>();
        testList.add(new Suburb("some_suburb", 6000));
        testList.add(new Suburb("some_other_suburb", 6001));

        Mockito.when(namesAndPostcodeService.addSuburbs(Mockito.anyList())).thenReturn(testList);
        Iterable<Suburb> results = namesAndPostcodeService.addSuburbs(testList);
        // really just testing jpa "saveAll"
        Assert.assertTrue(results.equals(testList));
    }

    @Test
    public void whenFindAllSortedSuburbsInRange_resultsSorted() throws Exception {
        List<Suburb> testList = new ArrayList<>();
        testList.add(new Suburb("Perth", 6999));
        testList.add(new Suburb("Carlisle", 6001));

        Mockito.when(namesAndPostcodeService.findAllSortedSuburbsInRange(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(testList.stream().sorted(Comparator.comparing(Suburb::getName)).toList());
        List<Suburb> resultsList = namesAndPostcodeService.findAllSortedSuburbsInRange(6000, 7000);
        // Carlisle comes before Perth!
        Assert.assertTrue(resultsList.get(0).getName().equals("Carlisle"));
    }

}
