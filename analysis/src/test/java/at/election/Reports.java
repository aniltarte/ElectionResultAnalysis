package at.election;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

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
        pune.stream().forEach(r -> print(r));
    }

    @Test
    public void listResultForMumbai() {
        List<Result> mumbaiNorth = computing.byConstituency(Constituency.MUMBAI_NORTH, Optional.empty());

        mumbaiNorth.stream().forEach(r -> print(r));
    }

    @Test
    public void listWinnersPerConstituency() {
        Map<Constituency, Optional<Result>> constituencyWiseWinner = results.stream()
                .collect(Collectors.groupingBy(Result::getConstituency, Collectors.maxBy(ResultComputing.compareByVotes)));

        constituencyWiseWinner.entrySet().stream().forEach(winner -> print(winner.getValue().get()));

    }

    @Test
    public void numberOfCandidatesPerParty() {
        Map<String, Long> candidatesPerParty = results.stream()
                .collect(Collectors.groupingBy(Result::getParty, Collectors.counting()));

        candidatesPerParty.entrySet().stream().forEach(entry ->
                System.out.println(entry.getKey() + "\t||\t" + entry.getValue()));
    }

    @Test
    public void listPartyWisePerformance() {
        Comparator<Result> compare = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

        Map<Constituency, Optional<Result>> constituencyWiseWinner = results.stream()
                .collect(Collectors.groupingBy(Result::getConstituency, Collectors.maxBy(compare)));

        Map<String, Long> partyWisePerformance = constituencyWiseWinner.values().stream()
                .map(o -> o.get())
                .collect(Collectors.groupingBy(Result::getParty, Collectors.counting()));

        partyWisePerformance.entrySet().stream().forEach(entry ->
                System.out.println(entry.getKey() + "\t||\t" + entry.getValue()));

    }

    @Test
    public void totalVotesInConstituencies() {
        Map<Constituency, Long> totalVotes = results.stream()
                .collect(Collectors.groupingBy(Result::getConstituency, Collectors.summingLong(Result::getVotes)));

        totalVotes.entrySet().stream()
                .sorted((Map.Entry<Constituency, Long> e1, Map.Entry<Constituency, Long> e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + "\t||\t" + entry.getValue()));
    }

    @Test
    public void partyWiseVoteShare() {
        int totalVotes = results.stream().mapToInt(r -> r.getVotes()).sum();

        BiFunction<String, Integer, Integer> percentile = (String k, Integer v) ->  (v * 100) / totalVotes;

        Map<String, Integer> partyWiseVotes = results.stream()
                .collect(Collectors.groupingBy(Result::getParty, Collectors.summingInt(Result::getVotes)));

        partyWiseVotes.replaceAll(percentile);

        partyWiseVotes.forEach((k, v) -> System.out.println(k + "\t||\t" + v));

    }

    private void print(Result res) {
        System.out.println(res.getConstituency() + "\t||\t" + res.getParty() + "\t||\t" + res.getCandidate() + "\t||\t" + res.getVotes());
    }

}
