package lt.brilingas.guestregistry.dao.impl.mongo.dao;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.impl.IQueryBuilder;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper.LocationMapper;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper.PersonMapper;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.location.LocationEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.person.PersonEntity;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.IPersonRepository;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDAO implements IPersonDAO {
    @Autowired
    private IPersonRepository personRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IQueryBuilder queryBuilder;
    @Override
    public String insert(PersonDTO personDTO) {
        PersonEntity personEntity = PersonMapper.toEntity(personDTO);
        PersonEntity savedPerson = personRepository.insert(personEntity);
        return savedPerson.getId();
    }
    @Override
    public void update(PersonDTO personDTO) {
        PersonEntity personEntity = PersonMapper.toEntity(personDTO);
        personRepository.save(personEntity);
    }

    @Override
    public void deleteById(String personId) {
        personRepository.deleteById(personId);
    }
    @Override
    public Optional<PersonDTO> getById(String personId) {
        return personRepository.findById(personId).map(PersonMapper::toDTO);
    }
    @Override
    public List<PersonDTO> getAll() {
        List<PersonEntity> personEntityList=personRepository.findAll();
        return PersonMapper.toDTOLinkedList(personEntityList);
    }

    @Override
    public List<PersonDTO> getByFilter(Map<String, Map<QueryParameterFunction, String>> parameters) throws Exception {
        BasicQuery query = queryBuilder.build(parameters, PersonDTO.FIELDS_ALLOWED_IN_FILTER);
        List<PersonEntity> personEntityList = mongoTemplate.find(query, PersonEntity.class);
        return PersonMapper.toDTOLinkedList(personEntityList);
    }

    @Override
    public boolean existsById(String personId) {
        return personRepository.existsById(personId);
    }

    @Override
    public List<PersonDTO> getByName(String personName) {//?
        List<PersonDTO> personDTOList = personRepository.getByName(personName).stream().map(PersonMapper::toDTO).collect(Collectors.toList());
       return personDTOList;
    }


}
