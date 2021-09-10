package org.djar.member.controller;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamRequest {

    private String teamId;
    private String name;
}
