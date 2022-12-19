package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspNotTest extends AspSyntax{
    boolean not = false;
    ArrayList<AspComparison> c = new ArrayList<AspComparison>();
    

    AspNotTest(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspNotTest parse(Scanner s) {
        enterParser("Not Test");
        AspNotTest a = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == TokenKind.notToken) {
            a.not = true;
            skip(s, notToken);
        }
        
        a.c.add(AspComparison.parse(s));
       

        leaveParser("Not Test");
        return a;
    }

    @Override
    void prettyPrint() {
        
        if (not) {
            prettyWrite("not ");
        }
        c.get(0).prettyPrint();
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //gitt i forelsening
        RuntimeValue v = c.get(0).eval(curScope);
        if (not) {
            v = v.evalNot(this);
        }
        return v;
    }
    
}
