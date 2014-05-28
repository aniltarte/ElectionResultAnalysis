package at.election;

import java.util.Comparator;

public class Result {

    private final State state;
    private final Constituency constituency;
    private final String party;
    private final String candidate;
    private final Integer votes;

    public Result(State state, Constituency constituency, String candidate, String party, Integer votes) {
        this.state = state;
        this.constituency = constituency;
        this.candidate = candidate;
        this.party = party;
        this.votes = votes;
    }

    public State getState() {
        return state;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public String getParty() {
        return party;
    }

    public String getCandidate() {
        return candidate;
    }

    public Integer getVotes() {
        return votes;
    }

}
