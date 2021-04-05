package lt.brilingas.guestregistry.controller.controllers;
import lt.brilingas.guestregistry.data.dto.person.PersonDTO;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.IPersonService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @PutMapping(path = "/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePersonById(@PathVariable String personId, @RequestBody PersonDTO personDTO) throws ResourceNotFoundException, FieldNotValidException {
        personService.updatePersonById(personId,personDTO);
    }
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> getAllPersons(){
        return personService.getAllPersons();
    }
    @GetMapping(path = "/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO getPersonById(@PathVariable String personId) throws FieldNotValidException {
        return personService.getPersonById(personId);
    }
    @DeleteMapping(path = "/{personId")
    @ResponseStatus(HttpStatus.OK)
    public void deletePersonById(@PathVariable String personId) throws FieldNotValidException, ResourceNotFoundException {
        personService.deletePersonById(personId);
    }
}
