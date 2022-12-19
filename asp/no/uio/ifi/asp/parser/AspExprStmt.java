package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspExprStmt extends AspSmallStmt{
    AspExpr expr = null;

    AspExprStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspExprStmt parse(Scanner s) {
        enterParser("Expression Statement");

        AspExprStmt a = new AspExprStmt(s.curLineNum());

        a.expr = AspExpr.parse(s);

        leaveParser("Expression Statement");

        return a;
    }

    @Override
    void prettyPrint() {
        expr.prettyPrint();
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //fra forelesning 5.11.2021
        
        RuntimeValue v = expr.eval(curScope);
        trace(v.showInfo() );
        return null;

        
    }
    
}
