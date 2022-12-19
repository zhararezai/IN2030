package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax {
    String tk = null;
    TokenKind k = null;

    AspFactorPrefix(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspFactorPrefix parse(Scanner s) {
        enterParser("FactorPrefix");
        AspFactorPrefix a = new AspFactorPrefix(s.curLineNum());


        a.k = s.curToken().kind;
        a.tk = s.curToken().name;
        skip(s, s.curToken().kind);
        

        leaveParser("FactorPrefix");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite(tk + " ");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //brukes aldri
        return null;
    }
}
