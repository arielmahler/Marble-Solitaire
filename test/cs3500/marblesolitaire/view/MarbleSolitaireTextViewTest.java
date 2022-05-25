package cs3500.marblesolitaire.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.marblesolitaire.CorruptAppendable;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing class for {@code MarbleSolitaireTextView} class and methods.
 */
public class MarbleSolitaireTextViewTest {

  MarbleSolitaireModel m1;
  MarbleSolitaireTextView t1;
  Appendable a1;

  MarbleSolitaireTextView t2;
  Appendable a2;
  MarbleSolitaireTextView t3;
  Appendable a3;
  MarbleSolitaireTextView t4;
  Appendable a4;

  @Before
  public void init() {
    this.m1 = new EnglishSolitaireModel();

    this.a1 = new StringBuilder();
    this.t1 = new MarbleSolitaireTextView(this.m1, this.a1);

    this.a2 = new StringBuilder();
    this.t2 = new MarbleSolitaireTextView(new EnglishSolitaireModel(2, 2), this.a2);

    this.a3 = new StringBuilder();
    this.t3 = new MarbleSolitaireTextView(new EnglishSolitaireModel(5), this.a3);

    this.a4 = new StringBuilder();
    this.t4 = new MarbleSolitaireTextView(new EnglishSolitaireModel(5, 7, 7),
            this.a4);
  }

  @Test
  public void testInitFirstConstructor() {
    this.t1 = new MarbleSolitaireTextView(new EnglishSolitaireModel());
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O O O _ O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());
    this.t2 = new MarbleSolitaireTextView(new EnglishSolitaireModel(2, 2));
    assertEquals("    O O O\n    O O O\nO O _ O O O O\n"
            + "O O O O O O O\nO O O O O O O\n    O O O\n    O O O", this.t2.toString());
    this.t3 = new MarbleSolitaireTextView((new EnglishSolitaireModel(5)));
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t3.toString());
    this.t4 = new MarbleSolitaireTextView((new EnglishSolitaireModel(5, 8, 10)));
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O _ O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t4.toString());
  }

  @Test
  public void testInitSecondConstructor() {
    this.t1 = new MarbleSolitaireTextView(this.m1, this.a1);
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O O O _ O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());

    this.t2 = new MarbleSolitaireTextView(new EnglishSolitaireModel(2, 2), this.a2);
    assertEquals("    O O O\n    O O O\nO O _ O O O O\n"
            + "O O O O O O O\nO O O O O O O\n    O O O\n    O O O", this.t2.toString());
    this.t3 = new MarbleSolitaireTextView(new EnglishSolitaireModel(5), this.a3);
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t3.toString());
    this.t4 = new MarbleSolitaireTextView(new EnglishSolitaireModel(5, 8, 10),
            this.a4);
     assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O _ O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t4.toString());
  }

  @Test
  public void testInvalidInitModel() {
    try {
      this.t1 = new MarbleSolitaireTextView(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Model cannot be null", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitAppendable() {
    try {
      this.t1 = new MarbleSolitaireTextView(this.m1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable cannot be null", e.getMessage());
    }
  }

  @Test
  public void testToString() {
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O O O _ O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());
    assertEquals("    O O O\n    O O O\nO O _ O O O O\n"
            + "O O O O O O O\nO O O O O O O\n    O O O\n    O O O", this.t2.toString());
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t3.toString());
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
            + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\nO O O O O O O _ O O O O O\nO O O O O O O O O O O O O\n"
            + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.t4.toString());
  }

  @Test
  public void testToStringMove() {
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O O O _ O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());
    this.m1.move(3, 1, 3, 3);
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O _ _ O O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());
  }

  @Test
  public void testRenderBoard() {
    try {
      this.t1.renderBoard();
      this.t2.renderBoard();
      this.t3.renderBoard();
      this.t4.renderBoard();
    } catch (IOException e) {
      fail("Something has gone wrong rendering the board");
    }

    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O O O _ O O O\nO O O O O O O\n    O O O\n    O O O", this.a1.toString());
    assertEquals("    O O O\n    O O O\nO O _ O O O O\n"
            + "O O O O O O O\nO O O O O O O\n    O O O\n    O O O", this.a2.toString());
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
                    + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
                    + "O O O O O O _ O O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
                    + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.a3.toString());
    assertEquals("        O O O O O\n        O O O O O\n        O O O O O\n"
                    + "        O O O O O\nO O O O O O O O O O O O O\nO O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\nO O O O O O O _ O O O O O\nO O O O O O O O O O O O O\n"
                    + "        O O O O O\n        O O O O O\n        O O O O O\n        O O O O O",
            this.a4.toString());
  }

  @Test
  public void testRenderBoardIOException() {
    this.t1 = new MarbleSolitaireTextView(this.m1, new CorruptAppendable());

    try {
      this.t1.renderBoard();
      fail("MarbleSolitaireTextView ignored IOException");
    } catch (IOException e) {
      assertEquals("Did not append!", e.getMessage());
    }
  }

  @Test
  public void testRenderMessage() {
    try {
      this.t1.renderMessage("Testing...");
      this.t2.renderMessage("Testing...");
      this.t3.renderMessage("Testing...");
      this.t4.renderMessage("Testing...");
    } catch (IOException e) {
      fail("Something has gone wrong rendering the message");
    }

    assertEquals("Testing...", this.a1.toString());
    assertEquals("Testing...", this.a2.toString());
    assertEquals("Testing...", this.a3.toString());
    assertEquals("Testing...", this.a4.toString());
  }

  @Test
  public void testRenderMessageIOException() {
    this.t1 = new MarbleSolitaireTextView(this.m1, new CorruptAppendable());

    try {
      this.t1.renderMessage("Testing...");
      fail("MarbleSolitaireTextView ignored IOException");
    } catch (IOException e) {
      assertEquals("Did not append!", e.getMessage());
    }
  }
}