package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Class representing the Triangle Solitaire version of the Marble Solitaire game.
 */
public class TriangleSolitaireModel extends AbstractSolitaireModel
        implements MarbleSolitaireModel {


  /**
   * Creates a new {@code TriangleSolitaireModel} with the default bottom size 5,
   * and empty slot at the top.
   */
  public TriangleSolitaireModel() {
    build(5, 5, 0, 0);
  }

  /**
   * Creates a new {@code TriangleSolitaireModel} with the given bottom dimension and empty slot
   * at the top. dimensions is restricted to being > 1, as any other board is either impossible or
   * immediately contains no valid moves.
   * @param dimensions the given dimension of the bottom row
   * @throws IllegalArgumentException if dimensions is less than 3.
   */
  public TriangleSolitaireModel(int dimensions) throws IllegalArgumentException {
    if (dimensions < 2) {
      throw new IllegalArgumentException("dimensions must be > 1");
    }
    build(dimensions, dimensions, 0,0);
  }

  /**
   * Creates a new {@code TriangleSolitaireModel} with the default bottom dimension 5 and
   * an empty slot in the given cell.
   * @param row the given row
   * @param col the given column
   * @throws IllegalArgumentException if the given position does not lie on the array, or lies on
   *     an invalid space
   */
  public TriangleSolitaireModel(int row, int col) throws IllegalArgumentException {
    build(5, 5, row, col);
  }

  /**
   * Creates a new {@code TriangleSolitaireModel} with the given bottom dimension and
   * an empty slot in the given cell.
   * @param dimensions the given dimensions of the bottom row
   * @param row the given row
   * @param col the given column
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) throws IllegalArgumentException {
    if (dimensions < 2) {
      throw new IllegalArgumentException("dimensions must be > 1");
    }
    build(dimensions, dimensions, row, col);
  }

  /**
   * Builder for the {@code TriangleSolitaireModel} class.
   */
  public static class TriangleModelBuilder extends SolitaireBuilder<TriangleModelBuilder> {

    /**
     * Creates a {@code TriangleModelBuilder} with default init values.
     */
    public TriangleModelBuilder() {
      this.size = 5;
      this.row = 0;
      this.col = 0;
    }

    protected TriangleModelBuilder returnBuilder() {
      return this;
    }

    protected TriangleSolitaireModel returnModel() {
      return new TriangleSolitaireModel(this.size, this.row, this.col);
    }
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (this.getSlotAt(i, j) == SlotState.Marble) {

          // Set up surrounding slot variables
          SlotState rightDiagUp = SlotState.Invalid;
          SlotState leftDiagDown = SlotState.Invalid;
          SlotState rightDiagDown = SlotState.Invalid;
          SlotState leftDiagUp = SlotState.Invalid;
          SlotState left = SlotState.Invalid;
          SlotState right = SlotState.Invalid;

          if (i - 1 >= 0) {
            rightDiagUp = this.getSlotAt(i - 1, j);
          }
          if (i + 1 < this.boardSize) {
            leftDiagDown = this.getSlotAt(i + 1, j);
          }
          if (j - 1 >= 0) {
            left = this.getSlotAt(i, j - 1);
          }
          if (j + 1 < this.boardSize) {
            right = this.getSlotAt(i, j + 1);
          }
          if (i - 1 >= 0 && j - 1 >= 0) {
            leftDiagUp = this.getSlotAt(i - 1, j - 1);
          }
          if (i + 1 < this.boardSize && j + 1 < this.boardSize) {
            rightDiagDown = this.getSlotAt(i + 1, j + 1);
          }

          // All of our move cases
          if ((rightDiagUp == SlotState.Marble && leftDiagDown == SlotState.Empty) ||
                  (rightDiagUp == SlotState.Empty && leftDiagDown == SlotState.Marble) ||
                  (rightDiagDown == SlotState.Marble && leftDiagUp == SlotState.Empty) ||
                  (rightDiagDown == SlotState.Empty && leftDiagUp == SlotState.Marble) ||
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
  protected int fillBoard(int bottom) {
    this.board = new SlotState[bottom][bottom];
    int marbles = 0;

    for (int i = 0; i < bottom; i++) {
      for (int j = 0; j < bottom; j++) {
        this.board[i][j] = SlotState.Invalid;
        if (j <= i) {
          this.board[i][j] = SlotState.Marble;
          marbles++;
        }
      }
    }
    return marbles;
  }

  @Override
  protected boolean validMove(int rowDelta, int colDelta) {
    return super.validMove(rowDelta, colDelta) || //up, down, left, right
            (colDelta == rowDelta && (Math.abs(rowDelta) == 2)); //diagonal
  }
}
