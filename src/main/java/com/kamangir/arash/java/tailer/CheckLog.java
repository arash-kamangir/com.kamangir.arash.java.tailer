package com.kamangir.arash.java.tailer;

import java.io.File;
import java.util.concurrent.Executor;
import org.apache.commons.io.input.Tailer;

/**
 * Checks a log file for occurance of String.
 *
 * @author AShahi
 *
 */
public class CheckLog {

   /**
    * @param args:
    *           <ol>
    *           <li><FILEPATH></li>
    *           <li><EXPRESSION></li>
    *           <li><DELAY> (optional></li>
    *           </ol>
    */
   public static void main(final String args[]) {
      final String filepath = args[0];
      final String expression = args[1];
      final long delayMillis = Long.valueOf(args[2]);

      System.out.println("Monitoring file '" + filepath + "' for occurance of the given expression ...");

      final ParameterizedTailerListener stopperListener = new ParameterizedTailerListener();
      final Tailer tailer = new Tailer(new File(filepath), stopperListener, delayMillis);
      stopperListener.setStringWatcher(line -> line.contains(expression));
      stopperListener.setSatisfiedConsumer(line -> lookupSuccessful(tailer, line, expression));

      DirectExecutor.instance.execute(tailer);
   }

   private static void lookupSuccessful(final Tailer tailer, final String line, final String expression) {
      tailer.stop();
      System.out.println();
      System.out.println(line);
   }

   /**
    * Executes the runnable directly.
    */
   static class DirectExecutor implements Executor {

      static final DirectExecutor instance = new DirectExecutor();

      public void execute(final Runnable r) {
         r.run();
      }
   }
}
