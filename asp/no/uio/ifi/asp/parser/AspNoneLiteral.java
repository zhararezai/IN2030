package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNoneLiteral extends AspAtom{

    AspNoneLiteral(int n) {
        super(n);
        
    }

    static AspNoneLiteral parse(Scanner s) {
        enterParser("None Literal");
        AspNoneLiteral a = new AspNoneLiteral(s.curLineNum());

        skip(s, TokenKind.noneToken);

        leaveParser("None Literal");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("None");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //riktig 
        trace("None");
        return new RuntimeNoneValue();
    }
    
}
