@Component
@RequiredArgsConstructor
public class CleanupScheduler {

    private final UploadedImageRepository repo;

    @Scheduled(cron = "0 0 2 * * ?") // каждый день в 2:00
    public void clean() {
        LocalDateTime before = LocalDateTime.now().minusDays(30);
        List<UploadedImage> old = repo.findAllByUploadTimeBefore(before);

        for (UploadedImage img : old) {
            try {
                Files.deleteIfExists(Path.of(img.getStoragePath()));
                repo.delete(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
