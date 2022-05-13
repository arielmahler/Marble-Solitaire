package cs3500.marblesolitaire.model.hw02;


/**
 * Represents the English Solitaire version of the Marble Solitaire game.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {

  private SlotState[][] board; // the game board model
  private final int boardSize;

  /**
   * Creates a {@code EnglishSolitaireModel} based on default specifications
   * (arm thickness 3, center slot empty).
   */
  public EnglishSolitaireModel() {

    this.fillBoard(3);
    this.board[3][3] = SlotState.Empty;
    this.boardSize = 7; //Hardcoded...
  }

  /**
   * Creates a {@code EnglishSolitaireModel} with arm thickness 3 and empty slot at given position
   * Throws error if cell position is invalid.
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
    }
    this.boardSize = 7; //Hardcoded...
  }

  /**
   * Creates a {@code EnglishSolitaireModel} using given arm thickness
   * with empty slot in the middle.
   * @param armThickness the given Arm Thickness
   * @throws IllegalArgumentException if Arm Thickness is not a positive odd number
   */
  public EnglishSolitaireModel(int armThickness) throws IllegalArgumentException {
    if (armThickness < 3 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm Thickness must be a positive odd number");
    }

    this.fillBoard(armThickness);
    this.board[armThickness][armThickness] = SlotState.Empty;
    this.boardSize = armThickness * 2 + 1;
  }

  /**
   * Creates a {@code EnglishSolitaireModel} of a given arm thickness
   * and puts empty spot at given coordinates, assuming they are correct.
   * @param armThick the given arm thickness
   * @param sRow given row value of the empty spot
   * @param sCol given column value of the empty spot
   * @throws IllegalArgumentException if arm thickness or empty spot coordinate is invalid
   */
  public EnglishSolitaireModel(int armThick, int sRow, int sCol) throws
          IllegalArgumentException {
    if (armThick < 3 || armThick % 2 == 0) {
      throw new IllegalArgumentException("Arm Thickness must be a positive odd number");
    }

    this.fillBoard(armThick);

    if (sRow < 0 || sRow > (armThick * 2) || sCol < 0 || sCol > (armThick * 2)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    } else if (this.board[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow +
              "," + sCol + ")");
    }

    this.board[sRow][sCol] = SlotState.Empty;
    this.boardSize = armThick * 2 + 1;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws
          IllegalArgumentException {

    SlotState from = this.getSlotAt(fromRow, fromCol);
    SlotState to = this.getSlotAt(toRow, toCol);

    // are these coordinates even valid?
    if (from != SlotState.Marble || to != SlotState.Empty) {
      throw new IllegalArgumentException("cannot move from " + from + " -> " + to);
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

    } else {
      throw new IllegalArgumentException("Move must across 2 slots");
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
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
    return 0;
  }

  /**
   * Fills the board's 2D-Array with values in a cross shape
   * @param armThickness the given arm thickness
   */
  // For now this does not include error checks, but it probably SHOULD
  private void fillBoard(int armThickness) {
    int boardLen = armThickness*2 + 1;
    int armStart = armThickness - 1;
    int armEnd = armThickness*2 - 1;
    this.board = new SlotState[boardLen][boardLen];

    for (int i = 0; i < (boardLen - 1); i++) {
      for (int j = 0; j < (boardLen - 1); j++) {
        this.board[i][j] = SlotState.Invalid;
        if ((i > armStart && i < armEnd) || (j > armStart && j < armEnd)) {
          this.board[i][j] = SlotState.Marble;
        }
      }
    }
  }
}
