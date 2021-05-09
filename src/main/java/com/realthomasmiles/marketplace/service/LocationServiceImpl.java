package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Location;
import com.realthomasmiles.marketplace.repository.marketplace.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LocationDto> getAllLocations() {
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                .map(location -> modelMapper.map(location, LocationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto getLocationByName(String name) {
        Optional<Location> location = Optional.ofNullable(locationRepository.findByNameIgnoreCase(name));
        if (location.isPresent()) {
            return modelMapper.map(location.get(), LocationDto.class);
        }

        throw exception(EntityType.LOCATION, ExceptionType.ENTITY_NOT_FOUND, name);
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }

}
