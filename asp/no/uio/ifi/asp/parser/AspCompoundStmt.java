package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspCompoundStmt extends AspStmt {

    AspCompoundStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspCompoundStmt parse(Scanner s){
        enterParser("Compound Statement");
        AspCompoundStmt a = null;

        switch (s.curToken().kind) {
        case forToken:
            a = AspForStmt.parse(s);
            break;
        case defToken:
            a = AspFuncDef.parse(s);
            break;
        case ifToken:
            a = AspIfStmt.parse(s);
            break;
        case whileToken:
            a = AspWhileStmt.parse(s);
            break;

        default:
        parserError("Expected an expression CompundStmt but found a " + s.curToken().kind + "!", s.curLineNum());
        
        
        }
        leaveParser("Compound Statement");
        return a;
    }
    
}
