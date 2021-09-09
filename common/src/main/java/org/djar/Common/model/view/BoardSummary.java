package org.djar.Common.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSummary {

    private String matchId;
    private Integer tasksCount;
    private Integer notesCount;




//    public MatchScore aggregate(MatchScore other) {
//        assertEquals(homeClubId, other.homeClubId, "homeClubId");
//        assertEquals(awayClubId, other.awayClubId, "awayClubId");
//
//        homeGoals += other.homeGoals;
//        awayGoals += other.awayGoals;
//
//        return this;
//    }

    private void assertEquals(Object thisValue, Object otherValue, String name) {
        if (!Objects.equals(thisValue, otherValue)) {
            throw new IllegalArgumentException("Expected " + name + ": " + thisValue + ", found: " + otherValue);
        }
    }
//
//    public MatchScore goal(GoalScored goal) {
//        if (goal != null) {
//            if (homeClubId.equals(goal.getScoredFor())) {
//                homeGoals++;
//            } else if (awayClubId.equals(goal.getScoredFor())) {
//                awayGoals++;
//            } else {
//                throw new IllegalArgumentException("Goal is not assignet to match, home club: " + homeClubId
//                    + ", away club: " + awayClubId + ", goal id: " + goal.getAggId());
//            }
//        }
//        return this;
//    }
//
//    public TeamRanking homeRanking() {
//        return ranking(homeClubId, homeGoals, awayGoals);
//    }
//
//    public TeamRanking awayRanking() {
//        return ranking(awayClubId, awayGoals, homeGoals);
//    }
//
//    private TeamRanking ranking(String clubId, int goalsFor, int goalsAgainst) {
//        int result = goalsFor - goalsAgainst;
//        int won = result > 0 ? 1 : 0;
//        int drawn = result == 0 ? 1 : 0;
//        int lose = result < 0 ? 1 : 0;
//        return new TeamRanking(clubId, 1, won, drawn, lose, goalsFor, goalsAgainst);

}
