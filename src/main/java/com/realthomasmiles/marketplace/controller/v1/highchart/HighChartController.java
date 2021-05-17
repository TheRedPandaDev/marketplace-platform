package com.realthomasmiles.marketplace.controller.v1.highchart;

import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.CategoryService;
import com.realthomasmiles.marketplace.service.LocationService;
import com.realthomasmiles.marketplace.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/highchart")
public class HighChartController {

    @Autowired
    private PostingService postingService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Long>> getCategoriesPieChart() {
        Map<String, Long> graphData = new TreeMap<>();
        List<CategoryDto> categories = categoryService.getAllCategories();
        categories.forEach(categoryDto -> graphData.put(categoryDto.getName(), postingService.getCountByCategory(categoryDto)));

        return new ResponseEntity<>(graphData, HttpStatus.OK);
    }

    @GetMapping("/locations")
    public ResponseEntity<Map<String, Long>> getLocationsPieChart() {
        Map<String, Long> graphData = new TreeMap<>();
        List<LocationDto> locations = locationService.getAllLocations();
        locations.forEach(locationDto -> graphData.put(locationDto.getName(), postingService.getCountByLocation(locationDto)));

        return new ResponseEntity<>(graphData, HttpStatus.OK);
    }

}
