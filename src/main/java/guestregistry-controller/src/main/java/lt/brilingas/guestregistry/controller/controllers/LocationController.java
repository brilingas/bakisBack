package lt.brilingas.guestregistry.controller.controllers;

import lt.brilingas.guestregistry.service.data.ILocationService;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private ILocationService locationService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewLocation(@RequestBody LocationDTO location) throws FieldNotValidException {
        return locationService.insertLocation(location);
    }

    @GetMapping(path = "/{locationId:[a-f0-9]{24}}")
    public LocationDTO getLocationById(@PathVariable String locationId) throws ResourceNotFoundException {
        return locationService.getLocationById(locationId);
    }

    @GetMapping(path = "")
    public List<LocationDTO> getLocations(@RequestParam Map<String, String> parameters) throws Exception {
        return locationService.getAllLocations(parameters);
    }

    @PutMapping(path = "/{locationId:[a-f0-9]{24}}")
    public void updateLocationById(@PathVariable String locationId, @RequestBody LocationDTO location)
            throws FieldNotValidException, ResourceNotFoundException {
        locationService.updateLocationById(locationId, location);
    }

    @DeleteMapping(path = "/{locationId:[a-f0-9]{24}}")
    public void deleteLocationById(@PathVariable String locationId) throws Exception {
        locationService.deleteLocationById(locationId);
    }
}