@Repository
public interface ReferenceAnswerRepository extends JpaRepository<ReferenceAnswer, Long> {
    List<ReferenceAnswer> findByVariant(String variant);
}
