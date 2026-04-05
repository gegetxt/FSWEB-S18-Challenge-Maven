package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private final EntityManager entityManager;

    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        Color parsedColor = Color.valueOf(color.toUpperCase());
        List<Card> cards = entityManager
                .createQuery(
                        "SELECT c FROM Card c WHERE c.color = " + Color.class.getName() + "." + parsedColor.name(),
                        Card.class)
                .getResultList();

        if (cards.isEmpty()) {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }

        return cards;
    }

    @Override
    public List<Card> findAll() {
        return entityManager.createQuery("SELECT c FROM Card c", Card.class).getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        List<Card> cards = entityManager
                .createQuery("SELECT c FROM Card c WHERE c.value = " + value, Card.class)
                .getResultList();

        if (cards.isEmpty()) {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }

        return cards;
    }

    @Override
    public List<Card> findByType(String type) {
        Type parsedType = Type.valueOf(type.toUpperCase());
        List<Card> cards = entityManager
                .createQuery(
                        "SELECT c FROM Card c WHERE c.type = " + Type.class.getName() + "." + parsedType.name(),
                        Card.class)
                .getResultList();

        if (cards.isEmpty()) {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }

        return cards;
    }

    @Override
    @Transactional
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    @Transactional
    public Card remove(Long id) {
        Card card = entityManager.find(Card.class, id);
        if (card == null) {
            throw new CardException("Card not found", HttpStatus.NOT_FOUND);
        }
        entityManager.remove(card);
        return card;
    }
}
