@Repository
public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {
    List<UploadedImage> findByUserId(Long userId);
    List<UploadedImage> findAllByUploadTimeBefore(LocalDateTime time);
}
