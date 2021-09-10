package org.djar.Common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreated extends Event {


    private String boardId;
    private String name;




    @Override
    public String getAggId() {
        return boardId;
    }

}
