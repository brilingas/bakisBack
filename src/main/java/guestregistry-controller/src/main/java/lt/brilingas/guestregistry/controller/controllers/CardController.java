package lt.brilingas.guestregistry.controller.controllers;
import lt.brilingas.guestregistry.data.dto.card.CardDTO;
import lt.brilingas.guestregistry.data.dto.card.CardType;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import lt.brilingas.guestregistry.service.data.ICardService;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/cards")
@CrossOrigin(origins = "http://localhost:3000")
public class CardController {
    @Autowired
    private ICardService cardService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewCard(@RequestBody CardDTO cardDTO) throws FieldNotValidException {
        cardService.insertCard(cardDTO);
        return cardDTO.getId();
    }

    @PutMapping(path = "/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCardById(@PathVariable String cardId, @RequestBody CardDTO cardDTO ) throws FieldNotValidException, ResourceNotFoundException {
        cardService.updateCardById(cardId, cardDTO);
    }

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<CardDTO> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping(path = "/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardDTO getCardById(@PathVariable String cardId) throws FieldNotValidException {
        return cardService.getCardById(cardId);
    }

    @GetMapping(path = "/available/{cardType}/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CardDTO> getAvailableCards(@PathVariable(name="cardType",required = false) CardType cardType, @PathVariable(required = false) String locationId) throws FieldNotValidException {
            return cardService.getFilteredCards(cardType,locationId);
    }

    @DeleteMapping(path = "/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCardById(@PathVariable String cardId) throws FieldNotValidException, ResourceNotFoundException {
        cardService.deleteCardById(cardId);
    }
}