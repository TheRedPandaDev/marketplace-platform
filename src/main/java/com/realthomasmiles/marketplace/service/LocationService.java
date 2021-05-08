package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto getLocationByName(String name);

}
