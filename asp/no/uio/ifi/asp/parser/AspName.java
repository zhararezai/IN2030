package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*; 

public class AspName extends AspAtom{
    String ss;

    AspName(int n) {
        super(n);
        
    }

    static AspName parse(Scanner s) {
        enterParser("Name");
        AspName a = new AspName(s.curLineNum());

        a.ss = s.curToken().name;
        skip(s, TokenKind.nameToken);

        leaveParser("Name");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite(ss);
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // fra forelesning 5.11.2021
        return curScope.find(ss, this);
    }

    public String toString() {
        return ss;
    }
    
}
