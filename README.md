# TextFileAnalyzer

## Project Description
TextFileAnalyzer is a Java program that reads and analyzes text from a plain text file and a Microsoft Word document. It calculates the file size, number of lines, number of words, and the number of times a character entered by the user appears in each file. The program then compares the results between the two files.

The program uses Apache POI to read Word documents and exception handling to report file errors.

## Files Being Analyzed
- `sample.txt` — contains the selected text in plain text format.
- `sample.docx` — contains the same or very similar text in Microsoft Word format.

Both files are stored in the `test-files` folder.

## Technologies Used
- Java
- Apache Maven
- Apache POI
- IntelliJ IDEA

## How to Run the Program
1. Clone or download this repository from GitHub.
2. Open the project in IntelliJ IDEA.
3. Allow Maven to download the dependencies listed in `pom.xml`.
4. Make sure `sample.txt` and `sample.docx` are located in the `test-files` folder.
5. Open `Main.java`.
6. Click the green Run button next to the `main` method.
7. Enter a single character when prompted.
8. Review the analysis results and comparison displayed in the console.


## Expected Results
The program displays the following information for each file:
- File name and type
- File size in bytes
- Number of lines
- Number of words
- Selected character
- Number of occurrences of the selected character

The program then compares the line, word, and character counts to determine whether they match. The file sizes may differ because Word documents contain additional formatting and document structure.

## Screenshot

<img width="699" height="608" alt="image" src="https://github.com/user-attachments/assets/c8c2f968-c8ba-4586-8b9c-ed7a247e9144" />
