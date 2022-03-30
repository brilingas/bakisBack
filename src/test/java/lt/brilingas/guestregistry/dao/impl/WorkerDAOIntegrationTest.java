//package lt.brilingas.guestregistry.dao.impl.mongo.dao;
//import lt.brilingas.guestregistry.dao.api.IWorkerDAO;
//import lt.brilingas.guestregistry.dao.api.QueryParameterFunction;
//import lt.brilingas.guestregistry.dao.impl.config.DAOTestConfig;
//import lt.brilingas.guestregistry.dao.impl.mongo.entity.worker.WorkerEntity;
//import lt.brilingas.guestregistry.dao.impl.mongo.repository.IWorkerRepository;
//import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.ContextConfiguration;
//import java.util.Collections;
//import java.util.List;
//
//@DataMongoTest
//@ContextConfiguration(classes = {DAOTestConfig.class})
//public class WorkerDAOIntegrationTest {
//    @Autowired
//    private IWorkerDAO workerDAO;
//    @Autowired
//    private IWorkerRepository workerRepository;
//
//    @Test
//    public void insertTest() {
//        WorkerDTO worker = createWorkerDTO("Abc");
//        Assertions.assertTrue(workerRepository.existsById(workerDAO.insert(worker)));
//    }
//
//    @Test
//    public void updateTest() {
//        String id = insertNewWorkerWithPositionToDB("Abc");
//        String newPosition = "Def";
//        WorkerDTO workerDTO = createWorkerDTO(newPosition);
//        workerDTO.setId(id);
//        workerDAO.update(workerDTO);
//        Assertions.assertEquals(newPosition, workerRepository.findById(id).get().getPosition());
//    }
//
//    @Test
//    public void deleteByIdTest() {
//        String id = insertNewWorkerWithPositionToDB("Abc");
//        workerDAO.deleteById(id);
//        Assertions.assertFalse(workerRepository.existsById(id));
//        Assertions.assertThrows(IllegalArgumentException.class, () -> workerDAO.deleteById(null));
//    }
//
//    @Test
//    public void findByIdTest() {
//        String position = "Abc";
//        String id = insertNewWorkerWithPositionToDB(position);
//        Assertions.assertEquals(position, workerDAO.findById(id).get().getPosition());
//        Assertions.assertThrows(IllegalArgumentException.class, () -> workerDAO.findById(null));
//    }
//
//    @Test
//    public void findAllTest() {
//        workerRepository.deleteAll();
//        Assertions.assertTrue(workerDAO.findAll().isEmpty());
//        insertNewWorkerWithPositionToDB("Abc");
//        Assertions.assertEquals(1, workerDAO.findAll().size());
//    }
//
//    @Test
//    public void findByFilterTest() throws Exception {
//        workerRepository.deleteAll();
//        Assertions.assertTrue(workerDAO.findAll().isEmpty());
//        String position = "Abc";
//        insertNewWorkerWithPositionToDB(position);
//        insertNewWorkerWithPositionToDB("Def");
//        List<WorkerDTO> result = workerDAO.findByFilter(
//                Collections.singletonMap("position", Collections.singletonMap(QueryParameterFunction.EQUALS, position)));
//        Assertions.assertEquals(1, result.size());
//    }
//
//    @Test
//    public void existsByIdTest() {
//        String id = insertNewWorkerWithPositionToDB("Abc");
//        Assertions.assertTrue(workerDAO.existsById(id));
//        workerRepository.deleteAll();
//        Assertions.assertFalse(workerDAO.existsById(id));
//        Assertions.assertThrows(IllegalArgumentException.class, () -> workerDAO.existsById(null));
//    }
//
//    private WorkerEntity createWorkerEntity(String position) {
//        WorkerEntity worker = new WorkerEntity();
//        worker.setPosition(position);
//        return worker;
//    }
//
//    private WorkerDTO createWorkerDTO(String position) {
//        WorkerDTO worker = new WorkerDTO();
//        worker.setPosition(position);
//        return worker;
//    }
//
//    private String insertNewWorkerWithPositionToDB(String position) {
//        WorkerEntity worker = createWorkerEntity(position);
//        return workerRepository.insert(worker).getId();
//    }
//}
