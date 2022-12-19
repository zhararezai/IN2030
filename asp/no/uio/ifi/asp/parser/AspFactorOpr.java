package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax {
    TokenKind tk = null;

    AspFactorOpr(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspFactorOpr parse(Scanner s) {
        enterParser("FactorOpr");
        AspFactorOpr a = new AspFactorOpr(s.curLineNum());

        if (s.isFactorOpr()) {
            a.tk = s.curToken().kind;
            skip(s, a.tk);
        }

        leaveParser("FactorOpr");
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
