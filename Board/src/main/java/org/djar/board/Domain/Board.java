package org.djar.board.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {


    private String boardId;
    private String name;
    private Team members;
    private List<Card> pendingCards = new ArrayList<>();
    private List<Card> inProgressCards = new ArrayList<>();
    private List<Card> completedCards = new ArrayList<>();







    public Card newCard(
             String cardId,
             String memberId,
             Card.Type type,
             String description
    ) {

        Card card = Card.builder().
                cardId(cardId).
                boardId(this.boardId).
                status(Card.Status.PENDING).
                description(description).
                memberId(memberId).
                type(type).
                build();
        this.pendingCards.add(card);
        return card;

    }

    public Card workOnCard(String cardId) throws Exception {
        Card card = this.pendingCards.stream().
                filter(ele -> ele.getCardId() == cardId).
                findAny().orElse(null);
        if(card.equals(null)){
           throw new Exception("Card not exited");
        }
        pendingCards.remove(card);
        inProgressCards.add(card);
        return card;
    }

    public Card completeCard(String cardId) throws Exception {
        Card card = this.inProgressCards.stream().
                filter(ele -> ele.getCardId() == cardId).
                findAny().orElse(null);
        if(card.equals(null)){
            throw new Exception("Card not exited");
        }
        inProgressCards.remove(card);
        completedCards.add(card);
        return card;
    }

}
