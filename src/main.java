import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.SolitaireBuilder;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

public class main {
  public static void main(String args[]) {
    /*
    args[0] = name of the game
    args[1] = either the size of the game or the coordinate of the start cell
    if args[1] == "size":
      args[2] = the size of the board
    else if args[1] == "hole"
      args[2] = the row of the start position
      args[2] = the column of the start position

    ASK: do we allow both at the same time? (e.g., english size 5 hole 4 4)
     */
    //We can just handle the input in a set of switch statements, ASSUME
    SolitaireBuilder builder = null;
    int argSize = args.length;

    switch(args[0]) {
      case "english":
        builder = new EnglishSolitaireModel.EnglishModelBuilder();
        break;
      case "european":
        builder = new EuropeanSolitaireModel.EuropeanModelBuilder();
        break;
      case "triangular":
        builder = new TriangleSolitaireModel.TriangleModelBuilder();
        break;
      default:
        // fail case: no need for now
        break;
    }

    if (argSize > 1) {
      switch (args[1]) {
        case "-size":
          builder.setSize(Integer.parseInt(args[2]));
          break;
        case "-hole":
          builder.setHole(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
          break;
        default:
          // fail case: no need for now
          break;
      }
    }

    MarbleSolitaireModel model = builder.build();
    MarbleSolitaireView view;
    if (args[0].equals("triangular")) {
      view = new TriangleSolitaireTextView(model);
    } else {
      view = new MarbleSolitaireTextView(model);
    }
    Readable read = new InputStreamReader(System.in);
    MarbleSolitaireController cont = new MarbleSolitaireControllerImpl(model, view, read);
    cont.playGame();
  }
}
