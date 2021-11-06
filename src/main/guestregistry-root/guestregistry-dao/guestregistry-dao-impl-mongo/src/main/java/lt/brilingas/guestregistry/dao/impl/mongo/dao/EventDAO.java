package lt.brilingas.guestregistry.dao.impl.mongo.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.brilingas.guestregistry.dao.api.IEventDAO;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.impl.IQueryBuilder;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper.EventMapper;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.event.EventEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.IEventRepository;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EventDAO implements IEventDAO {
    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IQueryBuilder queryBuilder;

    @Override
    public String insert(EventDTO event) {
        EventEntity eventEntity = EventMapper.toEntity(event);
        EventEntity savedEntity = eventRepository.insert(eventEntity);
        return savedEntity.getId();
    }

    @Override
    public void update(EventDTO event) {
        EventEntity eventEntity = EventMapper.toEntity(event);
        eventRepository.save(eventEntity);
    }

    @Override
    public void deleteById(String eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public Optional<EventDTO> findById(String eventId) {
        return eventRepository.findById(eventId).map(EventMapper::toDTO);
    }

    @Override
    public List<EventDTO> findAll() {
        List<EventEntity> listEntity = eventRepository.findAll();
        return EventMapper.toDTOLinkedList(listEntity);
    }

    @Override
    public List<EventDTO> findByFilter(Map<String, Map<QueryParameterFunction, String>> parameters)
            throws JsonProcessingException, ParameterNotValidException {
        BasicQuery query = queryBuilder.build(parameters, EventDTO.FIELDS_ALLOWED_IN_FILTER);
        List<EventEntity> listEntity = mongoTemplate.find(query, EventEntity.class);
        return EventMapper.toDTOLinkedList(listEntity);
    }

    @Override
    public boolean existsById(String eventId) {
        return eventRepository.existsById(eventId);
    }
}
