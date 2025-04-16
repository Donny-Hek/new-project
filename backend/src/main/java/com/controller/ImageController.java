@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final UploadedImageRepository imageRepo;
    private final UserRepository userRepo;
    private final OcrService ocrService;
    private final ReferenceAnswerImporter refImporter;
    private final ReferenceAnswerRepository refRepo;
    private final EvaluationService evalService;
    private final ExcelReportService excelService;

    private final Path root = Paths.get("uploads");

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        User user = userRepo.findByLogin(principal.getName()).orElseThrow();
        Path dir = root.resolve(LocalDate.now().toString());
        Files.createDirectories(dir);

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path fullPath = dir.resolve(filename);
        Files.copy(file.getInputStream(), fullPath);

        imageRepo.save(UploadedImage.builder()
                .originalFilename(file.getOriginalFilename())
                .storagePath(fullPath.toString())
                .uploadTime(LocalDateTime.now())
                .user(user)
                .build());

        return ResponseEntity.ok("Файл загружен");
    }

    @PostMapping("/import-reference")
    public ResponseEntity<?> importReference(@RequestParam("file") MultipartFile file) throws Exception {
        Path temp = Files.createTempFile("ref-", ".xlsx");
        file.transferTo(temp.toFile());

        List<ReferenceAnswer> refs = refImporter.importFromExcel(temp);
        refRepo.saveAll(refs);

        return ResponseEntity.ok("Импортировано: " + refs.size());
    }

    @GetMapping("/export")
    public ResponseEntity<?> export() throws IOException {
        // Должно приходить извне, здесь — заглушка
        List<StudentResult> results = List.of(); // ← здесь нужно подтянуть реальные данные

        ByteArrayOutputStream stream = excelService.generateReport(results);
        ByteArrayResource resource = new ByteArrayResource(stream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
