package parser;

import parser.Parser.*;
import parser.Common.*;

public class DemoLR0 {

  public static void main(String[] args) {
     Parser p = new Parser(new Common());
     for (String s: new String[] {"0 * 0 + 0 + 0 * 0", "0 "} ) {
        System.out.println("*********** Parse \"" + s + "\" *********");
        System.out.println(p.parse(s));
     }
  }
   
}
