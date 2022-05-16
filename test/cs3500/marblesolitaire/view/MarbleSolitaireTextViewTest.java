package cs3500.marblesolitaire.view;

import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;

import static org.junit.Assert.*;

public class MarbleSolitaireTextViewTest {

  MarbleSolitaireTextView t1;
  MarbleSolitaireTextView t2;
  MarbleSolitaireTextView t3;

  @Before
  public void init() {
    this.t1 = new MarbleSolitaireTextView(new EnglishSolitaireModel());
    this.t2 = new MarbleSolitaireTextView(new EnglishSolitaireModel(2, 2));
    this.t3 = new MarbleSolitaireTextView((new EnglishSolitaireModel(5)));

  }

  @Test
  public void testToString() {
    System.out.print(this.t1.toString());
    System.out.print("hi");
  }
}