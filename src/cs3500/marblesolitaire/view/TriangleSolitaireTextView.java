package cs3500.marblesolitaire.view;

import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;

/**
 * Class representing the text view of a Triangle Marble Solitaire game.
 */
public class TriangleSolitaireTextView implements MarbleSolitaireView {

  private MarbleSolitaireModelState board;
  private Appendable output;

  /**
   * Creates a {@code TriangleSolitaireTextView} using the given model as the game-board and
   * System.out as the output.
   * @param model the given game that needs to be viewed
   * @throws IllegalArgumentException if the game is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.board = model;
    this.output = System.out;
  }

  /**
   * Creates a {@code TriangleSolitaireTextView} using the given model as the game-board and
   * given appendable as the output.
   * @param model the given game that needs to be viewed
   * @param out the {@code Appendable} that this view will write to
   * @throws IllegalArgumentException if the game is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.board = model;
    this.output = out;
  }

  @Override
  public void renderBoard() throws IOException {
    this.output.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }

  @Override
  public String toString() {
    //The longest edge of the board (and by proxy, the array)
    int boardSize = this.board.getBoardSize();
    StringBuilder image = new StringBuilder();
    for (int i = 0; i < boardSize; i++) {
      String padding = "%" + (boardSize - 1 - i) + "s";
      if ((boardSize - 1 - i) > 0) {
        image.append(String.format(padding, " "));
      }
      for (int j = 0; j < boardSize; j++) {
        SlotState currSlot = this.board.getSlotAt(i, j);
        switch (currSlot) {
          case Marble:
            image.append("O");
            break;
          case Empty:
            image.append("_");
            break;
          case Invalid:
            image.append(" ");
            break;
          default:
            // There is no need to add anything else here, currSlot is an enum.
            break;
        }

        //The arguments MUST exist like this: short circuit
        if (!(j + 1 >= boardSize || (this.board.getSlotAt(i, j + 1) == SlotState.Invalid
                && currSlot != SlotState.Invalid))) {
          image.append(" ");
        } else {
          // We have reached the end of our row
          break;
        }
      }
      //Edge case ;)
      if (i + 1 < boardSize) {
        image.append("\n");
      }
    }

    return image.toString();
  }
}
