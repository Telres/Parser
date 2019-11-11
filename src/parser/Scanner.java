package parser;
import java.util.StringTokenizer;

public class Scanner {
   private Common.Token currentToken,
                        nextToken;
   private StringTokenizer input;

   public Scanner(String inp) {
      if (inp == null) 
         throw new NullPointerException("Entrada inválida");
      input = new StringTokenizer(inp + " $");
      currentToken = Common.TOKEN_NO.get(input.nextToken()); 
      nextToken = Common.TOKEN_NO.get(
            input.hasMoreTokens() ? input.nextToken() : ""
                                     ); 
   }

   /** Advances to the next non whitespace on the tape. */
   public void adv() {
      assert input.hasMoreTokens(); 
      currentToken = nextToken; 
      nextToken = Common.TOKEN_NO.get(
            input.hasMoreTokens() ? input.nextToken() : ""
                                     ); 
   }

   /** Gets current token */
   public Common.Token currentToken() { return currentToken; }
   /** Gets next token */
   public Common.Token nextToken() { return nextToken;  }
   /** Does tape still has something on to read? */
   public boolean hasNext() { return !nextToken.equals(Common.Token.EMPTY); }

   /** Compares symbol with next char on tape */
   public boolean same(String symbol) {
      if (symbol.equals(currentToken)) {
         adv(); 
         return true;
      }
      return false;
   }
}
