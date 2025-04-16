@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variant;
    private int questionNumber;
    private String correctAnswer;
    private int points;
}
