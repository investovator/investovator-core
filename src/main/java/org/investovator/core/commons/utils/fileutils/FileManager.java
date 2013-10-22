package org.investovator.core.commons.utils.fileutils;

public class FileManager {

    /**
     * Reads a file a returns string content
     * @param filePath Relative file path
     * @return String content of the file
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        BufferedReader reader;
        StringBuilder stringBuilder;
        String line;
        String ls;
        reader = new BufferedReader(new FileReader(filePath));
        stringBuilder = new StringBuilder();
        ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        reader.close();
        return stringBuilder.toString();
    }
