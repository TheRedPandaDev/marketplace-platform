package com.realthomasmiles.marketplace.dto.mapper;

import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.model.marketplace.Location;

public class LocationMapper {
    public static LocationDto toLocationDto(Location location) {
        return new LocationDto()
                .setId(location.getId())
                .setName(location.getName());
    }
}
