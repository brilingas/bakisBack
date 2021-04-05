package lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.person.PersonEntity;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;

public class PersonMapper {
    public static PersonDTO toDTO(PersonEntity personEntity) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(personEntity.getId());
        personDTO.setName(personEntity.getName());
        personDTO.setSurname(personEntity.getSurname());
        personDTO.setBirthday(personEntity.getBirthday());
        personDTO.setPhoneNumber(personEntity.getPhoneNumber());
        personDTO.setEmail(personEntity.getEmail());
        personDTO.setImage(personEntity.getImage());
        personDTO.setSignature(personEntity.getSignature());
        personDTO.setAddress(personEntity.getAddress());
        personDTO.setGender(personEntity.getGender());
        return personDTO;
    }
    public static PersonEntity toEntity(PersonDTO personDTO) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(personDTO.getId());
        personEntity.setName(personDTO.getName());
        personEntity.setSurname(personDTO.getSurname());
        personEntity.setBirthday(personDTO.getBirthday());
        personEntity.setPhoneNumber(personDTO.getPhoneNumber());
        personEntity.setEmail(personDTO.getEmail());
        personEntity.setImage(personDTO.getImage());
        personEntity.setSignature(personDTO.getSignature());
        personEntity.setAddress(personDTO.getAddress());
        personEntity.setGender(personDTO.getGender());
        return personEntity;
    }
}
