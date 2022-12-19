package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt {
    ArrayList<AspName> names = new ArrayList<AspName>(); //formelle parametere
    AspSuite suite = null;

    AspFuncDef(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspFuncDef parse(Scanner s){
        enterParser("Func def");
        AspFuncDef a = new AspFuncDef(s.curLineNum());

        skip(s, TokenKind.defToken);

        a.names.add(AspName.parse(s));

        skip(s, TokenKind.leftParToken);

        if(s.curToken().kind == TokenKind.nameToken){
            a.names.add(AspName.parse(s));

            while(s.curToken().kind != TokenKind.rightParToken){
                skip(s, TokenKind.commaToken);
                a.names.add(AspName.parse(s));
            }
        }

        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);

        a.suite = AspSuite.parse(s);
       

        leaveParser("Func def");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("def ");

        names.get(0).prettyPrint();


        prettyWrite(" (");

        for (int i = 1; i < names.size(); i ++) {
            names.get(i).prettyPrint();
            if (i < names.size() ) {
                prettyWrite(", ");
            }
        }
        

        prettyWrite(")");

        prettyWrite(" : ");

        suite.prettyPrint();
    }

    public ArrayList<AspName> hentListe() {
        return names;
    }
    public AspSuite hentBodySuite() {
        return suite;
    }
    public String hentnavn(int i) {
        return names.get(i).ss;
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        
        RuntimeValue v = new RuntimeFunc(this, curScope, names.get(0).ss);

        curScope.assign(names.get(0).ss, v);
        
        trace("def " + names.get(0).ss);
        //suites evalueres i runtime func greier
        return v;
    }
}
