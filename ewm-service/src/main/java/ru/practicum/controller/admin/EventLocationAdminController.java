package ru.practicum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventLocationDto;
import ru.practicum.dto.NewEventLocationDto;
import ru.practicum.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/locations")
@Validated
public class EventLocationAdminController {

    @Autowired
    LocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventLocationDto addLocation(@Valid @RequestBody NewEventLocationDto newEventLocationDto) {
        return locationService.addLocation(newEventLocationDto);
    }

    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
    }

    @PatchMapping("/{locationId}")
    EventLocationDto updateLocation(@PathVariable Long locationId,
                                    @Valid @RequestBody EventLocationDto updatedEventLocationDto) {
        return locationService.updateLocation(locationId, updatedEventLocationDto);
    }

    @GetMapping
    List<EventLocationDto> getLocations(@RequestParam List<Long> locationIds,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return locationService.getEventLocations(locationIds, from, size);
    }
}
