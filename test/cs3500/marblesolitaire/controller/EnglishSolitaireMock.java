package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Mock class for {@code EnglishSolitaireModel} input testing.
 */
public class EnglishSolitaireMock implements MarbleSolitaireModel {

  private final StringBuilder log;

  /**
   * Creates a new {@code EnglishSolitaireMock} with this StringBuilder.
   * @param log the given StringBuilder
   */
  EnglishSolitaireMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    this.log.append(String.format("fromRow= %d, fromCol= %d, toRow= %d, toCol= %d",
            fromRow, fromCol, toRow, toCol));
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    this.log.append(String.format("row= %d, col= %d", row, col));
    return null;
  }

  @Override
  public int getScore() {
    return 0;
  }
}
