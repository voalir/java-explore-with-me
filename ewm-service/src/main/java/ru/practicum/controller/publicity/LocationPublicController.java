package ru.practicum.controller.publicity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.EventLocationDto;
import ru.practicum.service.LocationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/locations")
public class LocationPublicController {

    @Autowired
    LocationService locationService;

    @GetMapping
    List<EventLocationDto> getLocations(@RequestParam List<Long> locationIds,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return locationService.getEventLocations(locationIds, from, size);
    }
}
