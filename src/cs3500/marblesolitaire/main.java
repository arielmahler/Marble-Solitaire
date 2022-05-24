package cs3500.marblesolitaire;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

public class main {
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);
    MarbleSolitaireModel game = new EnglishSolitaireModel();
    MarbleSolitaireView view = new MarbleSolitaireTextView(game);
    MarbleSolitaireController cont = new MarbleSolitaireControllerImpl(game, view, in);
    cont.playGame();
  }
}
