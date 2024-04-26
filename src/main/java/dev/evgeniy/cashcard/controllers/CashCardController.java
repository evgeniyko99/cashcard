package dev.evgeniy.cashcard.controllers;

import dev.evgeniy.cashcard.models.CashCard;
import dev.evgeniy.cashcard.repositories.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cardRepository;

    public CashCardController(CashCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{requestedId}")
    CashCard findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCard = cardRepository.findById(requestedId);
        if(cashCard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return cashCard.get();
    }



    @PostMapping("")
    ResponseEntity<Void> create(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

}
