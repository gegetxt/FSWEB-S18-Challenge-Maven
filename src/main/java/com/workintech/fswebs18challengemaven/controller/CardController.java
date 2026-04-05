package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;

    @GetMapping({"/cards", "/workintech/cards"})
    public List<Card> findAll() {
        try {
            return cardRepository.findAll();
        } catch (RuntimeException exception) {
            log.error("Failed to list cards", exception);
            throw exception;
        }
    }

    @GetMapping({"/cards/byColor/{color}", "/workintech/cards/byColor/{color}"})
    public List<Card> findByColor(@PathVariable String color) {
        try {
            return cardRepository.findByColor(color);
        } catch (RuntimeException exception) {
            log.error("Failed to find cards by color: {}", color, exception);
            throw exception;
        }
    }

    @GetMapping({"/cards/byValue/{value}", "/workintech/cards/byValue/{value}"})
    public List<Card> findByValue(@PathVariable Integer value) {
        try {
            return cardRepository.findByValue(value);
        } catch (RuntimeException exception) {
            log.error("Failed to find cards by value: {}", value, exception);
            throw exception;
        }
    }

    @GetMapping({"/cards/byType/{type}", "/workintech/cards/byType/{type}"})
    public List<Card> findByType(@PathVariable String type) {
        try {
            return cardRepository.findByType(type);
        } catch (RuntimeException exception) {
            log.error("Failed to find cards by type: {}", type, exception);
            throw exception;
        }
    }

    @PostMapping({"/cards", "/workintech/cards"})
    public Card save(@RequestBody Card card) {
        try {
            CardValidation.validateCard(card, false);
            return cardRepository.save(card);
        } catch (RuntimeException exception) {
            log.error("Failed to save card", exception);
            throw exception;
        }
    }

    @PutMapping({"/cards/", "/workintech/cards/"})
    public Card update(@RequestBody Card card) {
        try {
            if (card == null || card.getId() == null) {
                throw new IllegalArgumentException("Card id is required");
            }
            return cardRepository.update(card);
        } catch (RuntimeException exception) {
            log.error("Failed to update card with id: {}", card != null ? card.getId() : null, exception);
            throw exception;
        }
    }

    @DeleteMapping({"/cards/{id}", "/workintech/cards/{id}", "/workintech/card/{id}"})
    public Card remove(@PathVariable Long id) {
        try {
            return cardRepository.remove(CardValidation.validateId(id));
        } catch (RuntimeException exception) {
            log.error("Failed to remove card with id: {}", id, exception);
            throw exception;
        }
    }
}
