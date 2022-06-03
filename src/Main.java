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

/**
 * Main class for running the program.
 */
public class Main {
  /**
   * main method that runs at the beginning of the program.
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    //We can just handle the input in a set of switch statements, ASSUME
    SolitaireBuilder builder = null;
    int argSize = args.length;

    switch (args[0]) {
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
        System.out.println("Incorrect input");
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
          System.out.println("Incorrect input");
          break;
      }
    }

    // We are assuming that the builder has some value, and there were no incorrect inputs
    // If we needed to, we could add a try-catch loop
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
