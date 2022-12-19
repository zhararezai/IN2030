package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIntegerLiteral extends AspAtom{
    long i;

    AspIntegerLiteral(int n) {
        super(n);
        i = 0;
    }

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("Integer Literal");
        AspIntegerLiteral a = new AspIntegerLiteral(s.curLineNum());
        
        a.i = s.curToken().integerLit;
        
        skip(s, TokenKind.integerToken);
        
        
        leaveParser("Integer Literal");

        return a;
    }

    @Override
    void prettyPrint() {
        
        prettyWrite(String.valueOf(i));
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //riktig
        return new RuntimeIntValue(i);
    }
    
    
}
