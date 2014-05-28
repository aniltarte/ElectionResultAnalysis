package at.election;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reports {
    private List<Result> results;

    @Before
    public void setup() {
        results = ElectionResult.instance.getResult();
    }

    @Test
    public void listTheResult() {
        results.stream().forEach(r -> print(r));
    }

    @Test
    public void listResultForPune() {
        Comparator<Result> comparator = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

        List<Result> pune = results.stream()
                .filter(r -> r.getConstituency() == Constituency.PUNE)
                .sorted(comparator.reversed())
                .skip(0)
                .limit(5)
                .collect(Collectors.toList());

        pune.stream().forEach(r -> print(r));
    }

    @Test
    public void listWinnersPerConstituency() {
        Comparator<Result> compare = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

        Map<Constituency, Optional<Result>> constituencyWiseWinner = results.stream()
                .collect(Collectors.groupingBy(Result::getConstituency, Collectors.maxBy(compare)));

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

    private void print(Result res) {
        System.out.println(res.getConstituency() + "\t||\t" + res.getParty() + "\t||\t" + res.getCandidate() + "\t||\t" + res.getVotes());
    }

}
