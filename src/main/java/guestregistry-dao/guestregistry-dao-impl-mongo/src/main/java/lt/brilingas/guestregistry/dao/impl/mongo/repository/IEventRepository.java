package lt.brilingas.guestregistry.dao.impl.mongo.repository;

import lt.brilingas.guestregistry.dao.impl.mongo.entity.event.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends MongoRepository<EventEntity, String> {
}
