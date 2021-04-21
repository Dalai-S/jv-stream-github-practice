package practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.People;

public class StreamPractice {

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .map(w -> w.split(","))
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt)
                .filter(value -> value % 2 == 0)
                .min()
                .orElseThrow(
                        () -> new RuntimeException("Can't get min value from list:"
                                + numbers.toString()));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .map(index -> {
                    if (index % 2 != 0) {
                        return numbers.get(index) - 1;
                    }
                    return numbers.get(index);
                })
                .filter(value -> value % 2 != 0)
                .average()
                .orElseThrow(
                        () -> new NoSuchElementException("No such element")
                );
    }

    public List<People> selectMenByAge(List<People> peopleList, int fromAge, int toAge) {
        Predicate<People> checkForManAge = human -> human.getAge() >= fromAge
                && human.getAge() <= toAge && human.getSex() == People.Sex.MAN;
        return peopleList.stream()
                .filter(checkForManAge)
                .collect(Collectors.toList());
    }

    public List<People> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<People> peopleList) {
        Predicate<People> checkForWorkablePeople = human -> human.getAge() >= fromAge
                && human.getAge() <= (human.getSex() == People.Sex.MAN
                ? maleToAge : femaleToAge);
        return peopleList.stream()
                .filter(checkForWorkablePeople)
                .collect(Collectors.toList());
    }

    public List<String> getCatsNames(List<People> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(human -> human.getSex() == People.Sex.WOMEN
                        && human.getAge() >= femaleAge)
                .map(People::getCats)
                .flatMap(Collection::stream)
                .map(Cat::getName)
                .collect(Collectors.toList());
    }

    public static List<String> validateCandidates(List<Candidate> candidates) {
        return candidates.stream()
                .filter(new CandidateValidator())
                .map(Candidate::getName)
                .sorted()
                .collect(Collectors.toList());
    }

}
