package at.election;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ElectionResultStatistics {
    private ResultAnalyzer computing = null;

    @Before
    public void setup() {
        computing = new ResultAnalyzer(ElectionResult.instance);
    }

    @Test
    public void listTheResult() {
        ElectionResult.instance.getResult().stream().forEach(this::print);
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
        List<Result> winner = computing.analyzeBy(Result::getConstituency).winner().get();

        print(winner);
    }

    @Test
    public void numberOfCandidatesPerParty() {
        Map<String, Long> candidatesPerParty = computing.analyzeBy(Result::getParty).count();

        print(candidatesPerParty);
    }

    @Test
    public void listPartyWisePerformance() {
        Map<String, Long> summary = computing.analyzeBy(Result::getConstituency)
                .winner()
                .summarize(Result::getParty);

        print(summary);
    }

    @Test
    public void totalVotesInConstituencies() {
        Map<Constituency, Integer> totalVotes = computing.analyzeBy(Result::getConstituency).sumByVotes();

        print(totalVotes);
    }

    @Test
    public void partyWiseVoteShare() {
        Map<String, Integer> share = computing.analyzeBy(Result::getParty).voteShare();

        print(share);
    }

    @Test
    public void partyWiseRunnerUpSeats() {
        Map<String, Long> runnerUps = computing.analyzeBy(Result::getConstituency)
                .ranked(2)
                .summarize(Result::getParty);

        print(runnerUps);
    }

    private void print(List<Result> results) {
        results.stream().forEach(this::print);
    }

    private void print(Result result) {
        System.out.println(result.getConstituency() + "\t||\t" + result.getParty() + "\t||\t" + result.getCandidate() + "\t||\t" + result.getVotes());
    }

    private <K, V> void print(Map<K, V> result) {
        result.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "\t||\t" + e.getValue()));
    }

}
