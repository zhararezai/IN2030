package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspBooleanLiteral extends AspAtom {
    boolean bolean;
    
    AspBooleanLiteral(int n) {
        super(n);
        bolean = false;
        
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral a = new AspBooleanLiteral(s.curLineNum());

        

        //Jernbanediagram boolean
        if (s.curToken().kind == TokenKind.trueToken) {
            a.bolean = true;
        }

        skip(s, s.curToken().kind); 

        leaveParser("boolean literal");

        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite(String.valueOf(bolean));
        
    }
    
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        
        // gitt i forelesning
        return new RuntimeBoolValue(bolean);
    }
    
    
}
