package lt.brilingas.guestregistry.controller.controllers;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.IPersonService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewPerson(@RequestBody PersonDTO personDTO) throws FieldNotValidException {
        personService.insertPerson(personDTO);
        return personDTO.getId();
    }
    @GetMapping(path = "/{personId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO getPersonById(@PathVariable String personId) throws ResourceNotFoundException {
        return personService.getPersonById(personId);
    }
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> getAllPersons(@RequestParam Map<String, String> parameters) throws Exception {
        return personService.getAllPersons(parameters);
    }
    @PutMapping(path = "/{personId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePersonById(@PathVariable String personId, @RequestBody PersonDTO personDTO) throws FieldNotValidException,ResourceNotFoundException {
        personService.updatePersonById(personId,personDTO);
    }
    @DeleteMapping(path = "/{personId:[a-f0-9]{24}}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePersonById(@PathVariable String personId) throws Exception {
        personService.deletePersonById(personId);
    }
}
