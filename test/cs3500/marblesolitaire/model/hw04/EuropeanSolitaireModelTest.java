package cs3500.marblesolitaire.model.hw04;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

import static org.junit.Assert.*;

/**
 * Class representing the tests for the {@code EuropeanSolitaireModel} class and methods.
 */
public class EuropeanSolitaireModelTest {
  EuropeanSolitaireModel e1;
  EuropeanSolitaireModel e2;
  EuropeanSolitaireModel e3;
  EuropeanSolitaireModel e4;

  @Before
  public void init() {
    this.e1 = new EuropeanSolitaireModel();
    this.e2 = new EuropeanSolitaireModel(2, 2);
    this.e3 = new EuropeanSolitaireModel(5);
    this.e4 = new EuropeanSolitaireModel(5, 8, 8);
  }

  @Test
  public void testValidInit() {
    // We can test that this is a valid initialization by checking the size of the board,
    // the empty space, and the number of marbles on the board (the score).
    this.e1 = new EuropeanSolitaireModel();
    testInitHelper(this.e1, 7, 36, 3, 3);
    this.e2 = new EuropeanSolitaireModel(2, 2);
    testInitHelper(this.e2, 7, 36, 2, 2);
    this.e3 = new EuropeanSolitaireModel(5);
    testInitHelper(this.e3, 13, 128, 6, 6);
    this.e4 = new EuropeanSolitaireModel(5, 8, 8);
    testInitHelper(this.e4, 13, 128, 8, 8);

    // Bottlenose goes crazy if you don't have at least one explicit assertEquals in a test...
    assertEquals(36, this.e1.getScore());
  }

  @Test
  public void testInvalidInitSecondConstructor() {
    try {
      this.e2 = new EuropeanSolitaireModel(0, 0);
      fail("Second Constructor Created EuropeanSolitaireModel with invalid cell position");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position (0,0) is invalid", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitThirdConstructor() {
    try {
      this.e3 = new EuropeanSolitaireModel(2);
      fail("Third Constructor created EuropeanSolitaireModel with even number");
    } catch (IllegalArgumentException e) {
      assertEquals("Side length must be an odd number >= 3", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitFourthConstructorEven() {
    try {
      this.e4 = new EuropeanSolitaireModel(2, 3, 3);
      fail("Fourth Constructor created EuropeanSolitaireModel with even number");
    } catch (IllegalArgumentException e) {
      assertEquals("Side length must be an odd number >= 3", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitFourthConstructor() {
    try {
      this.e4 = new EuropeanSolitaireModel(5, 0, 0);
      fail("Fourth Constructor created EuropeanSolitaireModel with invalid cell position");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position (0,0) is invalid", e.getMessage());
    }
  }

  @Test
  public void moveHorizontalLtoR() {
    this.e1.move(3, 1, 3, 3);
    assertEquals(SlotState.Empty, this.e1.getSlotAt(3, 1));
    assertEquals(SlotState.Empty, this.e1.getSlotAt(3, 2));
    assertEquals(SlotState.Marble, this.e1.getSlotAt(3, 3));
    assertEquals(35, this.e1.getScore());
  }

  @Test
  public void moveHorizontalRtoL() {
    this.e2.move(2, 4, 2, 2);
    assertEquals(SlotState.Empty, this.e2.getSlotAt(2, 4));
    assertEquals(SlotState.Empty, this.e2.getSlotAt(2, 3));
    assertEquals(SlotState.Marble, this.e2.getSlotAt(2, 2));
    assertEquals(35, this.e2.getScore());
  }

  @Test
  public void moveVerticalUtoD() {
    this.e1.move(1, 3, 3, 3);
    assertEquals(SlotState.Empty, this.e1.getSlotAt(1, 3));
    assertEquals(SlotState.Empty, this.e1.getSlotAt(2, 3));
    assertEquals(SlotState.Marble, this.e1.getSlotAt(3, 3));
    assertEquals(35, this.e1.getScore());
  }

  @Test
  public void moveVerticalDtoU() {
    this.e2.move(4, 2, 2, 2);
    assertEquals(SlotState.Empty, this.e2.getSlotAt(4, 2));
    assertEquals(SlotState.Empty, this.e2.getSlotAt(3, 2));
    assertEquals(SlotState.Marble, this.e2.getSlotAt(2, 2));
    assertEquals(35, this.e2.getScore());
  }

  @Test
  public void invalidMoveTooFar() {
    try {
      this.e1.move(3, 0, 3, 3);
      fail("EuropeanSolitaireModel successfully moved marble two spaces.");
    } catch (IllegalArgumentException e) {
      assertEquals("Move must only cross 2 slots", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDNEFrom() {
    try {
      this.e1.move(30, 2, 3, 3);
      fail("EuropeanSolitaireModel successfully moved from nonexistent slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is not on the board", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDNETo() {
    try {
      this.e1.move(3, 1, 13, 3);
      fail("EuropeanSolitaireModel successfully moved to nonexistent slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is not on the board", e.getMessage());
    }
  }

  @Test
  public void invalidMoveFrom() {
    try {
      this.e1.move(3, 3, 1, 3);
      fail("EuropeanSolitaireModel successfully moved empty to marble slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot move from Empty -> Marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveTo() {
    try {
      this.e1.move(3, 0, 3, 2);
      fail("EuropeanSolitaireModel successfully moved marble to marble slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot move from Marble -> Marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveMiddle() {
    try {
      this.e1.move(3, 1, 3, 3);
      this.e1.move(3, 0, 3, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Middle spot must contain a marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDiagonal() {
    try {
      this.e1.move(2, 2, 3, 3);
      fail("EuropeanSolitaireModel successfully moved diagonally");
    } catch (IllegalArgumentException e) {
      assertEquals("Move cannot be diagonal", e.getMessage());
    }
  }

// FIXME: brokie

//  @Test
//  public void isGameOver() {
//    int moves[] = {5, 3, 3, 3};
//    this.e1.move(5, 3, 3, 3);
//    this.e1.move(2, 3, 4, 3);
//    this.e1.move(0, 3, 2, 3);
//    this.e1.move(3, 5, 3, 3);
//    this.e1.move(3, 2, 3, 4);
//    this.e1.move(3, 0, 3, 2);
//    MarbleSolitaireTextView view = new MarbleSolitaireTextView(this.e1);
//    System.out.println(view.toString());
//    assertEquals(true, this.e1.isGameOver());
//    assertEquals(false, this.e2.isGameOver());
//  }

  @Test
  public void getBoardSize() {
    assertEquals(7, this.e1.getBoardSize());
    Random r = new Random();
    for (int i = 0; i < 1000; i++) {
      int arm = r.nextInt(97) + 3;
      if (arm % 2 == 0) {
        arm++;
      }
      EuropeanSolitaireModel e = new EuropeanSolitaireModel(arm);
      assertEquals(3 * arm - 2, e.getBoardSize());
    }
  }

  @Test
  public void getSlotAt() {
    // Let's just check all the marbles in e1
    for (int i = 2; i < 5; i++) {
      for (int j = 0; j < 7; j++) {
        if (i == 3 && j == 3) {
          assertEquals(SlotState.Empty, this.e1.getSlotAt(i, j));
          continue;
        }
        assertEquals(SlotState.Marble, this.e1.getSlotAt(i, j));
        assertEquals(SlotState.Marble, this.e1.getSlotAt(j, i));
      }
    }
  }

  @Test
  public void getSlotAtInf() {
    try {
      this.e1.getSlotAt(1000, 1000);
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is not on the board", e.getMessage());
    }
  }

  @Test
  public void getScore() {
    // the score should go down by one each time
    assertEquals(36, this.e1.getScore());
    this.e1.move(5, 3, 3, 3);
    assertEquals(35, this.e1.getScore());
    this.e1.move(2, 3, 4, 3);
    assertEquals(34, this.e1.getScore());
  }

  /**
   * This will run all the assertEquals that are mentioned in the testValidInit method.
   *
   * @param e         the given {@code EuropeanSolitaireModel} we want to test
   * @param boardSize The expected board size of e
   * @param marbles   the expected number of marbles in e
   * @param empRow    the expected row that the empty slot is in.
   * @param empCol    the expected column that the empty slot is in.
   */
  public void testInitHelper(EuropeanSolitaireModel e, int boardSize,
                             int marbles, int empRow, int empCol) {
    assertEquals(boardSize, e.getBoardSize());
    assertEquals(marbles, e.getScore());
    assertEquals(SlotState.Empty, e.getSlotAt(empRow, empCol));
  }
}