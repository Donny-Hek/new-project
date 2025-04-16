@Service
public class ReferenceAnswerImporter {

    public List<ReferenceAnswer> importFromExcel(Path path) throws IOException {
        List<ReferenceAnswer> result = new ArrayList<>();
        Workbook wb = WorkbookFactory.create(path.toFile());
        Sheet sheet = wb.getSheetAt(0);
        Row lastRow = sheet.getRow(sheet.getLastRowNum());

        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String variant = row.getCell(0).getStringCellValue();

            for (int j = 1; j < row.getLastCellNum(); j++) {
                String ans = row.getCell(j).getStringCellValue();
                int points = (int) lastRow.getCell(j).getNumericCellValue();

                result.add(ReferenceAnswer.builder()
                        .variant(variant)
                        .questionNumber(j)
                        .correctAnswer(ans.trim().toLowerCase())
                        .points(points)
                        .build());
            }
        }

        return result;
    }
}
