package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * Represents the controller for the Marble Solitaire game.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable input;

  /**
   * Creates a new {@code MarbleSolitaireControllerImpl} with given model and view.
   * Uses in as input.
   * @param model the given MarbleSolitaire model
   * @param view the given MarbleSolitaire view
   * @param in the given input Readable
   * @throws IllegalArgumentException if any of the arguments are null
   */
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
    String[] inputPrompts = {"From Row:\n", "From Column:\n", "To Row:\n", "To Column:\n"};

    while (!gameOver) {

      //render the current state of the game
      transmitBoard();
      transmit("\nScore: " + this.model.getScore() + "\n");

      boolean validMove = false;
      boolean quit = false;

      // MOVE LOOP ----------------------------------------------------------------------------
      while (!validMove) {

        boolean fullInput = false;
        int inputCount = 0; //number of SUCCESSFUL inputs gathered

        // INPUT LOOP -------------------------------------------------------------------------
        while (!fullInput) {
          transmit(inputPrompts[inputCount]);
          if (!scan.hasNextInt()) {

            String next;
            try {
              next = scan.next();
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Input is exhausted");
            }

            if (next.equalsIgnoreCase("q")) {
              quit = true;
              break; //leave input loop
            } else {
              transmit("Invalid choice: Try again\n");
            }
          } else {
            //scan is either empty, or an integer
            int value = scan.nextInt();

            if (value < 1) {
              transmit("Invalid choice: Try again\n");
              continue;
            }

            values[inputCount] = value - 1;
            inputCount++;
            if (inputCount == 4) {
              //we have now successfully collected 4 inputs and filled values
              fullInput = true;
            }
          }
        }
        // INPUT LOOP -------------------------------------------------------------------------

        if (quit) {
          break;
        }

        //pass info on to the model to make the move (or fail)
        try {
          this.model.move(values[0], values[1], values[2], values[3]);
          // the move worked without throwing an exception, it is valid
          validMove = true;
        } catch (IllegalArgumentException e) {
          transmit("Invalid move. Play again. " + e.getMessage() + "\n");
        }
      }
      // MOVE LOOP ----------------------------------------------------------------------------

      // Finally: evaluate the board
      if (this.model.isGameOver() || quit) {
        String gameOverMessage;
        gameOver = true;
        if (quit) {
          gameOverMessage = "Game quit!\nState of game when quit:\n";
        } else {
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
    } catch (IOException e) {
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
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
