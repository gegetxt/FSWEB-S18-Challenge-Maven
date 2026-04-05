package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import org.springframework.http.HttpStatus;

public final class CardValidation {

    private CardValidation() {
    }

    public static void validateCard(Card card, boolean idRequired) {
        if (card == null) {
            throw new CardException("Card body cannot be null", HttpStatus.BAD_REQUEST);
        }

        if (idRequired && card.getId() == null) {
            throw new CardException("Card id is required", HttpStatus.BAD_REQUEST);
        }

        Type type = card.getType();
        Integer value = card.getValue();
        Color color = card.getColor();

        if (type == null && value == null) {
            throw new CardException("Card must have either type or value", HttpStatus.BAD_REQUEST);
        }

        if (type != null && value != null) {
            throw new CardException("Card cannot have both type and value", HttpStatus.BAD_REQUEST);
        }

        if (type == Type.JOKER) {
            if (value != null || color != null) {
                throw new CardException("JOKER card cannot have color or value", HttpStatus.BAD_REQUEST);
            }
            return;
        }

        if (color == null) {
            throw new CardException("Card color cannot be null", HttpStatus.BAD_REQUEST);
        }
    }

    public static String validateColor(String color) {
        try {
            return Color.valueOf(color.toUpperCase()).name();
        } catch (IllegalArgumentException exception) {
            throw new CardException("Invalid color: " + color, HttpStatus.BAD_REQUEST);
        }
    }

    public static String validateType(String type) {
        try {
            return Type.valueOf(type.toUpperCase()).name();
        } catch (IllegalArgumentException exception) {
            throw new CardException("Invalid type: " + type, HttpStatus.BAD_REQUEST);
        }
    }

    public static Integer validateValue(Integer value) {
        if (value == null) {
            throw new CardException("Value cannot be null", HttpStatus.BAD_REQUEST);
        }
        return value;
    }

    public static Long validateId(Long id) {
        if (id == null || id <= 0) {
            throw new CardException("Invalid id: " + id, HttpStatus.BAD_REQUEST);
        }
        return id;
    }
}
