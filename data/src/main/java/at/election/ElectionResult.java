package at.election;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElectionResult {
    public static final ElectionResult instance = new ElectionResult();
    private List<Result> result = Collections.emptyList();

    private ElectionResult() {
        InputStream stream = getClass().getResourceAsStream("/Maharashtra.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        result = reader.lines()
                .skip(1)
                .map(this::toResult)
                .collect(Collectors.toList());

        Collections.shuffle(result);
    }

    public List<Result> getResult() {
        return Collections.unmodifiableList(result);
    }

    private Result toResult(String line) {
        String[] parts = line.split(",");
        return new Result(toState(parts[0]), toConstituency(parts[1]), parts[2], parts[3], toInt(parts[4]));
    }

    private State toState(String value) {
        return State.valueOf(value);
    }

    private Integer toInt(String value) {
        return Integer.valueOf(value);
    }

    private Constituency toConstituency(String value) {
        return Constituency.valueOf(value.replaceAll(" ", "_").toUpperCase());
    }

}
