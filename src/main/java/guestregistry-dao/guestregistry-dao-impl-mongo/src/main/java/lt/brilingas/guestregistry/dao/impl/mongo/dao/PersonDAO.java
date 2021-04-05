package lt.brilingas.guestregistry.dao.impl.mongo.dao;
import lt.brilingas.guestregistry.dao.api.IPersonDAO;
import lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper.PersonMapper;
import lt.brilingas.guestregistry.dao.impl.mongo.repository.IPersonRepository;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDAO implements IPersonDAO {
    @Autowired
    private IPersonRepository personRepository;
    @Override
    public String insert(PersonDTO personDTO) {
        personRepository.save(PersonMapper.toEntity(personDTO));
        return personDTO.getId();
    }
    @Override
    public Optional<PersonDTO> getById(String personId) {
        return personRepository.findById(personId).map(PersonMapper::toDTO);
    }

    @Override
    public List<PersonDTO> getByName(String personName) {
        List<PersonDTO> personDTOList = personRepository.getByName(personName).stream().map(PersonMapper::toDTO).collect(Collectors.toList());
       return personDTOList;
    }

    @Override
    public List<PersonDTO> getAll() {
        List<PersonDTO> listOfPersonDTO = personRepository.findAll().stream().map(PersonMapper::toDTO).collect(Collectors.toList());
        return listOfPersonDTO;
    }

    @Override
    public void update(PersonDTO personDTO) {
        personRepository.save(PersonMapper.toEntity(personDTO));
    }

    @Override
    public void deleteById(String personId) {
        personRepository.deleteById(personId);
    }
}
