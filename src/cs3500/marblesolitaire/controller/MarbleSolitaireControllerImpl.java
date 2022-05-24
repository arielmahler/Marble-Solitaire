package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private MarbleSolitaireModel model;
  private MarbleSolitaireView view;
  private Readable input;

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

  //TODO: I either need another constructor, or to create a mock implementation

  @Override
  public void playGame() throws IllegalStateException {
    //Scanner for the Readable
    Scanner scan = new Scanner(this.input);
    // boolean value for regulating if the game has ended or not
    boolean gameOver = false;

    // the values that we will use to hold user input from turn to turn.
    int startRow;
    int startCol;
    int endRow;
    int endCol;

    while (!gameOver) {
      // this insures a loop invariant for all 4 inputs
      startRow = -1;
      startCol = -1;
      endRow = -1;
      endCol = -1;

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
      boolean fullInput = false;
      while(!fullInput) {

        if (!scan.hasNextInt()) {
          if (scan.next().toLowerCase().equals("q")) {
            gameOver = true;
          } else {
            //this is both not an integer or a quit command, ask again
          }
        } else {
          // this value is an integer
          int value = scan.nextInt();

          //TODO: This is poopy code, don't know how to fix YET.
          if (startRow == -1) {

          }
        }
      }

      //pass info on to the model to make the move (or fail)
      // at this point we assume that all 4 variables were chosen
      try {
        this.model.move(startRow, startCol, endRow, endCol);
      } catch (IllegalArgumentException e) {
        //TODO: if this is read that means the argument inputs were incorrect
      }

      //in theory, the fullInput loop should end here...

      // Finally: evaluate the board
      if (this.model.isGameOver() || gameOver) {
        String gameOverMessage = "";
        // Determine the ending message
        if (gameOver) {
          // this is only reachable if you hit "q"
          gameOverMessage = "Game quit!\nState of game when quit:\n";
        } else {
          //otherwise, the game is simply over
          gameOver = true;
          gameOverMessage = "Game over!\n";
        }
        try {
          this.view.renderMessage(gameOverMessage);
          this.view.renderBoard();
          this.view.renderMessage("\nScore: " + this.model.getScore());
        } catch (IOException e) {
          throw new IllegalStateException(e.getMessage());
        }
      }
    }
  }
}
