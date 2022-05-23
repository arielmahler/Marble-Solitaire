package cs3500.marblesolitaire.view;

import org.junit.Before;
import org.junit.Test;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for {@code MarbleSolitaireTextView} class and methods.
 */
public class MarbleSolitaireTextViewTest {

  MarbleSolitaireTextView t1;

  MarbleSolitaireModel m1;
  MarbleSolitaireTextView t2;
  MarbleSolitaireTextView t3;
  MarbleSolitaireTextView t4;

  @Before
  public void init() {
    this.m1 = new EnglishSolitaireModel();
    this.t1 = new MarbleSolitaireTextView(this.m1);
    this.t2 = new MarbleSolitaireTextView(new EnglishSolitaireModel(2, 2));
    this.t3 = new MarbleSolitaireTextView((new EnglishSolitaireModel(5)));
    this.t4 = new MarbleSolitaireTextView((new EnglishSolitaireModel(5, 7, 7)));
  }

  @Test
  public void testInit() {
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
  public void testInvalidInit() {
    MarbleSolitaireModelState failCase = null;
    try {
      this.t1 = new MarbleSolitaireTextView(failCase);
    } catch (IllegalArgumentException e) {
      assertEquals("Model cannot be null", e.getMessage());
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

    this.m1.move(3, 1, 3, 3);
    assertEquals("    O O O\n    O O O\nO O O O O O O\n"
            + "O _ _ O O O O\nO O O O O O O\n    O O O\n    O O O", this.t1.toString());
  }
}