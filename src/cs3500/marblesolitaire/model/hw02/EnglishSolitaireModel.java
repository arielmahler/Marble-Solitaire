package cs3500.marblesolitaire.model.hw02;


import cs3500.marblesolitaire.model.hw04.AbstractSolitaireModel;
import cs3500.marblesolitaire.model.hw04.SolitaireBuilder;

/**
 * Represents the English Solitaire version of the Marble Solitaire game.
 */
public class EnglishSolitaireModel extends AbstractSolitaireModel implements MarbleSolitaireModel {

  /**
   * Creates a {@code EnglishSolitaireModel} based on default specifications
   * (arm thickness 3, center slot empty).
   */
  public EnglishSolitaireModel() {
    build(7, 3, 3, 3);
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
    build(7, 3, sRow, sCol);
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
    //VERY IMPORTANT: int's automatic removal of 0.5 helps us here, we want the lower bound
    int mid = ((3 * armThickness) / 2) - 1;
    int size = (3 * armThickness) - 2; // Found doing math
    build(size, armThickness, mid, mid);
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

    int size = (3 * armThick) - 2;
    build(size, armThick, sRow, sCol);
  }

  /**
   * Builder for the {@code EnglishSolitaireModel} class.
   */
  public static class EnglishModelBuilder extends SolitaireBuilder<EnglishModelBuilder> {

    /**
     * Creates a {@code EnglishModelBuilder} with default init values.
     */
    public EnglishModelBuilder() {
      this.size = 3;
    }

    protected EnglishModelBuilder returnBuilder() {
      return this;
    }

    protected EnglishSolitaireModel returnModel() {
      if (this.row == 0 || this.col == 0) { //never initialized
        return new EnglishSolitaireModel(this.size);
      }
      return new EnglishSolitaireModel(this.size, this.row, this.col);
    }
  }

  /**
   * Fills the board's 2D-Array with values in a cross shape, additionally setting the score.
   *
   * @param armThickness the given arm thickness
   */
  protected int fillBoard(int armThickness) {
    int boardLen = (3 * armThickness) - 2;
    int armStart = armThickness - 1;
    int armEnd = armThickness * 2 - 1;
    int marble = 0;
    this.board = new SlotState[boardLen][boardLen];

    for (int i = 0; i < boardLen; i++) {
      for (int j = 0; j < boardLen; j++) {
        this.board[i][j] = SlotState.Invalid;
        if ((i >= armStart && i < armEnd) || (j >= armStart && j < armEnd)) {
          this.board[i][j] = SlotState.Marble;
          marble++;
        }
      }
    }
    return marble;
  }
}
