package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable input;

  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable in) throws IllegalArgumentException {
    // While it means more lines of code, I appreciate decent error messages!
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    } else if (in == null) {
      throw new IllegalArgumentException("Readable cannot be null");
    }

    this.model = model;
    this.view = view;
    this.input = in;
  }

  @Override
  public void playGame() throws IllegalStateException {
    //Scanner for the Readable
    Scanner scan = new Scanner(this.input);
    // boolean value for regulating if the game has ended or not
    boolean gameOver = false;

    // the values that we will use to hold user input from turn to turn.
    // 0: startRow 1: startCol 2: endRow 3: endCol
    int[] values = new int[4];

    // the input prompts for the user
    String[] inputPrompts = {"From Row: ", "From Column: ", "To Row: ", "To Column: "};

    while (!gameOver) {

      //render the current state of the game
      try {
        this.view.renderBoard();
      } catch (IOException e) {
        throw new IllegalStateException(e.getMessage());
      }

      // transmit the score as "Score: N"
      try {
        //need the extra \n to move lines from the board!
        this.view.renderMessage("\nScore: " + this.model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException(e.getMessage());
      }

      // obtain the next user input through the Readable
      // This will be in the format "row-num col-num row-num col-num"
      // all starting at 1 (meaning adjust the input to -1)
      // or, "q" and "Q", representing end game
      // if an input is invalid, ask for THAT input again (indicating loops?)
      boolean validMove = false;
      // MOVE LOOP ----------------------------------------------------------------------------
      while (!validMove) {

        boolean fullInput = false;
        int inputCount = 0; //number of SUCCESSFUL inputs gathered

        // INPUT LOOP -------------------------------------------------------------------------
        while (!fullInput) {
          transmit(inputPrompts[inputCount]);
          if (!scan.hasNextInt()) {
            if (scan.next().equalsIgnoreCase("q")) {
              //TODO: apparently return; should NOT be used :(
              transmit("Game quit!\nState of game when quit:\n");
              transmitBoard();
              transmit("\nScore: " + this.model.getScore());
              //gameOver = true; ???
              return;
            } else {
              transmit("Invalid choice: Try again\n");
            }
          } else { //logically, this means next() is an int
            int value = scan.nextInt();

            try {
              values[inputCount] = value - 1;
              inputCount++;
            } catch (NullPointerException e) {
              throw new IllegalStateException("inputCount should not be" + inputCount);
            }
            if (inputCount == 4) {
              //we have now successfully collected 4 inputs and filled values
              fullInput = true;
            }
          }
        }
        // INPUT LOOP -------------------------------------------------------------------------

        //pass info on to the model to make the move (or fail)
        // at this point we assume that all 4 variables were chosen
        try {
          this.model.move(values[0], values[1], values[2], values[3]);
          // the move worked without throwing an exception, it is valid
          validMove = true;
        } catch (IllegalArgumentException e) {
          // arguments were incorrect
          transmit("Invalid move. Play again. " + e.getMessage() + "\n");
          fullInput = false;
        }
        //the end of the turn, assuming everything worked out.
      }
      // MOVE LOOP ----------------------------------------------------------------------------

      // Finally: evaluate the board
      if (this.model.isGameOver() || gameOver) {
        String gameOverMessage;
        // Determine the ending message
        if (gameOver) {
          // this is only reachable if you hit "q"
          gameOverMessage = "Game quit!\nState of game when quit:\n";
        } else {
          //otherwise, the game is simply over
          gameOver = true;
          gameOverMessage = "Game over!\n";
        }
        transmit(gameOverMessage);
        transmitBoard();
        transmit("\nScore: " + this.model.getScore());
      }
    }
  }

  /**
   * can be used to send a message through this view, while changing IOExceptions to
   * IllegalStateExceptions.
   * @param message the given message
   * @throws IllegalStateException if there is an IOException
   */
  private void transmit(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch(IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * can be used to send the board through this view, while changing IOExceptions to
   * IllegalStateExceptions.
   * @throws IllegalStateException if there is an IOException
   */
  private void transmitBoard() throws IllegalStateException {
    try {
      this.view.renderBoard();
    } catch(IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
