package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * Mock class for {@code MarbleSolitaireTestView} input testing.
 */
public class MarbleSolitaireViewMock implements MarbleSolitaireView {

  private final StringBuilder log;

  /**
   * Creates a new {@code MarbleSolitaireViewMock} with this StringBuilder.
   * @param log the given StringBuilder
   */
  MarbleSolitaireViewMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderBoard() {
    System.out.println("The board");
  }

  @Override
  public void renderMessage(String message) {
    this.log.append(message);
  }
}
