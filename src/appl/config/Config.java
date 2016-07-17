package appl.config;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by ca on 16/07/16.
 */
public class Config {

    private int repetitionTime = 1;
    private Path outputDirectory;

    public Config() {

    }

    public int getRepetitionTime() {
        return repetitionTime;
    }

    public void setRepetitionTime(int repetitionTime) {
        if (repetitionTime <= 0) {
            throw new IllegalArgumentException("Repetition time must be greater 0");
        }
        this.repetitionTime = repetitionTime;
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(Path outputDirectory) {
        if (outputDirectory == null || !Files.isDirectory(outputDirectory)) {
            throw new IllegalArgumentException("The given path is either null or does not lead to a directory.");
        }
        this.outputDirectory = outputDirectory;
    }
}
