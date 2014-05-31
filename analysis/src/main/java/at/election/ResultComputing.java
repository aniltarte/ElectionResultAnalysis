package at.election;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultComputing {
    private ElectionResult electionResult;

    private final Comparator<Result> CompareByVotes = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

    public ResultComputing(ElectionResult electionResult) {
        this.electionResult = electionResult;
    }

    public List<Result> byConstituency(Constituency constituency, Optional<Integer> limit) {
        return electionResult.getResult().stream()
                .filter(r -> r.getConstituency() == constituency)
                .sorted(CompareByVotes.reversed())
                .limit(limit.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toList());
    }

    public <T> AnalyzedResult analyzeBy(Function<Result, T> by) {
        Map<T, List<Result>> result = electionResult.getResult().stream()
                .collect(Collectors.groupingBy(by));
        return new AnalyzedResult(result);
    }

    private Integer totalVotes() {
        return electionResult.getResult().stream().mapToInt(result -> result.getVotes()).sum();
    }

    public class AnalyzedResult<T> {
        private Map<T, List<Result>> analyzed;

        AnalyzedResult(Map<T, List<Result>> analyzed) {
            this.analyzed = analyzed;
        }

        public Map<T, List<Result>> get() {
            return analyzed;
        }

        public RankedResult winner() {
            return ranked(1);
        }

        public RankedResult ranked(int rank) {
            int previousRank = rank - 1;
            List<Result> result = analyzed.entrySet().stream()
                    .map(entry -> {
                        return entry.getValue().stream()
                                .sorted(CompareByVotes.reversed())
                                .skip(previousRank)
                                .findFirst().get();
                        })
                    .collect(Collectors.toList());
            return new RankedResult(result);
        }

        public Map<T, Long> count() {
            return analyzed.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().count()));
        }

        public Map<T, Integer> sumByVotes() {
            return analyzed.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().mapToInt(Result::getVotes).sum()));
        }

        public Map<T, Integer> voteShare() {
            Function<Integer, Integer> percentile = vote -> (vote * 100) / totalVotes();
            return sumByVotes().entrySet().stream()
                    // clean way is to create a custom object Pair and map Map.Entry to Pair
                    .map(e -> {
                        e.setValue(percentile.apply(e.getValue()));
                        return e;
                    })
                    .filter(e -> e.getValue() > 0)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }

    }

    public class RankedResult {
        private List<Result> ranked;

        RankedResult(List<Result> ranked) {
            this.ranked = ranked;
        }

        public List<Result> get() {
            return ranked;
        }

        public <T> Map<T, Long> summarize(Function<Result, T> by) {
            return ranked.stream()
                    .collect(Collectors.groupingBy(by, Collectors.counting()));
        }

    }

}
