package parser;
import java.util.Stack;
  @SuppressWarnings("rawtypes")

public class Parser {

   private ITable tbl;
   private IGrammar g;

   public Parser(Common c) {
      this.tbl = c.tbl;
      this.g   = c.g;
   }

   // Define your parse table as follows
  public static interface ITable {
      // given a pair (a,t) returns an action (see below)
      public ActionTo getState(int state, Enum tok);
      // given a pair (a,nont) returns a goto (see below)
      public int _goto(int state, Enum nonterm);
      // returns last state number in table
      public int lastState();
   }
   
   // Actions in parse table 
   public enum Action { 
      ERROR, SHIFT, REDUCE,  GOTO, ACC
   }   

   /* Action pair (a,t) which can be
    *     (shift,to) or (reduce,rule) or (goto,state) 
    */     
   public static class ActionTo {
      private Action action;
      private int ruleOrTo;
      public ActionTo(Action action, int ruleOrTo) {
         assert action.ordinal() >= Action.ERROR.ordinal() && 
                action.ordinal() <= Action.ACC.ordinal();
         this.action = action;
         this.ruleOrTo = ruleOrTo;
      }
      public Action kindOf() { return this.action; }
      public int    to()     { return this.ruleOrTo; }
   }
   
   // Define your grammar as follows
   public static interface IGrammar {
      public Enum lhs(int ruleNo);
      public int noSymRHS(int ruleNo);
   }
   
   
   public String parse(String toParse) {  
       class Pair {
         Enum noSymbolOrRule;
         int state;
         Pair(Enum noSymbolOrRule, int state) {
            this.noSymbolOrRule = noSymbolOrRule;
            this.state = state;
         }
         public int getState() { return state; }
         public String toString() {
            return "(" + noSymbolOrRule + "," + state + ")";
         }
      }
      // Prepare scanner
      Scanner scan = new Scanner(toParse); 
      // Stack for automata
      Stack<Pair> stk = new Stack<Pair>();
      int currentState = 0; // 
      stk.push(new Pair(Common.Token.EOF,0));                                               int step = 1;
      while (true) {
         Common.Token tok = scan.currentToken();
         ActionTo action = tbl.getState(currentState, tok);                                 System.out.print(step + ":" + " State:" + currentState  + ", Stack:" + stk + ", token: " + tok + ", Action: "); 
         switch (action.kindOf()) {
            case SHIFT : 
               currentState = action.to();                                                  System.out.println("s" + action.to());
               stk.push(new Pair(tok, currentState)); 
               scan.adv();
               break;
            case REDUCE: 
               /* According to the stack top pop as many symbols as the rule there */       System.out.println("r" + action.to());
               int m = g.noSymRHS(action.to());                                             System.out.print("Pop " + m + " symbols (rule " + action.to() +"). ");  
               for (int i = 1; i <= m; i++) 
                  if (!stk.isEmpty()) stk.pop(); 
                  else return "Error popping stack"; 
               /* Push nonterminal */                                                       System.out.println("Stack: " + stk);
               Enum nonterm = g.lhs(action.to());                                           System.out.print("Push nonterminal. ");
               stk.push(new Pair(nonterm, tbl._goto(stk.peek().getState(), nonterm)));      System.out.println("Stack: " + stk);
               // Read goto state from the new stack top
               currentState = stk.peek().getState(); 
               break;
            case ACC   : return "Accept";
            case ERROR : return "Fail";
            default:
               break;
         }                                                                                  step++;
      }    
      //return "Unexpected end of file";
   }

}
