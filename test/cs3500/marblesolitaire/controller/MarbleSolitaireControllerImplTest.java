package cs3500.marblesolitaire.controller;

import org.junit.Before;
import org.junit.Test;
import java.io.StringReader;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class containing tests for the {@code MarbleSolitaireControllerImpl} class.
 */
public class MarbleSolitaireControllerImplTest {

  Appendable a1;
  Readable r1;
  MarbleSolitaireView v1;
  MarbleSolitaireModel m1;
  MarbleSolitaireController c1;

  @Before
  public void init() {
    this.a1 = new StringBuilder();
    this.r1 = new StringReader("");
    this.m1 = new EnglishSolitaireModel();
    this.v1 = new MarbleSolitaireTextView(this.m1, this.a1);
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
  }

  //FIXME: THIS NEEDS AT LEAST ONE ASSERTEQUALS
  @Test
  public void testInitModelConstructors() {
    this.c1 = new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(), this.v1, this.r1);
    this.c1 = new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(2,2),
            this.v1, this.r1);
    this.c1 = new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(5),
            this.v1, this.r1);
    this.c1 = new MarbleSolitaireControllerImpl(new EnglishSolitaireModel(5, 4, 4),
            this.v1, this.r1);
  }

  //FIXME: THIS NEEDS AT LEAST ONE ASSERTEQUALS
  @Test
  public void testInitViewConstructor() {
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, new MarbleSolitaireTextView(this.m1),
            this.r1);
    this.c1 = new MarbleSolitaireControllerImpl(this.m1,
            new MarbleSolitaireTextView(this.m1, new StringBuilder()), this.r1);
  }

  @Test
  public void testInvalidInitModel() {
    try {
      this.c1 = new MarbleSolitaireControllerImpl(null, this.v1, this.r1);
      fail("MarbleSolitaireControllerImpl created controller with null model");
    } catch (IllegalArgumentException e) {
      assertEquals("Model cannot be null", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitView() {
    try {
      this.c1 = new MarbleSolitaireControllerImpl(this.m1, null, this.r1);
      fail("MarbleSolitaireControllerImpl created controller with null view");
    } catch (IllegalArgumentException e) {
      assertEquals("View cannot be null", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitReadable() {
    try {
      this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, null);
      fail("MarbleSolitaireControllerImpl created controller with null Readable");
    } catch (IllegalArgumentException e) {
      assertEquals("Readable cannot be null", e.getMessage());
    }
  }

  /*
  check inputs for:
    String message in transmit() - Mock for view
    model.move inputs - Mock for model
   */

  @Test
  public void playGameNegStartRow() {
      this.r1 = new StringReader("-1 q");
      this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
      this.c1.playGame();

      String log = this.a1.toString();
      String[] splitLog = log.split("\n");

      // Bear in mind that since 'From Row:' doesn't have a \n in it, it will be in the entry
      assertEquals("Invalid choice: Try again", splitLog[9]);
  }

  @Test
  public void playGameNegStartCol() {
    this.r1 = new StringReader("3 -1 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[10]);
  }

  @Test
  public void playGameNegEndRow() {
    this.r1 = new StringReader("3 3 -1 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[11]);
  }

  @Test
  public void playGameNegEndCol() {
    this.r1 = new StringReader("3 3 5 -1 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[12]);
  }

  @Test
  public void playGameQuitStartRow() {
    this.r1 = new StringReader("q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Game quit!", splitLog[9]);
  }

  @Test
  public void playGameQuitStartCol() {
    this.r1 = new StringReader("3 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Game quit!", splitLog[10]);
  }

  @Test
  public void playGameQuitEndRow() {
    this.r1 = new StringReader("3 3 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Game quit!", splitLog[11]);
  }

  @Test
  public void playGameQuitEndCol() {
    this.r1 = new StringReader("3 3 5 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Game quit!", splitLog[12]);
  }

  @Test  public void playGameInvalidStartRow() {
    this.r1 = new StringReader("too q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[9]);
  }

  @Test
  public void playGameInvalidStartCol() {
    this.r1 = new StringReader("3 many q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[10]);
  }

  @Test
  public void playGameInvalidEndRow() {
    this.r1 = new StringReader("3 3 tests q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[11]);
  }

  @Test
  public void playGameInvalidEndCol() {
    this.r1 = new StringReader("3 3 5 -here q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid choice: Try again", splitLog[12]);
  }

  //FIXME: do we need to test additional cases? we already have...
  @Test
  public void playGameInvalidMove() {
    this.r1 = new StringReader("3 3 5 4 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    assertEquals("Invalid move. Play again. " +
            "Cannot move from Marble -> Marble", splitLog[12]);
  }

  @Test
  public void playGameValidMove() {
    this.r1 = new StringReader("4 2 4 4 q");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    //FIXME: maybe make a greater test of accuracy
    assertEquals("O _ _ O O O O", splitLog[15]);
  }

  @Test
  public void playGameOver() {
    //loss steps from gameOver() test
    this.r1 = new StringReader("6 4 4 4 3 4 5 4 1 4 3 4 4 6 4 4 4 3 4 5 4 1 4 3");
    this.c1 = new MarbleSolitaireControllerImpl(this.m1, this.v1, this.r1);
    this.c1.playGame();

    String log = this.a1.toString();
    String[] splitLog = log.split("\n");

    //FIXME: maybe make a greater test of accuracy
    assertEquals("Game over!", splitLog[72]);
    assertEquals("Score: 26", splitLog[80]);
  }

  @Test
  public void playGameOutOfInputs() {
    try {
      this.c1.playGame();
      fail("MarbleSolitaireControllerImpl somehow ran on zero inputs");
    } catch (IllegalStateException e) {
      assertEquals("Input is exhausted", e.getMessage());
    }
  }

  @Test
  public void testInputsTransmit() {
    //TODO: WRITE TEST
  }

  @Test
  public void testInputsMove() {
    //TODO: WRITE TEST
  }
}