package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspCompOpr extends AspSyntax {
    TokenKind tk = null;
    

    AspCompOpr(int n) {
        super(n); 
        
        
    }

    static AspCompOpr parse(Scanner s) {
        enterParser("CompOpr");
        AspCompOpr a = new AspCompOpr(s.curLineNum());

        
        a.tk = s.curToken().kind;
        skip(s, a.tk);
        

        leaveParser("CompOpr");

        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite(tk.toString());
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // brukes aldri
        return null;
    }
    
}
