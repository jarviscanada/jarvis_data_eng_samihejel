package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: Javagrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }

    }

    @Override
    public void process() throws IOException{
        ArrayList<String> matchedLines = new ArrayList<String>();
        for (File file : listFiles(rootPath)) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }

        }
        writeToFile(matchedLines);
    }

    public List<File> listFiles(String rootDir){
        File directoryPath = new File(rootDir);
        ArrayList<File> fileList = new ArrayList<File>();
        if(directoryPath.listFiles() != null) {
            for (File f : directoryPath.listFiles()) {
                if (f.isDirectory()) {
                    fileList.addAll(listFiles(f.getAbsolutePath()));
                } else {
                    fileList.add(f);
                }
            }
        }
        return fileList;
    }

    public List<String> readLines(File inputFile){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            // Handle the IOException here
            logger.error("Error reading lines from file: " + inputFile.getAbsolutePath(), e);
        }
        return lines;
    }

    public boolean containsPattern(String line){
        return line.matches(regex);
    }

    public void writeToFile(List<String> matchedLines){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            Stream<String> stream = Stream.of(matchedLines);

            stream.forEach(line -> {
                writer.write(line);
                writer.newLine();
            });

        } catch (IOException e) {
            // Handle the IOException here
            logger.error("Error writing lines to file: " + outFile, e);
        }
    }


    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

}