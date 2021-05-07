package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/all")
    public Response<Object> getAllLocations() {
        return Response
                .ok()
                .setPayload(locationService.getAllLocations());
    }

}
