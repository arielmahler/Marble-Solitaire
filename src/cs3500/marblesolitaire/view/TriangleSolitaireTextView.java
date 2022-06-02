package cs3500.marblesolitaire.view;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class TriangleSolitaireTextView implements MarbleSolitaireView {

  private MarbleSolitaireModelState board;
  private Appendable output;

  public TriangleSolitaireTextView(MarbleSolitaireModel model) {
    this.board = model;
    this.output = System.out;
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
        MarbleSolitaireModelState.SlotState currSlot = this.board.getSlotAt(i, j);
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
        if (!(j + 1 >= boardSize || (this.board.getSlotAt(i, j + 1) == MarbleSolitaireModelState.SlotState.Invalid
                && currSlot != MarbleSolitaireModelState.SlotState.Invalid))) {
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
