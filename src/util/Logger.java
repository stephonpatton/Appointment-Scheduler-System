package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    /**
     * Writes to log file when a user signs in. Includes name and timestamp
     * @param username User that has logged in
     * @throws IOException If write to file fails
     */
    public static void logUser(String username) throws IOException {
        String filename = "login_activity.txt";
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        outputFile.println(username + " logged in on " + LocalDateTime.now().toLocalDate() + " " + LocalDateTime.now().toLocalTime());
        outputFile.close();
    }
}
