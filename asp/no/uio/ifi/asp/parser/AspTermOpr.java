package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax{
    String tk = null;
    TokenKind k = null;
    AspTermOpr(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("Term Opr");
        AspTermOpr a = new AspTermOpr(s.curLineNum());

         
        a.k = s.curToken().kind;
        a.tk = s.curToken().name;
        skip(s, s.curToken().kind);
        

        leaveParser("Term Opr");
        return a;
    }

    @Override
    void prettyPrint() {

        prettyWrite(tk);
        
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // brukes aldri
        return null;
    }
}
