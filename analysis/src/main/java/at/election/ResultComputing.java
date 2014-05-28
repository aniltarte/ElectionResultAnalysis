package at.election;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ResultComputing {
    private ElectionResult electionResult;

    public static final Comparator<Result> compareByVotes = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

    public ResultComputing(ElectionResult electionResult) {
        this.electionResult = electionResult;
    }

    public List<Result> byConstituency(Constituency constituency, Optional<Integer> limit) {
        return electionResult.getResult().stream()
                .filter(r -> r.getConstituency() == constituency)
                .sorted(ResultComputing.compareByVotes.reversed())
                .limit(limit.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toList());
    }

}
