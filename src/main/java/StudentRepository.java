import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class StudentRepository {
    private Collection<Student> students;

    public StudentRepository(Collection<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public List<String> getStudentEmailsSortedByAgeUnderTheAgeOf(int age) {
        return students.stream()
                .filter(student -> student.getAge() < age)
                .sorted(Comparator.comparingInt(Student::getAge))
                .map(Student::getEmail)
                .collect(Collectors.toList());
    }

    /**
     * @return returns the sorted list of distinct names.
     *
     * SIDE EFFECT: makes all student names uppercase
     */
    public List<String> makeStudentNamesUppercaseAndReturnThemAsSortedDistinctList() {
        return students.stream()
                .peek(student -> student.setName(student.getName().toUpperCase()))
                .map(Student::getName)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    public Set<String> getNonNullUniversities() {
        return students.stream()
                .map(Student::getUniversity)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    public Map<String, Student> getStudentsMappedByEmail() {
        return students.stream()
                .collect(Collectors.toMap(
                        Student::getEmail,
                        Function.identity()
                ));
    }

    public Map<String, List<Student>> getOverageStudentsGroupedByUniversity() {
        return students.stream()
                .filter(student -> student.isOverage() && student.getUniversity() != null)
                .collect(Collectors.groupingBy(
                        Student::getUniversity,
                        Collectors.toList()
                ));
    }

    public Optional<Student> getTheStudentWithTheNthShortestEmail(int n) {
        return students.stream()
                .sorted(Comparator.comparingInt(student -> student.getEmail().length()))
                .skip(n - 1)
                .findFirst();
    }

    public Optional<String> getTheNameOfTheSecondOldestStudent() {

        return students.stream()
                .sorted(Comparator.comparingInt(Student::getAge))
                .map(Student::getName)
                .skip(students.size() - 2)
                .findFirst();
    }

    public OptionalDouble getAverageAgeOfNStudentsInUniversity(int n, String university) {
        return students.stream()
                .filter(student -> university.equals(student.getUniversity()))
                .mapToInt(Student::getAge)
                .limit(n)
                .average();
    }

    public long countStudentsWithNamesLongerThan(int n)
    {
        return students.stream()
                .map(Student::getName)
                .mapToInt(String::length)
                .filter(number -> number > n)
                .count();

    }


}
