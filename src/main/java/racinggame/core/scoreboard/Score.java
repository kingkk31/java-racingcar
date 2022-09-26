package racinggame.core.scoreboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import racinggame.core.Car;
import racinggame.exception.InvalidUnknownCarNameException;

public class Score {

    private final Map<String, Integer> scoreInfo = new LinkedHashMap<>();

    public void recordScore(List<Car> cars) {
        for (Car car : cars) {
            scoreInfo.put(car.getName(), car.getDistance());
        }
    }

    public Map<String, Integer> getAllScore() {
        return scoreInfo;
    }

    public Integer getScore(String name) {
        Integer distance = scoreInfo.get(name);
        if (distance == null) {
            throw new InvalidUnknownCarNameException();
        }
        return distance;
    }

    public List<String> getWinner() {
        List<Entry<String, Integer>> scoreDatalist = sortScoreDataReverseByDistance();
        int maxDistance = scoreDatalist.get(0)
                .getValue();

        return pickCarNamesWithDistance(scoreDatalist, maxDistance);
    }

    private List<Entry<String, Integer>> sortScoreDataReverseByDistance() {
        List<Entry<String, Integer>> scoreDatalist = new ArrayList<>(scoreInfo.entrySet());
        scoreDatalist.sort(Entry.comparingByValue(Comparator.reverseOrder()));
        return scoreDatalist;
    }

    private List<String> pickCarNamesWithDistance(List<Entry<String, Integer>> scoreDatalist, int distance) {
        return scoreDatalist.stream()
                .filter(entry -> entry.getValue() == distance)
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }

}
