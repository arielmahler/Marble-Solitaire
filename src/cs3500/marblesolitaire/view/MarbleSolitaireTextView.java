package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import static cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;

/**
 * Class representing the text view of a Marble Solitaire game.
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {

  private MarbleSolitaireModelState board;

  /**
   * Creates a {@code MarbleSolitaireTextView} using the given model as the game-board.
   * @param model the given game that needs to be viewed
   * @throws IllegalArgumentException if the game is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.board = model;
  }

  @Override
  public String toString() {
    //The longest edge of the board (and by proxy, the array)
    int boardSize = this.board.getBoardSize();
    String image = "";

    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        SlotState currSlot = this.board.getSlotAt(i, j);
        switch (currSlot) {
          case Marble:
            image += "O";
            break;
          case Empty:
            image += "_";
            break;
          case Invalid:
            image += " ";
            break;
          default:
            // There is no need to add anything else here, currSlot is an enum.
            break;
        }

        //The arguments MUST exist like this: short circuit
        if (!(j + 1 >= boardSize || (this.board.getSlotAt(i, j + 1) == SlotState.Invalid
                && currSlot != SlotState.Invalid))) {
          image += " ";
        } else {
          // We have reached the end of our row
          break;
        }
      }
      //Edge case ;)
      if (i + 1 < boardSize) {
        image += "\n";
      }
    }

    return image;
  }
}
