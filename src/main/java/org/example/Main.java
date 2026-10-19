
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Path TXT_PATH =
            Path.of("test-files", "sample.txt");

    private static final Path DOCX_PATH =
            Path.of("test-files", "sample.docx");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String txtText = Files.readString(
                    TXT_PATH, StandardCharsets.UTF_8);

            String docxText = readDocx(DOCX_PATH);

            System.out.print("Enter a character to search for: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("Please enter a character.");
                return;
            }

            if (input.length() != 1) {
                System.out.println(
                        "Please enter exactly one character.");
                return;
            }

            char selectedChar = input.charAt(0);

            FileResults txtResults = analyzeFile(
                    TXT_PATH, "TXT", txtText, selectedChar);

            FileResults docxResults = analyzeFile(
                    DOCX_PATH, "DOCX", docxText, selectedChar);

            displayResults(txtResults, selectedChar);
            displayResults(docxResults, selectedChar);
            compareResults(txtResults, docxResults);

        } catch (IOException e) {
            System.err.println(
                    "File error: " + e.getMessage());
            System.err.println(
                    "Check that both test files exist and can be read.");
        } catch (Exception e) {
            System.err.println(
                    "An unexpected error occurred: " + e.getMessage());
        }
    }

    private static String readDocx(Path path) throws IOException {
        try (InputStream input = Files.newInputStream(path);
             XWPFDocument document = new XWPFDocument(input)) {

            StringBuilder text = new StringBuilder();
            List<XWPFParagraph> paragraphs =
                    document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                if (text.length() > 0) {
                    text.append("\n");
                }
                text.append(paragraph.getText());
            }

            return text.toString();
        }
    }

    private static FileResults analyzeFile(
            Path path, String type, String text, char selectedChar)
            throws IOException {

        long fileSize = Files.size(path);

        int lineCount = text.isEmpty()
                ? 0
                : text.split("\\R", -1).length;

        Matcher matcher = Pattern
                .compile("\\b[\\p{L}\\p{N}_]+\\b")
                .matcher(text);

        int wordCount = 0;
        while (matcher.find()) {
            wordCount++;
        }

        int characterCount = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == selectedChar) {
                characterCount++;
            }
        }

        return new FileResults(
                path.getFileName().toString(),
                type,
                fileSize,
                lineCount,
                wordCount,
                characterCount
        );
    }

    private static void displayResults(
            FileResults results, char selectedChar) {

        System.out.println("\n========== " + results.fileName
                + " ==========");
        System.out.println("File type: " + results.fileType);
        System.out.println("File size: " + results.fileSize
                + " bytes");
        System.out.println("Number of lines: " + results.lineCount);
        System.out.println("Number of words: " + results.wordCount);
        System.out.println("Character searched: " + selectedChar);
        System.out.println("Character occurrences: "
                + results.characterCount);
    }

    private static void compareResults(
            FileResults txt, FileResults docx) {

        System.out.println("\n========== COMPARISON ==========");

        compareValue("Line counts", txt.lineCount, docx.lineCount);
        compareValue("Word counts", txt.wordCount, docx.wordCount);
        compareValue("Character counts",
                txt.characterCount, docx.characterCount);

        if (txt.fileSize == docx.fileSize) {
            System.out.println("File sizes are the same.");
        } else {
            System.out.println("File sizes are different.");
            System.out.println(
                    "DOCX files contain additional document structure "
                            + "and formatting.");
        }
    }

    private static void compareValue(
            String label, int txtValue, int docxValue) {

        String result = txtValue == docxValue
                ? "the same"
                : "different";

        System.out.println(label + ": " + result
                + " (TXT: " + txtValue
                + ", DOCX: " + docxValue + ")");
    }

    private static class FileResults {
        String fileName;
        String fileType;
        long fileSize;
        int lineCount;
        int wordCount;
        int characterCount;

        FileResults(String fileName, String fileType,
                    long fileSize, int lineCount, int wordCount,
                    int characterCount) {
            this.fileName = fileName;
            this.fileType = fileType;
            this.fileSize = fileSize;
            this.lineCount = lineCount;
            this.wordCount = wordCount;
            this.characterCount = characterCount;
        }
    }
}

