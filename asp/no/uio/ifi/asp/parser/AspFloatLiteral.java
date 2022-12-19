package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFloatLiteral extends AspAtom{
    double d;
    

    AspFloatLiteral(int n) {
        super(n);
        d = 0;
        
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("Float Literal");
        AspFloatLiteral a = new AspFloatLiteral(s.curLineNum());

        a.d = s.curToken().floatLit;
        skip(s, TokenKind.floatToken);
    
        leaveParser("Float Literal");

        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite(String.valueOf(d));
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //riktig
        return new RuntimeFloatValue(d);
    }
    
}
