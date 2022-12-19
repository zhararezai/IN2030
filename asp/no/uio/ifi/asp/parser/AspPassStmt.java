package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPassStmt extends AspSmallStmt{
    

    AspPassStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspPassStmt parse(Scanner s) {
        enterParser("Pass Statement");

        AspPassStmt a = new AspPassStmt(s.curLineNum());

        skip(s, passToken);
       

        leaveParser("Pass Statement");

        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("pass ");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // fra forlesning 5.11.2021
        trace("pass");
        return null;
    }
}
