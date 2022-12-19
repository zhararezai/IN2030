package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspReturnStmt extends AspSmallStmt{
    
    AspExpr expr = null;

    AspReturnStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("Return Statement");
        AspReturnStmt a = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        a.expr = AspExpr.parse(s);

        leaveParser("Return Statement");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("return ");
        expr.prettyPrint();
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //fra forelesning
        RuntimeValue v = expr.eval(curScope);
        trace("return " + v.showInfo());
        throw new RuntimeReturnValue(v, lineNum); 
        //return null; lov fordi returnvalue er en exeption??
    }
}
