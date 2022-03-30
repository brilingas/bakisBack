package lt.brilingas.guestregistry.dao.impl.repository;
import lt.brilingas.guestregistry.dao.impl.entity.location.LocationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends MongoRepository<LocationEntity, String> {
}