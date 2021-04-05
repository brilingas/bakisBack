package lt.brilingas.guestregistry.dao.impl.mongo.repository;
import lt.brilingas.guestregistry.dao.impl.mongo.entity.worker.WorkerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkerRepository extends MongoRepository<WorkerEntity, String> {
}

