package org.djar.board.domain;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    public enum Type {
        TASK, NOTE
    }

    public enum Status {
        PENDING, IN_PROCESS, COMPLETED
    }


    private String cardId;
    private String boardId;
    private String memberId;
    private Type type;
    private String description;
    private Status status;
}
