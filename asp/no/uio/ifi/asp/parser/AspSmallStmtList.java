package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspSmallStmtList extends AspStmt{
    ArrayList<AspSmallStmt> stmt = new ArrayList<AspSmallStmt>();
    boolean semicolonpaaslutten = true;

    AspSmallStmtList(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspSmallStmtList parse(Scanner s) {

        enterParser("Small Statement List");
        AspSmallStmtList a = new AspSmallStmtList(s.curLineNum());

        a.stmt.add(AspSmallStmt.parse(s));
        
        //skal være semicolon eller newtoken her.
        
        while(s.curToken().kind == semicolonToken){
            skip(s, semicolonToken);
            
            
            if(s.curToken().kind == newLineToken){
                
                a.semicolonpaaslutten = false;
                break;
            }
            a.stmt.add(AspSmallStmt.parse(s));;
            
        }

        
        skip(s, newLineToken);
        

        leaveParser("small statement list");
        return a;


    }

    @Override
    void prettyPrint() {

        stmt.remove(0).prettyPrint();
        
        while(!stmt.isEmpty()) {
            prettyWrite("; ");
            stmt.remove(0).prettyPrint();

        }

        if (semicolonpaaslutten) {
            prettyWrite("; ");
        }
        prettyWriteLn();
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        
        for ( AspSmallStmt sstmt : stmt) {
            sstmt.eval(curScope);
        }
        return null;
    }
    
}
