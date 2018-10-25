package com.kamangir.arash.java.tailer;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.io.input.TailerListenerAdapter;

/**
 * Runns a given Runnable if the given Predicate is satisfied.
 *
 * @author AShahi
 *
 */
public class ParameterizedTailerListener extends TailerListenerAdapter {

   private Predicate<String> stringWatcher;

   private Consumer<String> satisfiedConsumer;

   /**
    * If a read line contains the expression, stop the tailer.
    */
   @Override
   public void handle(final String line) {
      if (stringWatcher.test(line)) {
         satisfiedConsumer.accept(line);
      }
      else {
         System.out.print(".");
      }
   }

   public Predicate<String> getStringWatcher() {
      return stringWatcher;
   }

   public void setStringWatcher(final Predicate<String> stringWatcher) {
      this.stringWatcher = stringWatcher;
   }

   public Consumer<String> getSatisfiedConsumer() {
      return satisfiedConsumer;
   }

   public void setSatisfiedConsumer(final Consumer<String> satisfiedConsumer) {
      this.satisfiedConsumer = satisfiedConsumer;
   }

}
