package lt.brilingas.guestregistry.dao.impl.repository;

import lt.brilingas.guestregistry.dao.impl.entity.event.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends MongoRepository<EventEntity, String> {
}
