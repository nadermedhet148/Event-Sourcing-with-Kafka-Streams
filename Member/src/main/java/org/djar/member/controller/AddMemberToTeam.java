package org.djar.member.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberToTeam {


    private String teamId;
    private String memberId;
    private String name;

}
