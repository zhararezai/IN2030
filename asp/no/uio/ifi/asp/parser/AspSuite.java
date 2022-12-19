package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    AspSmallStmtList sl = null;
    ArrayList<AspStmt> stmts = new ArrayList<>();
    boolean ersmallstmtlist = false;

    AspSuite(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspSuite parse(Scanner s) {
        enterParser("Suite");
        AspSuite a = new AspSuite(s.curLineNum());

        
        if(s.curToken().kind == TokenKind.newLineToken){
            skip(s, TokenKind.newLineToken);
            skip(s, TokenKind.indentToken);


            while(s.curToken().kind != TokenKind.dedentToken){
                
                a.stmts.add(AspStmt.parse(s));
            }
            
            skip(s, TokenKind.dedentToken);



        } else{
            a.ersmallstmtlist = true;
            a.sl = AspSmallStmtList.parse(s);
        }
       

        leaveParser("Suite");
        return a;
    }

    @Override
    void prettyPrint() {

        if (ersmallstmtlist) {
            prettyWriteLn(); //newline
            
            prettyIndent();
            sl.prettyPrint();
            prettyDedent();
            

        } else { //det andre
            prettyWriteLn(); //newline
            prettyIndent();

            for (int i = 0; i < stmts.size(); i++) {
                
                stmts.get(i).prettyPrint();
            }
            
            prettyDedent();
        }  
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if(sl != null){
            RuntimeValue v = sl.eval(curScope);
        } else {
            for(AspStmt stmt : stmts){
                stmt.eval(curScope);
            }
        }
        return null;
    }
}
