package tech.lacambra.utils.cli;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class CLI {

  private static final Logger LOGGER = Logger.getLogger(CLI.class.getName());

  public CompletableFuture<Process> execute(String command, Consumer<String> stdOutConsumer, Consumer<String> errConsumer) {

    Process p;

    try {
      p = Runtime.getRuntime().exec(command);
      run(p::getInputStream, stdOutConsumer);
      run(p::getErrorStream, errConsumer);
      return p.onExit();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void run(Supplier<InputStream> streamSupplier, Consumer<String> streamConsumer) {
    try (InputStream stream = streamSupplier.get()) {
      StringBuilder currentLine = new StringBuilder();
      int nextChar;
      while ((nextChar = stream.read()) != -1) {
        if (nextChar == '\n') {
          streamConsumer.accept(currentLine.toString());
          continue;
        }
        currentLine.append((char) nextChar);
        System.out.println((char) nextChar);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}