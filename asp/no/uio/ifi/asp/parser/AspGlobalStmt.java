package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspGlobalStmt extends AspSmallStmt{
    ArrayList<AspName> names = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("Global Statement");

        AspGlobalStmt a = new AspGlobalStmt(s.curLineNum());

        skip(s, globalToken);
        a.names.add(AspName.parse(s));
        while(s.curToken().kind == commaToken) {
            skip(s, commaToken);
            a.names.add(AspName.parse(s));
        }

        leaveParser("Global Statement");

        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("global ");
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // legge til i det gobale skopet
        for (AspName a: names) {
            a.eval(curScope);
            curScope.registerGlobalName(a.ss);
            //trace("global " + a.ss);
        }
        
        return null; //vi oppdaterer scopet saa trenger ikke returnere noe
    }
}
