package lt.brilingas.guestregistry.dao.impl.mongo.repository;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.person.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IPersonRepository extends MongoRepository<PersonEntity,String> {
     List<PersonEntity> getByName(String personName);
}
