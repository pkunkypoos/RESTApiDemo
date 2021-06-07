package com.restapi.controller;

import com.restapi.dto.NamesAndPostcodeResult;
import com.restapi.dto.NamesAndPostcodesRequest;
import com.restapi.dto.SuburbRangeResult;
import com.restapi.model.Suburb;
import com.restapi.service.NamesAndPostcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private NamesAndPostcodeService nameAndPostcodeService;

    @PostMapping("/names-and-postcodes")
    public @ResponseBody
    NamesAndPostcodeResult setNamesAndPostcodes(@RequestBody NamesAndPostcodesRequest namesAndPostcodesRequest, HttpServletResponse httpServletResponse) {
        if (logger.isDebugEnabled()) {
            logger.debug("Request information - NamesAndPostcodesRequest: " + namesAndPostcodesRequest.toString());
        }

        List<Long> addedEntities = new ArrayList<>();
        try {
            for (Suburb newSuburb : nameAndPostcodeService.addSuburbs(parseNamesAndPostcodesRequest(namesAndPostcodesRequest))) {
                addedEntities.add(newSuburb.getId());
            }
        } catch (Exception e) {
            logger.error("Request Error: " + e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new NamesAndPostcodeResult(addedEntities, "Unable to add some/all results: " + e.getMessage());
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return new NamesAndPostcodeResult(addedEntities, "Name and Postcode pair added correctly");
    }

    @GetMapping("/names-and-character-count{lower}{upper}")
    public @ResponseBody
    SuburbRangeResult getNamesAndCharacterCount(@RequestParam Integer lower, @RequestParam Integer upper, HttpServletResponse httpServletResponse) {
        if (logger.isDebugEnabled()) {
            logger.debug("Request information - lower value:" + lower + " upper value: " + upper);
        }
        try {
            parseNamesAndCharacterCountRequest(lower, upper);
        } catch (Exception e) {
            logger.error("Request Error: " + e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new SuburbRangeResult(null, 0, "Request Error: " + e.getMessage());
        }
        List<Suburb> sortedSuburbs = nameAndPostcodeService.findAllSortedSuburbsInRange(lower.intValue(), upper.intValue());

        return new SuburbRangeResult(sortedSuburbs.stream().map(s -> s.getName()).toList(),
                sortedSuburbs.stream().mapToInt(sub -> sub.getName().length()).sum(),
                "A list of sorted names in the range and their character count");
    }

    // TODO: Since i've declared the setting of names/postcodes pairs as not-idempotent you'd no doubt have a
    //       matching DELETE endpoint to clear out old data. Not that postcodes change that often in practise ;)

    private void parseNamesAndCharacterCountRequest(Integer lower, Integer upper) {
        // Spring is of course handling if these items are null or are not numbers for us,
        // so lets not bother with testing for that. But we should stop people sending silly values!
        if (upper.compareTo(lower) < 0) {
            throw new IllegalArgumentException("upper is LESS THAN the lower value!");
        }

        if (lower.compareTo(upper) > 0) {
            throw new IllegalArgumentException("lower is GREATER THAT upper value!");
        }
    }

    private List<Suburb> parseNamesAndPostcodesRequest(NamesAndPostcodesRequest namesAndPostcodesRequest) {
        List<Suburb> suburbs = new ArrayList<>();
        namesAndPostcodesRequest.namesAndPostcodes.forEach((np) -> {
            if (!StringUtils.hasText(np.getName())) {
                throw new IllegalArgumentException("name property must be set");
            }

            if (!StringUtils.hasText(np.getPostcode())) {
                throw new IllegalArgumentException("postcode property must be set");
            }

            try {
                int postcode = Integer.parseInt(np.getPostcode());
                if (postcode >= 10000 || postcode < 1000) {
                    throw new IllegalArgumentException("That probably isn't a valid Australian postcode: " + postcode);
                }

                suburbs.add(new Suburb(np.getName(), postcode));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("That isn't a well formatted postcode: " + e.getMessage());
            }
        });

        return suburbs;
    }
}
