import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

public class main {
  public static void main(String args[]) {
    MarbleSolitaireModel model = new TriangleSolitaireModel(3);
    MarbleSolitaireView view = new TriangleSolitaireTextView(model);
    Readable read = new InputStreamReader(System.in);
    MarbleSolitaireController cont = new MarbleSolitaireControllerImpl(model, view, read);
    cont.playGame();
  }
}
