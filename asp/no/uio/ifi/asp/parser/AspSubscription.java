package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSubscription extends AspPrimarySuffix{
    AspExpr expr = null;
    AspSubscription(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    public static AspSubscription parse (Scanner s) {
        
        enterParser("Subscription");

        AspSubscription a = new AspSubscription(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);

        a.expr = AspExpr.parse(s);
        
        skip(s, TokenKind.rightBracketToken);
        leaveParser("Subscription");
        return a;
    
    }

    @Override
    void prettyPrint() {
        prettyWrite("[");
        
        expr.prettyPrint();
        prettyWrite("]");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //riktig
        RuntimeValue v = expr.eval(curScope);
        return v;
    }
    
}
