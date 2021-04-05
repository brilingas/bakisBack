package lt.brilingas.guestregistry.service.impl;

import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.service.data.ILocationService;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.impl.validation.IQueryParametersCreator;
import lt.brilingas.guestregistry.service.impl.validation.ILocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LocationService implements ILocationService {
    @Autowired
    private ILocationDAO locationDAO;
    @Autowired
    private ILocationValidator locationValidator;
    @Autowired
    private IQueryParametersCreator queryParametersCreator;


    @Override
    public String insertLocation(LocationDTO location) throws FieldNotValidException {
        if (location == null) throw new IllegalArgumentException();
        locationValidator.validateOnCreate(location);
        return locationDAO.insert(location);
    }

    @Override
    public void updateLocation(String locationId, LocationDTO locationForUpdate)
            throws FieldNotValidException, ResourceNotFoundException {
        if (locationForUpdate == null || locationId == null) throw new IllegalArgumentException();
        locationForUpdate.setId(locationId);
        locationValidator.validateOnUpdate(locationForUpdate);
        if (locationDAO.existsById(locationId)) {
            locationDAO.update(locationForUpdate);
        } else {
            throw new ResourceNotFoundException("Location by ID = " + locationId + " not found");
        }
    }

    @Override
    public void deleteLocation(String locationId) throws Exception {
        if (locationId == null) throw new IllegalArgumentException();
        locationValidator.validateOnDelete(locationId);
        locationDAO.deleteById(locationId);
    }

    @Override
    public LocationDTO getLocation(String locationId) throws ResourceNotFoundException {
        if (locationId == null) throw new IllegalArgumentException();
        Optional<LocationDTO> locationOptional = locationDAO.findById(locationId);
        if (locationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Location by ID = " + locationId + " not found");
        } else {
            return locationOptional.get();
        }
    }

    @Override
    public List<LocationDTO> getLocations(Map<String, String> parameters) throws Exception {
        if (parameters == null) throw new IllegalArgumentException();
        if (parameters.isEmpty()) {
            return locationDAO.findAll();
        } else {
            return locationDAO.findByFilter(queryParametersCreator.create(parameters, LocationDTO.FIELDS_ALLOWED_IN_FILTER));
        }
    }

    @Override
    public boolean existsById(String locationId) {
        if (locationId == null) throw new IllegalArgumentException();
        return locationDAO.existsById(locationId);
    }
}
