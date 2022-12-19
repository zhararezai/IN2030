package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax{

    AspStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspStmt parse(Scanner s) {
        enterParser("Stmt");
        AspStmt a = null;
        
        if (s.curToken().kind == TokenKind.forToken ||
            s.curToken().kind == TokenKind.ifToken ||
            s.curToken().kind == TokenKind.whileToken ||
            s.curToken().kind == TokenKind.defToken) {

            a = AspCompoundStmt.parse(s); 
        } else {
            a = AspSmallStmtList.parse(s);
        }
        leaveParser("Stmt");
        return a;    
    }
    
}
