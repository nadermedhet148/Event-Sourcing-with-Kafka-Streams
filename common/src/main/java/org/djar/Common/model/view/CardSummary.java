package org.djar.Common.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.djar.Common.model.event.CardCreated;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardSummary {

    private Integer tasksCount;
    private Integer notesCount;

    public CardSummary aggregate(CardCreated createdCard) {
        if(createdCard.getType() == CardCreated.Type.NOTE )
            this.notesCount += 1;
        if(createdCard.getType() == CardCreated.Type.TASK )
            this.tasksCount += 1;
        return this;
    }
}
