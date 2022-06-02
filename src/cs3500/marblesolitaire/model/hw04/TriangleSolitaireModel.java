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
   * at the top. dimensions is restricted to being > 2, as any other board is either impossible or
   * immediately contains no valid moves.
   * @param dimensions the given dimension of the bottom row
   * @throws IllegalArgumentException if dimensions is less than 3.
   */
  public TriangleSolitaireModel(int dimensions) throws IllegalArgumentException {
    if (dimensions < 3) {
      throw new IllegalArgumentException("dimensions must be > 2");
    }
    build(dimensions, dimensions, 0,0);
  }

  /**
   * Creates a new {@code TriangleSolitaireModel} with the default bottom dimension 5 and
   * an empty slot in the given cell.
   * @param row the given row
   * @param col the given column
   * @throws IllegalArgumentException if the given position does not lie on the array, or lies on
   * an invalid space
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
    if (dimensions < 3) {
      throw new IllegalArgumentException("dimensions must be > 2");
    }
    build(dimensions, dimensions, row, col);
  }

  /**
   * Builder for the {@code TriangleSolitaireModel} class.
   */
  public static class TriangleModelBuilder extends SolitaireBuilder<TriangleModelBuilder>{

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

  /* TODO:
      isGameOver() needs to be overriden
   */
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
