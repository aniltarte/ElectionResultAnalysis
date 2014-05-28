package at.election;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvDataCombiner {

    public void load() throws Exception {
        Path path = getResourcesPath();

        Files.list(path).forEach(file -> printConstituencyEnumValues(file.toFile()));

        System.out.println("=============================================");
        System.out.println("State,Constituency,Candidate,Party,Votes");

        Files.list(path).forEach(file -> printFileContent(file.toFile()));
    }

    private void printConstituencyEnumValues(File file) {
        String constituency = extractConstituency(file);
        System.out.println(constituency.replaceAll(" ", "_").toUpperCase() + "(\"" + constituency + "\"),");
    }

    private void printFileContent(File file) {
        try {
            String constituency = extractConstituency(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines()
                    .skip(1)
                    .forEach(line -> System.out.println("Maharashtra," + constituency + "," + line));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractConstituency(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf("-") + 2, fileName.length() - 4);
    }

    private Path getResourcesPath() {
        String logicalPath = FileSystems.getDefault().getPath("resources").toAbsolutePath().toString();
        String rootPath = logicalPath.substring(0, logicalPath.lastIndexOf(File.separator));
        return Paths.get(rootPath, "data", "src", "test", "resources");
    }

    public static void main(String[] args) throws Exception {
        new CsvDataCombiner().load();
    }
}
