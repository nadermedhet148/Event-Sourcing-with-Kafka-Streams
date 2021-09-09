package org.djar.Common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreated extends Event {


    public enum Type {
        TASK, NOTE
    }


    private String cardId;
    private String boardId;
    private String memberId;
    private  Type type;


    @Override
    public String getAggId() {
        return boardId;
    }

}
