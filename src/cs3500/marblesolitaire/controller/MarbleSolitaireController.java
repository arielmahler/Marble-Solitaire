package cs3500.marblesolitaire.controller;

/**
 * Interface representing the controller methods for a Marble Solitaire game.
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of Marble Solitaire.
   * @throws IllegalStateException if the controller is unable to read input or transmit output
   */
  void playGame() throws IllegalStateException;
}
