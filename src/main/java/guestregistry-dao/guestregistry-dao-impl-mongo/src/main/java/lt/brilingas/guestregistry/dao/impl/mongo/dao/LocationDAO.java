package lt.brilingas.guestregistry.dao.impl.mongo.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.brilingas.guestregistry.dao.api.ILocationDAO;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.impl.IQueryBuilder;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper.LocationMapper;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.location.LocationEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.ILocationRepository;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class LocationDAO implements ILocationDAO {
    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IQueryBuilder queryBuilder;

    @Override
    public String insert(LocationDTO location) {
        LocationEntity locationEntity = LocationMapper.toEntity(location);
        LocationEntity savedLocation = locationRepository.insert(locationEntity);
        return savedLocation.getId();
    }

    @Override
    public void update(LocationDTO location) {
        LocationEntity locationEntity = LocationMapper.toEntity(location);
        locationRepository.save(locationEntity);
    }

    @Override
    public void deleteById(String locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public Optional<LocationDTO> findById(String locationId) {
        return locationRepository.findById(locationId).map(LocationMapper::toDTO);
    }

    @Override
    public List<LocationDTO> findAll() {
        List<LocationEntity> listEntity = locationRepository.findAll();
        return LocationMapper.toDTOLinkedList(listEntity);
    }

    @Override
    public List<LocationDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters)
            throws JsonProcessingException, ParameterNotValidException {
        BasicQuery query = queryBuilder.build(parameters, LocationDTO.FIELDS_ALLOWED_IN_FILTER);
        List<LocationEntity> listEntity = mongoTemplate.find(query, LocationEntity.class);
        return LocationMapper.toDTOLinkedList(listEntity);
    }

    @Override
    public boolean existsById(String locationId) {
        return locationRepository.existsById(locationId);
    }
}
