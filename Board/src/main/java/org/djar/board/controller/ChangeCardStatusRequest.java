package org.djar.board.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCardStatusRequest {


    private String cardId;
    private String boardId;
    private String status;

}
