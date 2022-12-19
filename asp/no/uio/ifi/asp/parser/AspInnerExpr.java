package no.uio.ifi.asp.parser;



import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

class AspInnerExpr extends AspAtom{

    AspExpr expr = null;

    AspInnerExpr(int n){
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        enterParser("Inner Expr");

        AspInnerExpr a = new AspInnerExpr(s.curLineNum());
        skip(s, TokenKind.leftParToken);

        a.expr = AspExpr.parse(s);

        
        skip(s, TokenKind.rightParToken);
        
        
        leaveParser("Inner Expr");
        return a;
    }

    @Override
    void prettyPrint(){
        prettyWrite("(");
        
        expr.prettyPrint();
        prettyWrite(")");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // riktig
        RuntimeValue v = expr.eval(curScope);
        return v;
    }
}