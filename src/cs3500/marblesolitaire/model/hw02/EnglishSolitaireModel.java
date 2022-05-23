package cs3500.marblesolitaire.model.hw02;


/**
 * Represents the English Solitaire version of the Marble Solitaire game.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {

  private SlotState[][] board; // the game board model

  private final int boardSize; //The longest edge of the board
  private int marbles; // the number of marbles present on the board

  /**
   * Creates a {@code EnglishSolitaireModel} based on default specifications
   * (arm thickness 3, center slot empty).
   */
  public EnglishSolitaireModel() {

    this.fillBoard(3);
    this.board[3][3] = SlotState.Empty;
    this.marbles -= 1;
    this.boardSize = 7; //Hardcoded..
  }

  /**
   * Creates a {@code EnglishSolitaireModel} with arm thickness 3 and empty slot at given position
   * Throws error if cell position is invalid.
   *
   * @param sRow The given empty slot row number
   * @param sCol The given empty slow column number
   * @throws IllegalArgumentException if position is invalid
   */
  public EnglishSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    if (sRow < 0 || sRow > 6 || sCol < 0 || sCol > 6) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    }
    this.fillBoard(3);
    if (this.board[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    } else {
      this.board[sRow][sCol] = SlotState.Empty;
      this.marbles -= 1;
    }
    this.boardSize = 7; //Hardcoded...
  }

  /**
   * Creates a {@code EnglishSolitaireModel} using given arm thickness
   * with empty slot in the middle.
   *
   * @param armThickness the given Arm Thickness
   * @throws IllegalArgumentException if Arm Thickness is not a positive odd number
   */
  public EnglishSolitaireModel(int armThickness) throws IllegalArgumentException {
    if (armThickness < 3 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm Thickness must be a positive odd number > 3");
    }

    this.fillBoard(armThickness);
    //VERY IMPORTANT: int's automatic removal of 0.5 helps us here, we want the lower bound
    int midpoint = ((3 * armThickness) / 2) - 1;
    this.board[midpoint][midpoint] = SlotState.Empty;
    this.marbles -= 1;
    this.boardSize = (3 * armThickness) - 2; // Found doing math
  }

  /**
   * Creates a {@code EnglishSolitaireModel} of a given arm thickness
   * and puts empty spot at given coordinates, assuming they are correct.
   *
   * @param armThick the given arm thickness
   * @param sRow     given row value of the empty spot
   * @param sCol     given column value of the empty spot
   * @throws IllegalArgumentException if arm thickness or empty spot coordinate is invalid
   */
  public EnglishSolitaireModel(int armThick, int sRow, int sCol) throws
          IllegalArgumentException {
    if (armThick < 3 || armThick % 2 == 0) {
      throw new IllegalArgumentException("Arm Thickness must be a positive odd number > 3");
    }

    this.fillBoard(armThick);
    this.boardSize = (3 * armThick) - 2;

    if (sRow < 0 || sRow >= boardSize || sCol < 0 || sCol >= boardSize) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    } else if (this.board[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    }

    this.board[sRow][sCol] = SlotState.Empty;
    this.marbles -= 1;
  }

  /**
   * Moves a marble from the given from slot to the given to slot, if it is valid.
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the given two coordinates: <br>
   *        - From does not contain a marble, or To is not an empty <br>
   *        - Given coordinates do not exist on the board <br>
   *        - The distance is greater than 2 slots away <br>
   *        - The two coordinates do not share a column or row (diagonal move) <br>
   *        - The middle slot does not contain a marble
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws
          IllegalArgumentException {

    if (fromRow >= boardSize || fromRow < 0 || fromCol >= boardSize || fromCol < 0) {
      throw new IllegalArgumentException("From point must be on grid");
    } else if (toRow >= boardSize || toRow < 0 || toCol >= boardSize || toCol < 0) {
      throw new IllegalArgumentException("To point must be on grid");
    }

    SlotState from = this.getSlotAt(fromRow, fromCol);
    SlotState to = this.getSlotAt(toRow, toCol);

    if (from != SlotState.Marble || to != SlotState.Empty) {
      throw new IllegalArgumentException("Cannot move from " + from + " -> " + to);
    }

    int rowDelta = toRow - fromRow;
    int colDelta = toCol - fromCol;

    if ((Math.abs(rowDelta) == 2 && colDelta == 0) ||
            (Math.abs(colDelta) == 2 && rowDelta == 0)) {
      //Next step located here:
      // check middle piece, complete motion
      int midRow = (rowDelta / 2) + fromRow;
      int midCol = (colDelta / 2) + fromCol;
      SlotState mid = this.getSlotAt(midRow, midCol);

      if (mid != SlotState.Marble) {
        throw new IllegalArgumentException("Middle spot must contain a marble");
      }
      //At this point, all cases have been cleared:
      // from and to are valid positions that are two spaces apart
      // from has a marble and to doesn't
      // mid is a marble space
      // Commit change
      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
      this.board[midRow][midCol] = SlotState.Empty;
      this.marbles--;
    } else if (rowDelta != 0 && colDelta != 0) {
      throw new IllegalArgumentException("Move cannot be diagonal");
    } else {
      throw new IllegalArgumentException("Move must only cross 2 slots");
    }
  }

  @Override
  public boolean isGameOver() {
    //Check every marble:
    //if the marble contains a marble on one end and empty on other, game is not over
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (this.getSlotAt(i, j) == SlotState.Marble) {

          // Set up surrounding slot variables
          SlotState up = SlotState.Invalid;
          SlotState down = SlotState.Invalid;
          SlotState left = SlotState.Invalid;
          SlotState right = SlotState.Invalid;

          //Ensure that we don't run into a NullPointer Trap!
          if (i - 1 >= 0) {
            up = this.getSlotAt(i - 1, j);
          }
          if (i + 1 < this.boardSize) {
            down = this.getSlotAt(i + 1, j);
          }
          if (j - 1 >= 0) {
            left = this.getSlotAt(i, j - 1);
          }
          if (j + 1 < this.boardSize) {
            right = this.getSlotAt(i, j + 1);
          }

          // All of our move cases
          if ((up == SlotState.Marble && down == SlotState.Empty) ||
                  (up == SlotState.Empty && down == SlotState.Marble) ||
                  (left == SlotState.Marble && right == SlotState.Empty) ||
                  (left == SlotState.Empty && right == SlotState.Marble)) {
            // There is at least one legal move, game is not over
            return false;
          }
        }
      }
    }

    // At this point, there are no marbles on the board that can move
    return true;
  }

  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    return this.board[row][col];
  }

  @Override
  public int getScore() {
    return this.marbles;
  }

  /**
   * Fills the board's 2D-Array with values in a cross shape, additionally setting the score.
   *
   * @param armThickness the given arm thickness
   */
  private void fillBoard(int armThickness) {
    int boardLen = (3 * armThickness) - 2;
    int armStart = armThickness - 1;
    int armEnd = armThickness * 2 - 1;
    this.board = new SlotState[boardLen][boardLen];

    for (int i = 0; i < boardLen; i++) {
      for (int j = 0; j < boardLen; j++) {
        this.board[i][j] = SlotState.Invalid;
        if ((i >= armStart && i < armEnd) || (j >= armStart && j < armEnd)) {
          this.board[i][j] = SlotState.Marble;
          this.marbles++;
        }
      }
    }
  }
}
