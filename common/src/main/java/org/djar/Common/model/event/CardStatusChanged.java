package org.djar.Common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusChanged extends Event {

    public enum Status {
        PENDING, IN_PROCESS, COMPLETED
    }


    private String cardId;
    private String boardId;
    private Status status;


    @Override
    public String getAggId() {
        return boardId;
    }
}
