package org.djar.Common.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreated extends Event {


    private String teamId;
    private String name;




    @Override
    public String getAggId() {
        return teamId;
    }

}
