package at.election;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultComputing {
    private ElectionResult electionResult;

    public static final Comparator<Result> CompareByVotes = (Result r1, Result r2) -> r1.getVotes().compareTo(r2.getVotes());

    public ResultComputing(ElectionResult electionResult) {
        this.electionResult = electionResult;
    }

    public List<Result> byConstituency(Constituency constituency, Optional<Integer> limit) {
        return electionResult.getResult().stream()
                .filter(r -> r.getConstituency() == constituency)
                .sorted(ResultComputing.CompareByVotes.reversed())
                .limit(limit.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toList());
    }

    public <T> GroupBy groupBy(Function<Result, T> by) {
        Map<T, List<Result>> result = electionResult.getResult().stream()
                .collect(Collectors.groupingBy(by));
        return new GroupBy(result);
    }

    private Integer totalVotes() {
        return  electionResult.getResult().stream().mapToInt(result -> result.getVotes()).sum();
    }

    public class GroupBy<T> {
        private Map<T, List<Result>> groupBy;

        GroupBy(Map<T, List<Result>> groupBy) {
            this.groupBy = groupBy;
        }

        public Map<T, List<Result>> get() {
            return groupBy;
        }

        public Winner winner() {
            List<Result> winner = groupBy.entrySet().stream()
                    .map(entry -> entry.getValue().stream().max(ResultComputing.CompareByVotes).get())
                    .collect(Collectors.toList());
            return new Winner(winner);
        }

        public Map<T, Long> count() {
            return groupBy.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().count()));
        }

        public Map<T, Integer> sumByVotes() {
            return groupBy.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().mapToInt(Result::getVotes).sum()));
        }

        public Map<T, Integer> voteShare() {
            Function<Integer, Integer> percentile = vote ->  (vote * 100) / totalVotes();
            return sumByVotes().entrySet().stream()
                    .map(e -> {
                            e.setValue(percentile.apply(e.getValue()));
                            return e;
                        })
                    .filter(e -> e.getValue() > 0)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }
    }

    public class Winner {
        private List<Result> winner;

        Winner(List<Result> winner) {
            this.winner = winner;
        }

        public List<Result> get() {
            return winner;
        }

        public <T> Map<T, Long> summarize(Function<Result, T> by) {
            return winner.stream()
                    .collect(Collectors.groupingBy(by, Collectors.counting()));
        }
    }

}
