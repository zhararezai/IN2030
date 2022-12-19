package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom{
    String ss;

    AspStringLiteral(int n) {
        super(n);
        
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("String Literal");
        AspStringLiteral a = new AspStringLiteral(s.curLineNum());

        a.ss = s.curToken().stringLit;
        
        skip(s, TokenKind.stringToken);

        leaveParser("String Literal");
        return a;
    }

    @Override
    void prettyPrint() {
        
        prettyWrite('"'+ss+'"');
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // riktig
        
        return new RuntimeStringValue(ss);
    }
    
}