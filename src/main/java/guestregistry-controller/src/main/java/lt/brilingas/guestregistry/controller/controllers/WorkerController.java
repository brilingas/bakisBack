package lt.brilingas.guestregistry.controller.controllers;
import lt.brilingas.guestregistry.data.dto.worker.LoginStatus;
import lt.brilingas.guestregistry.data.dto.worker.WorkerDTO;
import lt.brilingas.guestregistry.service.data.DTOReferenceException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.IWorkerService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workers" )
public class WorkerController {
    @Autowired
    private IWorkerService workerService;

    @PostMapping(path ={"","/signup"} )
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewWorker(@RequestBody WorkerDTO worker)
            throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        return workerService.insertWorker(worker);
    }
    @PostMapping(path ={"/login"} )
    @ResponseStatus(HttpStatus.CREATED)
    public LoginStatus login (@RequestBody WorkerDTO workerDTO)
            throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        return workerService.login(workerDTO);
    }

    @GetMapping(path = "/{workerId:[a-f0-9]{24}}" )
    public WorkerDTO getWorkerById(@PathVariable String workerId) throws ResourceNotFoundException {
        return workerService.getWorkerById(workerId);
    }

    @GetMapping(path = "" )
    public List<WorkerDTO> getWorkers(@RequestParam Map<String, String> parameters) throws Exception {
        return workerService.getAllWorkers(parameters);
    }
    @PutMapping(path = "/{workerId:[a-f0-9]{24}}" )
    public void updateWorkerById(@PathVariable String workerId, @RequestBody WorkerDTO worker)
            throws FieldNotValidException, DTOReferenceException, ResourceNotFoundException {
        workerService.updateWorkerById(workerId, worker);
    }

    @DeleteMapping(path = "/{workerId:[a-f0-9]{24}}" )
    public void deleteWorkerById(@PathVariable String workerId) throws ResourceNotFoundException, FieldNotValidException {
        workerService.deleteWorkerById(workerId);
    }
}
