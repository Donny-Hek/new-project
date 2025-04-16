@Service
public class OcrService {

    private final Tesseract tesseract;

    public OcrService() {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // путь к tessdata
        tesseract.setLanguage("rus+eng");
    }

    public String extractText(Path imagePath) throws TesseractException {
        return tesseract.doOCR(imagePath.toFile());
    }
}
