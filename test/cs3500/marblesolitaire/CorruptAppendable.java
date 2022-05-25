package cs3500.marblesolitaire;

import java.io.IOException;

/**
 * Class created solely to crash, for testing {@code Appendable} IOExceptions.
 */
public class CorruptAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Did not append!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Did not append!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Did not append!");
  }
}
