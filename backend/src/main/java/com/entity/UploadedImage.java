@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadedImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;
    private String storagePath;
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
