package tech.lacambra.utils.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

class CLITest {

  private CLI cut;

  @BeforeEach
  public void before() {
    cut = new CLI();
  }

  @Test
  void execute() {

    AtomicInteger counter = new AtomicInteger();

    Consumer<String> stdOut = line -> {
      counter.incrementAndGet();
    };

    int exit = cut.execute("pwd", stdOut, stdOut).join().exitValue();
    Assertions.assertEquals(0, exit);
    Assertions.assertEquals(1, counter.get());
  }


}