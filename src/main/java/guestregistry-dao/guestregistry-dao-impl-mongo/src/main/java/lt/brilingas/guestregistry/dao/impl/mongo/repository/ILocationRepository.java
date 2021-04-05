package lt.brilingas.guestregistry.dao.impl.mongo.repository;

import lt.brilingas.guestregistry.dao.impl.mongo.entity.location.LocationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends MongoRepository<LocationEntity, String> {
}