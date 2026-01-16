import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

import org.howard.edu.lsp.assignment1.HelloWorld;

/**
 * A simple CLI router
 */
public class Router {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("Enter an assignment number and its arguments (if applicable)");
      System.out.print("> ");
      Scanner in = new Scanner(System.in);
      String line = in.nextLine().trim();
      if (line.isEmpty()) {
        args = new String[0];
      } else {
        args = line.split("\\s+");
      }
      in.close();
    }

    if (args.length == 0) {
      // No error message because this only happens if the user entered nothing.
      return;
    }

    String choice = args[0];
    String[] remainingArgs = Arrays.copyOfRange(args, 1, args.length);

    switch (choice) {
      case "1":
        HelloWorld.main(remainingArgs);
        break;
      default:
        System.out.println("Invalid choice.");
    }
  }
}
