package org.djar.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private String teamId;
    private String name;
    private ArrayList<Member> members = new ArrayList<Member>();





    public void addMember(Member member) {
        members.add(member);
    }
}
