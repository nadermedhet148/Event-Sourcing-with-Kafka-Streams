package org.djar.board.Domain;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private String clubId;
    private Set<String> memberIds = new HashSet<>();

    private Team() {
    }

    Team(String clubId) {
        this.clubId = clubId;
    }

    public String getClubId() {
        return clubId;
    }

    public boolean isMember(Member member) {
        return memberIds.contains(member.getId());
    }

    public void addMember(Member member) {
        memberIds.add(member.getId());
    }
}
