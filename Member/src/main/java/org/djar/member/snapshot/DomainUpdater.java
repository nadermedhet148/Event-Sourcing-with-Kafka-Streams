package org.djar.member.snapshot;

import static org.djar.Common.stream.StreamsUtils.addProcessor;
import static org.djar.Common.stream.StreamsUtils.addStore;

import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.KeyValueStore;
import org.djar.Common.model.event.MemberAddedToTeam;
import org.djar.Common.model.event.TeamCreated;
import org.djar.member.domain.Member;
import org.djar.member.domain.Team;

import java.util.ArrayList;
import java.util.Objects;

public class DomainUpdater {

    public static final String TEAM_STORE = "team_store";

    public void init(Topology topology) {
        addProcessor(topology, TeamCreated.class, (eventId, event, store) -> {
            Team team = new Team(event.getTeamId(), event.getName() , new ArrayList<Member>());
            store.put(team.getTeamId(), team);
        }, TEAM_STORE);

        addProcessor(topology, MemberAddedToTeam.class, (eventId, event, store) -> {
            Member member = new Member(event.getMemberId(), event.getName());
            Team team = findTeam(store, event.getTeamId());
            team.addMember(member);
            store.put(team.getTeamId(), team);
        }, TEAM_STORE);

        addStore(topology, Team.class, TEAM_STORE, new Class[] {TeamCreated.class , MemberAddedToTeam.class});
    }

    private Team findTeam(KeyValueStore<String, Object> store, String teamId) {
        return (Team) Objects.requireNonNull(store.get(teamId), "Team not found: " + teamId);
    }
}
