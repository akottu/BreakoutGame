package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;

/**
 * Generates blocks based on file associated with level.
 */
public class BlockGenerator {

  private static int totalRowNumber = 0;
  private static final int TOTAL_COLUMNS = 16;

  public static List<Block> generateBlocks(Group root, int level) {

    List<List<Integer>> levelConfiguration = null;
    try {
      levelConfiguration = BlockConfigurationReader.readLevelConfiguration(level);
    } catch (FileNotFoundException e) {
      return null;
    }
    List<Block> blocks = new ArrayList<Block>();
    Block block;

    int rowNumber = 0;
    int columnNumber = 0;
    rowNumber = createBlocks(root, levelConfiguration, blocks, rowNumber, columnNumber);
    totalRowNumber = rowNumber;
    return blocks;
  }

  private static int createBlocks(Group root, List<List<Integer>> levelConfiguration,
      List<Block> blocks, int rowNumber, int columnNumber) {
    Block block;
    for (List<Integer> row : levelConfiguration) {

      for (Integer blockNumber : row) {
        createBlock(root, blocks, rowNumber, columnNumber, blockNumber);
        columnNumber++;
      }
      rowNumber++;
    }
    return rowNumber;
  }

  private static void createBlock(Group root, List<Block> blocks, int rowNumber, int columnNumber,
      Integer blockNumber) {
    Block block;
    int column = columnNumber % TOTAL_COLUMNS;
    if (blockNumber != 0) {
      block = new Block(column, rowNumber, blockNumber);
      blocks.add(block);
      root.getChildren().add(block);
    }
  }

  public static int getTotalRowNumber() {
    return totalRowNumber;
  }

  public static int getTotalColumns() {
    return TOTAL_COLUMNS;
  }

}


