package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Abstract class representing the abstracted methods that each of the model builders have.
 * @param <T> A builder that extends {@code SolitaireBuilder}
 */
public abstract class SolitaireBuilder<T extends SolitaireBuilder<T>> {
  protected int size;
  protected int row;
  protected int col;

  /**
   * Set the size of the Builder to given value.
   * @param s the new size of the builder
   * @return new builder of same initial type, with modified size
   */
  public T setSize(int s) {
    this.size = s;
    return returnBuilder();
  }

  /**
   * Set the hole of the Builder to given position.
   * @param r the new row of the builder
   * @param c the new column of the builder
   * @return new builder of same initial type, with modified row value
   */
  public T setHole(int r, int c) {
    this.row = r;
    this.col = c;
    return returnBuilder();
  }

  /**
   * Returns, or "builds" the {@code MarbleSolitaireModel} corresponding to this builder.
   * @return the {@code MarbleSolitaireModel} with values equal to the ones in this builder
   */
  public MarbleSolitaireModel build() {
    return returnModel();
  }

  /**
   * returns this builder.
   * @return this builder
   */
  protected abstract T returnBuilder();

  /**
   * returns the model that would be created by this builder.
   * @return a {@code MarbleSolitaireModel} with values equal to builder
   */
  protected abstract MarbleSolitaireModel returnModel();
}
