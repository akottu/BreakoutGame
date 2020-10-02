package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to read the configuration of blocks in a particular level
 */
public class BlockConfigurationReader {

  private static final String NEW_LINE = "\n";
  private static final String DATA_FILE_PATH = "data/level";
  private static final String FILE_EXTENSION = ".txt";

  /**
   * Reads block configuration from a level file
   *
   * @param level
   * @return
   * @throws FileNotFoundException
   */
  public static List<List<Integer>> readLevelConfiguration(int level) throws FileNotFoundException {
    List<List<Integer>> levelConfiguration = new ArrayList<List<Integer>>();
    String file = DATA_FILE_PATH + level + FILE_EXTENSION;
    Scanner scanner = new Scanner(new File(file));
    scanner.useDelimiter(NEW_LINE);
    int counter = 0;
    processFileContents(levelConfiguration, scanner, counter);
    return levelConfiguration;
  }

  private static void processFileContents(List<List<Integer>> levelConfiguration, Scanner scanner,
      int counter) {
    while (scanner.hasNext()) {
      counter++;
      levelConfiguration.add(parseBlockLine(scanner.next(), counter));
    }
    scanner.close();
  }

  private static List<Integer> parseBlockLine(String next, int counter) {
    String[] lineEntries = next.split(" ");
    List<Integer> blockEntries = new ArrayList<Integer>();

    for (String blockEntry : lineEntries) {
      blockEntries.add(Integer.parseInt(blockEntry));
    }
    return blockEntries;
  }
}
