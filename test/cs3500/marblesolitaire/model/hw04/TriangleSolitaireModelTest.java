package cs3500.marblesolitaire.model.hw04;

import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class representing the tests for the {@code TriangleSolitaireModel} class and methods.
 */
public class TriangleSolitaireModelTest {

  TriangleSolitaireModel e1;
  TriangleSolitaireModel e2;
  TriangleSolitaireModel e3;
  TriangleSolitaireModel e4;

  @Before
  public void init() {
    this.e1 = new TriangleSolitaireModel();
    this.e2 = new TriangleSolitaireModel(2, 2);
    this.e3 = new TriangleSolitaireModel(7);
    this.e4 = new TriangleSolitaireModel(7, 3, 1);
  }

  @Test
  public void testValidInit() {
    // We can test that this is a valid initialization by checking the size of the board,
    // the empty space, and the number of marbles on the board (the score).
    this.e1 = new TriangleSolitaireModel();
    testInitHelper(this.e1, 5, 14, 0, 0);
    this.e2 = new TriangleSolitaireModel(2, 2);
    testInitHelper(this.e2, 5, 14, 2, 2);
    this.e3 = new TriangleSolitaireModel(7);
    testInitHelper(this.e3, 7, 27, 0, 0);
    this.e4 = new TriangleSolitaireModel(7, 3, 1);
    testInitHelper(this.e4, 7, 27, 3, 1);

    // Bottlenose goes crazy if you don't have at least one explicit assertEquals in a test...
    assertEquals(14, this.e1.getScore());
  }

  @Test
  public void testInvalidInitSecondConstructor() {
    try {
      this.e2 = new TriangleSolitaireModel(3, 4);
      fail("Second Constructor Created TriangleSolitaireModel with invalid cell position");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position (3,4) is invalid", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitThirdConstructor() {
    try {
      this.e3 = new TriangleSolitaireModel(-1);
      fail("Third Constructor created TriangleSolitaireModel with negative number");
    } catch (IllegalArgumentException e) {
      assertEquals("dimensions must be > 2", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitFourthConstructorEven() {
    try {
      this.e4 = new TriangleSolitaireModel(0, 3, 3);
      fail("Fourth Constructor created TriangleSolitaireModel with negative number/zero");
    } catch (IllegalArgumentException e) {
      assertEquals("dimensions must be > 2", e.getMessage());
    }
  }

  @Test
  public void testInvalidInitFourthConstructor() {
    try {
      this.e4 = new TriangleSolitaireModel(5, 3, 4);
      fail("Fourth Constructor created EuropeanSolitaireModel with invalid cell position");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position (3,4) is invalid", e.getMessage());
    }
  }

  @Test
  public void moveDownRow() {
    this.e1.move(2, 0, 0, 0);
    assertEquals(SlotState.Empty, this.e1.getSlotAt(2, 0));
    assertEquals(SlotState.Empty, this.e1.getSlotAt(1, 0));
    assertEquals(SlotState.Marble, this.e1.getSlotAt(0, 0));
    assertEquals(13, this.e1.getScore());
  }

  @Test
  public void moveDownCol() {
    this.e4.move(3, 3, 3, 1);
    assertEquals(SlotState.Empty, this.e4.getSlotAt(3, 3));
    assertEquals(SlotState.Empty, this.e4.getSlotAt(3, 2));
    assertEquals(SlotState.Marble, this.e4.getSlotAt(3, 1));
    assertEquals(26, this.e4.getScore());
  }

  @Test
  public void moveUpRow() {
    this.e4.move(1, 1, 3, 1);
    assertEquals(SlotState.Empty, this.e4.getSlotAt(1, 1));
    assertEquals(SlotState.Empty, this.e4.getSlotAt(2, 1));
    assertEquals(SlotState.Marble, this.e4.getSlotAt(3, 1));
    assertEquals(26, this.e4.getScore());
  }

  @Test
  public void moveUpCol() {
    this.e2.move(2, 0, 2, 2);
    assertEquals(SlotState.Empty, this.e2.getSlotAt(2, 0));
    assertEquals(SlotState.Empty, this.e2.getSlotAt(2, 1));
    assertEquals(SlotState.Marble, this.e2.getSlotAt(2, 2));
    assertEquals(13, this.e2.getScore());
  }

  @Test
  public void moveDiagDown() {
    this.e1.move(2, 2, 0, 0);
    assertEquals(SlotState.Empty, this.e1.getSlotAt(2, 2));
    assertEquals(SlotState.Empty, this.e1.getSlotAt(1, 1));
    assertEquals(SlotState.Marble, this.e1.getSlotAt(0, 0));
    assertEquals(13, this.e1.getScore());
  }

  @Test
  public void moveDiagUp() {
    this.e2.move(0, 0, 2, 2);
    assertEquals(SlotState.Empty, this.e2.getSlotAt(0, 0));
    assertEquals(SlotState.Empty, this.e2.getSlotAt(1, 1));
    assertEquals(SlotState.Marble, this.e2.getSlotAt(2, 2));
    assertEquals(13, this.e2.getScore());
  }

  @Test
  public void invalidMoveTooFar() {
    try {
      this.e1.move(3, 0, 0, 0);
      fail("TriangleSolitaireModel successfully moved marble two spaces.");
    } catch (IllegalArgumentException e) {
      assertEquals("Move must only cross 2 slots", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDNEFrom() {
    try {
      this.e1.move(30, 2, 3, 3);
      fail("TriangleSolitaireModel successfully moved from nonexistent slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is not on the board", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDNETo() {
    try {
      this.e1.move(3, 1, 13, 3);
      fail("TriangleSolitaireModel successfully moved to nonexistent slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is not on the board", e.getMessage());
    }
  }

  @Test
  public void invalidMoveFrom() {
    try {
      this.e1.move(0, 0, 2, 0);
      fail("TriangleSolitaireModel successfully moved empty to marble slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot move from Empty -> Marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveTo() {
    try {
      this.e1.move(3, 0, 3, 2);
      fail("TriangleSolitaireModel successfully moved marble to marble slot");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot move from Marble -> Marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveMiddle() {
    try {
      this.e1.move(2, 0, 0, 0);
      this.e1.move(3, 0, 1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Middle spot must contain a marble", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDiagDownUp() {
    try {
      this.e2.move(4, 0, 2, 2);
      fail("TriangleSolitaireModel successfully moved across wrong diagonal");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move direction", e.getMessage());
    }
  }

  @Test
  public void invalidMoveDiagUpDown() {
    try {
      this.e4 = new TriangleSolitaireModel(7, 4, 0);
      this.e4.move(2, 2, 4, 0);
      fail("TriangleSolitaireModel successfully moved across wrong diagonal");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move direction", e.getMessage());
    }
  }



  @Test
  public void isGameOver() {
    this.e1 = new TriangleSolitaireModel(3);
    this.e1.move(2, 2, 0, 0);
    this.e1.move(2, 0, 2, 2);
    this.e1.move(0, 0, 2, 0);
    assertEquals(true, this.e1.isGameOver());
    assertEquals(false, this.e2.isGameOver());
  }

  @Test
  public void isGameOverDiagDown() {
    this.e1 = new TriangleSolitaireModel(3);
    this.e1.move(2, 0, 0, 0);
    this.e1.move(2, 2, 2, 0);
    assertEquals(false, this.e1.isGameOver());
  }

  @Test
  public void isGameOverDiagUp() {
    this.e1 = new TriangleSolitaireModel(3, 2, 0);
    this.e1.move(0, 0, 2, 0);
    assertEquals(false, this.e1.isGameOver());
  }

  @Test
  public void getBoardSize() {
    assertEquals(5, this.e1.getBoardSize());
    Random r = new Random();
    for (int i = 0; i < 1000; i++) {
      int arm = r.nextInt(97) + 3;
      if (arm % 2 == 0) {
        arm++;
      }
      TriangleSolitaireModel e = new TriangleSolitaireModel(arm);
      assertEquals(arm, e.getBoardSize());
    }
  }

  @Test
  public void getSlotAt() {
    // Let's just check all the marbles in e1
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (j > i) {
          assertEquals(SlotState.Invalid, this.e1.getSlotAt(i, j));
        } else if (i == 0) {
          assertEquals(SlotState.Empty, this.e1.getSlotAt(i, j));
        } else {
          assertEquals(SlotState.Marble, this.e1.getSlotAt(i, j));
        }
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
    assertEquals(14, this.e1.getScore());
    this.e1.move(2, 0, 0, 0);
    assertEquals(13, this.e1.getScore());
    this.e1.move(4, 0, 2, 0);
    assertEquals(12, this.e1.getScore());
  }

  /**
   * This will run all the assertEquals that are mentioned in the testValidInit method.
   *
   * @param e         the given {@code TriangleSolitaireModel} we want to test
   * @param boardSize The expected board size of e
   * @param marbles   the expected number of marbles in e
   * @param empRow    the expected row that the empty slot is in.
   * @param empCol    the expected column that the empty slot is in.
   */
  public void testInitHelper(TriangleSolitaireModel e, int boardSize,
                             int marbles, int empRow, int empCol) {
    assertEquals(boardSize, e.getBoardSize());
    assertEquals(marbles, e.getScore());
    assertEquals(SlotState.Empty, e.getSlotAt(empRow, empCol));
  }
}