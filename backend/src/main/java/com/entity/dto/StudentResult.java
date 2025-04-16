@Data
@Builder
public class StudentResult {
    private String firstName;
    private String lastName;
    private String middleName;
    private String className;
    private String variant;
    private Map<Integer, String> answers;
    private Map<Integer, Integer> scores;
}
