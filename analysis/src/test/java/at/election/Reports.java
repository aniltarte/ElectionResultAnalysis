package at.election;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class Reports {
    private List<Result> results;
    private ResultComputing computing = null;

    @Before
    public void setup() {
        results = ElectionResult.instance.getResult();
        computing = new ResultComputing(ElectionResult.instance);
    }

    @Test
    public void listTheResult() {
        results.stream().forEach(r -> print(r));
    }

    @Test
    public void listResultForPune() {
        List<Result> pune = computing.byConstituency(Constituency.PUNE, Optional.of(5));

        print(pune);
    }

    @Test
    public void listResultForMumbai() {
        List<Result> mumbaiNorth = computing.byConstituency(Constituency.MUMBAI_NORTH, Optional.empty());

        print(mumbaiNorth);
    }

    @Test
    public void listWinnersPerConstituency() {
        List<Result> winner = computing.computeBy(Result::getConstituency).winner().get();

        print(winner);
    }

    @Test
    public void numberOfCandidatesPerParty() {
        Map<String, Long> candidatesPerParty = computing.computeBy(Result::getParty).count();

        print(candidatesPerParty);
    }

    @Test
    public void listPartyWisePerformance() {
        Map<String, Long> summary = computing.computeBy(Result::getConstituency)
                .winner()
                .summarize(Result::getParty);

        print(summary);
    }

    @Test
    public void totalVotesInConstituencies() {
        Map<Constituency, Integer> totalVotes = computing.computeBy(Result::getConstituency).sumByVotes();

        print(totalVotes);
    }

    @Test
    public void partyWiseVoteShare() {
        Map<String, Integer> share = computing.computeBy(Result::getParty).voteShare();

        print(share);

    }

    private void print(List<Result> results) {
        results.stream().forEach(r -> print(r));
    }

    private void print(Result result) {
        System.out.println(result.getConstituency() + "\t||\t" + result.getParty() + "\t||\t" + result.getCandidate() + "\t||\t" + result.getVotes());
    }

    private <K, V> void print(Map<K, V> result) {
        result.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "\t||\t" + e.getValue()));
    }

}
