package kozmikoda.utilitytoolbox;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;


public class SoundAnalyzerStart extends Thread {
    Path source;
    Path newDir;
    TextField output;
    JFXButton selectButton, soundAnalyzerBackButton;
    ProgressIndicator analyzeBar;
    Label analyzeLabel;
    FileWriter myWriter;

    public SoundAnalyzerStart(File file, TextField output, JFXButton selectButton, ProgressIndicator analyzeBar, Label analyzeLabel, JFXButton soundAnalyzerBackButton) throws IOException {
        source = Paths.get(file.toString());
        newDir = Paths.get(System.getProperty("user.dir"));
        this.output = output;
        this.selectButton = selectButton;
        this.analyzeBar = analyzeBar;
        this.analyzeLabel = analyzeLabel;
        this.soundAnalyzerBackButton = soundAnalyzerBackButton;

        myWriter = new FileWriter("debug.txt");
        myWriter.write(newDir.toString());

    }

    PrintStream ps = null;

    /**
     * Runs the sound processing on the selected file
     */
    public void run() {
        soundAnalyzerBackButton.setDisable(true);
        File exePath = new File(newDir.resolve("resources").toString());
        Process process;
        String data = "Weird unknown error occurred. Please try again.";
        try {
            // Getting the exe path from resources file

            System.out.println("EXE PATH " + exePath);

            // Creating the process for execute the soundAnalyzer.exe
            process = Runtime.getRuntime().exec(new String[]{"python", "soundAnalyzer.py"}, null, exePath);
            System.out.println("PROCESS INFO " + process.info());
            myWriter.write("PROCESS INFO " + process.info());
            myWriter.close();

            // Waiting for the process to be finished
            process.waitFor();

        }catch(Exception e) {

            try {
                ps = new PrintStream(new File("logs.txt"));
            } catch (Exception er) {
                er.printStackTrace();
            }
            
            output.setText(data);
            e.printStackTrace();
            e.printStackTrace(ps);

        }

        // Reading the output from out.txt that is created by executable
        try {
            Path txtPath = newDir.resolve("resources/out.txt");;
            File myObj = new File(txtPath.toString());
            Scanner myReader = new Scanner(myObj);

            data = myReader.nextLine();
            myReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(ps);
        }

        // Change elements visibilities and texts after the operation
        analyzeBar.setVisible(false);
        analyzeLabel.setVisible(false);
        output.setVisible(true);
        output.setText(data);
        selectButton.setDisable(false);
        soundAnalyzerBackButton.setDisable(false);

    }

    /**
     * Copies file into needed locations and prepares it for processing
     */
    public void prepareFile() {
        // PREPROCESS
        // copying selected .wav file
        Path soundPath = newDir.resolve("resources/" + Paths.get(source.getFileName().toString()));

        try {
            Files.copy(source, soundPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // rename the copied file to test.wav
        try {
            // rename a file in the same directory
            Files.move(soundPath, soundPath.resolveSibling("test.wav"), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
