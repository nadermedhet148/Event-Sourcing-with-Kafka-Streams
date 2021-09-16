package org.djar.board.controller;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    private String cardId;
    private String boardId;
    private String memberId;
    private String type;
    private String description;
}
