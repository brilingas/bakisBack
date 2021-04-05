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
    public String insertLocation(LocationDTO locationDTO) throws FieldNotValidException {
        if (locationDTO == null) throw new IllegalArgumentException();
        locationValidator.validateOnCreate(locationDTO);
        return locationDAO.insert(locationDTO);
    }

    @Override
    public void updateLocationById(String locationId, LocationDTO locationDTO)
            throws FieldNotValidException, ResourceNotFoundException {
        if (locationDTO == null || locationId == null) throw new IllegalArgumentException();
        locationDTO.setId(locationId);
        locationValidator.validateOnUpdate(locationDTO);
        if (locationDAO.existsById(locationId)) {
            locationDAO.update(locationDTO);
        } else {
            throw new ResourceNotFoundException("Location by ID = " + locationId + " not found");
        }
    }

    @Override
    public void deleteLocationById(String locationId) throws Exception {
        if (locationId == null) throw new IllegalArgumentException();
        locationValidator.validateOnDelete(locationId);
        locationDAO.deleteById(locationId);
    }

    @Override
    public LocationDTO getLocationById(String locationId) throws ResourceNotFoundException {
        if (locationId == null) throw new IllegalArgumentException();
        Optional<LocationDTO> locationOptional = locationDAO.getById(locationId);
        if (locationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Location by ID = " + locationId + " not found");
        } else {
            return locationOptional.get();
        }
    }

    @Override
    public List<LocationDTO> getAllLocations(Map<String, String> parameters) throws Exception {
        if (parameters == null) throw new IllegalArgumentException();
        if (parameters.isEmpty()) {
            return locationDAO.getAll();
        } else {
            return locationDAO.getByFilter(queryParametersCreator.create(parameters, LocationDTO.FIELDS_ALLOWED_IN_FILTER));
        }
    }

    @Override
    public boolean existsById(String locationId) {
        if (locationId == null) throw new IllegalArgumentException();
        return locationDAO.existsById(locationId);
    }
}
