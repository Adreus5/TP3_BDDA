package test;

import java.util.Arrays;
import model.DataAccess;

/**
 *
 * @author Jean-Michel Busca
 */
public class Test {

  /**
   * @param args the command line arguments
   *
   * @throws java.lang.Exception
   */
  public static void main(String[] args) throws Exception {

    // work around Netbeans bug
    if (args.length == 2) {
      args = Arrays.copyOf(args, 3);
      args[2] = "";
    }

    // create a data access object
    DataAccess data = new DataAccess(args[0], args[1], args[2]);

    // access the database using high-level Java methods
    // ...
    // close the data access object when done
    // ...
  }
}
