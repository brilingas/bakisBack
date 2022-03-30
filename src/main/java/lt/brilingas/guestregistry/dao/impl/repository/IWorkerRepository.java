package lt.brilingas.guestregistry.dao.impl.repository;
import lt.brilingas.guestregistry.dao.impl.entity.worker.WorkerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkerRepository extends MongoRepository<WorkerEntity, String> {
}

