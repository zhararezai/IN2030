package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt{

    AspExpr expr = null;
    AspSuite suite = null;

    AspWhileStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("While Statement");
        AspWhileStmt a = new AspWhileStmt(s.curLineNum());

        skip(s, TokenKind.whileToken);

        a.expr = AspExpr.parse(s);

        skip(s, TokenKind.colonToken);

        a.suite = AspSuite.parse(s);


        leaveParser("While Statement");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("while ");

        expr.prettyPrint();

        prettyWrite(": ");

        suite.prettyPrint();
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // fra forelesning 5.11.2021
        
        while (true) {
            RuntimeValue t = expr.eval(curScope);

            if (!t.getBoolValue("while loop test", this)) break;

            trace("while True: ...");
            suite.eval(curScope);
        }
        trace("while False:");
        return null;
    }

    /*While stmt:
        beregn utrykket med eval, og bruk getboolvalue() for å skjekke for funkjson.
        Om utrykket/expr er usann/false, skal vi ikke gjøre noe(ikke kjøre det i while løkken) aka. break
        Om true:
        Evalurer suite.
        Så starter vi på nytt. Fra toppen. 
        
        Ulovlig t er funksjon, som vi sjekker med get bool value.
        */
    
}
