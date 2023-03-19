package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EventLocationDto;
import ru.practicum.dto.NewEventLocationDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.model.EventLocation;
import ru.practicum.repository.LocationRepository;
import ru.practicum.service.LocationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public EventLocationDto addLocation(NewEventLocationDto newEventLocationDto) {
        return LocationMapper.toEventLocationDto(
                locationRepository.save(LocationMapper.toEventLocation(newEventLocationDto)));
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public EventLocationDto updateLocation(Long locationId, EventLocationDto updatedEventLocationDto) {
        EventLocation eventLocation = getLocationByIdRaw(locationId);
        if (updatedEventLocationDto.getLat() != null) {
            eventLocation.setLat(updatedEventLocationDto.getLat());
        }
        if (updatedEventLocationDto.getLon() != null) {
            eventLocation.setLon(updatedEventLocationDto.getLon());
        }
        if (updatedEventLocationDto.getRadius() != null) {
            eventLocation.setRadius(updatedEventLocationDto.getRadius());
        }
        if (updatedEventLocationDto.getName() != null) {
            eventLocation.setName(updatedEventLocationDto.getName());
        }
        return LocationMapper.toEventLocationDto(locationRepository.save(eventLocation));
    }

    @Override
    public List<EventLocationDto> getEventLocations(List<Long> locationIds, Integer from, Integer size) {
        return locationRepository.findAllById(locationIds).stream().map(LocationMapper::toEventLocationDto)
                .collect(Collectors.toList());
    }

    public EventLocation getLocationByIdRaw(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NotFoundException("location with id=" + locationId + " not found"));
    }
}
