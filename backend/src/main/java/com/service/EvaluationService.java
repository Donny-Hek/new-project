@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final ReferenceAnswerRepository referenceRepo;

    public Map<Integer, Integer> evaluate(String variant, Map<Integer, String> answers) {
        List<ReferenceAnswer> refs = referenceRepo.findByVariant(variant);
        Map<Integer, Integer> result = new HashMap<>();

        for (ReferenceAnswer ref : refs) {
            String userAns = answers.getOrDefault(ref.getQuestionNumber(), "").trim().toLowerCase();
            result.put(ref.getQuestionNumber(),
                    userAns.equals(ref.getCorrectAnswer()) ? ref.getPoints() : 0);
        }

        return result;
    }
}
