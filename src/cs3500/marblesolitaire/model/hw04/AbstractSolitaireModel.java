package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * The abstract class for Solitaire game models containing similar methods and parameters.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {

  protected SlotState[][] board; // the game board model
  protected int boardSize; //The longest edge of the board
  protected int marbles; // the number of marbles present on the board

  /**
   * Abstraction of constructors, will fill board and place empty space in given space. IT WILL
   * NOT catch an error in the size, only in the given position. THE CHILD MUST CATCH SIZE ERRORS.
   * @param boardSize the longest edge of the board
   * @param size the "size" distinguishing the board (e.g., dimensions, arm thickness)
   * @param row the row of the starting position
   * @param col the column of the starting position
   * @throws IllegalArgumentException if the given row and column do not correspond to a valid
   *     spot, whether that be because it is off the board or it is in an invalid spot
   */
  protected void build(int boardSize, int size, int row, int col) throws
          IllegalArgumentException {
    if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
      throw new IllegalArgumentException("Given position is not on array");
    }
    this.marbles = fillBoard(size);
    if (this.board[row][col] == SlotState.Invalid) {
      throw new IllegalArgumentException("Given position (" + row + "," + col + ") is invalid");
    }
    this.board[row][col] = SlotState.Empty;
    this.marbles--;
    this.boardSize = boardSize;
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

    // .getSlotAt() already throws an IllegalArgumentException if either point is off grid
    SlotState from = this.getSlotAt(fromRow, fromCol);
    SlotState to = this.getSlotAt(toRow, toCol);

    if (from != SlotState.Marble || to != SlotState.Empty) {
      throw new IllegalArgumentException("Cannot move from " + from + " -> " + to);
    }
    int rowDelta = toRow - fromRow;
    int colDelta = toCol - fromCol;

    if (this.validMove(rowDelta, colDelta)) {

      // check middle piece, complete motion
      int midRow = (rowDelta / 2) + fromRow;
      int midCol = (colDelta / 2) + fromCol;
      SlotState mid = this.getSlotAt(midRow, midCol);

      if (mid != SlotState.Marble) {
        throw new IllegalArgumentException("Middle spot must contain a marble");
      }

      //If we are here, the move is already deemed valid: we can do it
      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;
      this.board[midRow][midCol] = SlotState.Empty;
      this.marbles--;
    } else if (rowDelta != 0 && colDelta != 0) {
      throw new IllegalArgumentException("Invalid move direction");
    } else {
      throw new IllegalArgumentException("Move must only cross 2 slots");
    }
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (this.getSlotAt(i, j) == SlotState.Marble) {

          // Set up surrounding slot variables
          SlotState up = SlotState.Invalid;
          SlotState down = SlotState.Invalid;
          SlotState left = SlotState.Invalid;
          SlotState right = SlotState.Invalid;

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
    if (row < 0 || row >= this.boardSize || col < 0 || col >= this.boardSize) {
      throw new IllegalArgumentException("Given position is not on the board");
    }
    return this.board[row][col];
  }

  @Override
  public int getScore() {
    return this.marbles;
  }

  // All implementations of this class are required to provide their own way to fill their board

  /**
   * Fills the board with SlotStates appropriate to the style of Marble Solitaire game and returns
   * the number of marbles the board contains.
   * @param size the "size" of the board (arm thickness, dimensions)
   * @return the amount of marbles on the board
   */
  protected abstract int fillBoard(int size);

  /**
   * Evaluates row and column deltas, then uses them to see if it constitutes a valid move.
   * @param rowDelta difference between the from row and to row
   * @param colDelta difference between the from column and to column
   * @return true if case is satisfied, false otherwise
   */
  // For right now, I can further reduce the amount of code used if I make the default (this impl)
  // just the code for European and English solitaire.
  protected boolean validMove(int rowDelta, int colDelta) {
    return (Math.abs(rowDelta) == 2 && colDelta == 0) ||
            (Math.abs(colDelta) == 2 && rowDelta == 0);
  }
}
