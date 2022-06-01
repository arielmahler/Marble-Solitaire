package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Class representing a European Solitaire version of the Marble Solitaire game.
 */
public class EuropeanSolitaireModel extends AbstractSolitaireModel implements MarbleSolitaireModel {

  /**
   * Creates a new {@code EuropeanSolitaireModel} with the default side length 3,
   * and empty slot in the middle.
   */
  public EuropeanSolitaireModel() {
    build(7, 3, 3, 3);
  }

  /**
   * Creates a new {@code EuropeanSolitaireModel} with the given side length and empty slot
   * in the middle.
   * @param sideLen the given side length
   * @throws IllegalArgumentException if side length is not an odd number >= 3
   */
  public EuropeanSolitaireModel(int sideLen) throws IllegalArgumentException {
    if (sideLen < 3 || sideLen % 2 == 0) {
      throw new IllegalArgumentException("Side length must be an odd number >= 3");
    }
    // Both of these were found with math, and inherited from the EnglishSolitaireModel
    int mid = ((3 * sideLen) / 2) - 1;
    int boardSize = (3 * sideLen) - 2;

    build(boardSize, sideLen, mid, mid);
  }

  /**
   * Creates a new {@code EuropeanSolitaireModel} with the default side length 3 and
   * an empty slot in the given cell.
   * @param row the given row
   * @param col the given column
   * @throws IllegalArgumentException if the row and column do not correspond to a
   * valid start position.
   */
  public EuropeanSolitaireModel(int row, int col) throws IllegalArgumentException {
    build(7, 3, row, col);
  }

  /**
   * Creates a new {@code EuropeanSolitaireModel} with the given side length and
   * an empty slot in the given cell.
   * @param sideLen the given side length
   * @param row the given row
   * @param col the given column
   * @throws IllegalArgumentException if the side length is not an odd number >= 3
   */
  public EuropeanSolitaireModel(int sideLen, int row, int col) throws IllegalArgumentException {
    if (sideLen < 3 || sideLen % 2 == 0) {
      throw new IllegalArgumentException("Side length must be an odd number >= 3");
    }
    int boardSize = (3 * sideLen) - 2;
    build(boardSize, sideLen, row, col);
  }

  /**
   * Fills the board's 2D-Array with values in an octagonal shape, additionally setting the score.
   *
   * @param sideLen the given side length
   */
  @Override
  protected int fillBoard(int sideLen) {
    int boardLen = (3 * sideLen) - 2;
    int rowStart = sideLen - 1;
    int rowEnd = sideLen * 2 - 1;
    int colStart = rowStart;
    int colEnd = rowEnd;
    int inc = 1;
    int marbles = 0;
    this.board = new SlotState[boardLen][boardLen];

    for (int i = 0; i < boardLen; i++) {
      for (int j = 0; j < boardLen; j++) {
        this.board[i][j] = SlotState.Invalid;
        if ((i >= rowStart && i < rowEnd) || (j >= colStart && j < colEnd)) {
          this.board[i][j] = SlotState.Marble;
          marbles++;
        }
      }
      // For the sides
      if ((i < rowStart || i >= (rowEnd - 1))) {
        colStart -= inc;
        colEnd += inc;
      } else if (i == sideLen - 1) {
        inc = -1;
      }
    }
    return marbles;
  }


  protected void validMove(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {

    int rowDelta = toRow - fromRow;
    int colDelta = toCol - fromCol;

    if ((Math.abs(rowDelta) == 2 && colDelta == 0) ||
            (Math.abs(colDelta) == 2 && rowDelta == 0)) {

      // check middle piece, complete motion
      int midRow = (rowDelta / 2) + fromRow;
      int midCol = (colDelta / 2) + fromCol;
      SlotState mid = this.getSlotAt(midRow, midCol);

      if (mid != SlotState.Marble) {
        throw new IllegalArgumentException("Middle spot must contain a marble");
      }
    } else if (rowDelta != 0 && colDelta != 0) {
      throw new IllegalArgumentException("Move cannot be diagonal");
    } else {
      throw new IllegalArgumentException("Move must only cross 2 slots");
    }
  }
}
