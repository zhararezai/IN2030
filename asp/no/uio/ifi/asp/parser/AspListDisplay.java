package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("listDisplay");

        AspListDisplay a = new AspListDisplay(s.curLineNum());
        skip(s, TokenKind.leftBracketToken);

        boolean f = false; //ferdig

        while(!f){
            if(s.curToken().kind != TokenKind.rightBracketToken){
                a.expr.add(AspExpr.parse(s));

                if(s.curToken().kind == TokenKind.commaToken){
                    skip(s, TokenKind.commaToken);
                    if (s.curToken().kind == TokenKind.rightBracketToken) {
                        parserError("Expected an an expressin atom after comma in list, but found " + s.curToken().kind + "!", s.curLineNum());
                    }
                } else{
                    f = true;
                } 
            } else {
                f = true;
            }
        }
        skip(s, TokenKind.rightBracketToken);
        leaveParser("listDisplay");
        return a;
    }

    @Override
    void prettyPrint(){
        prettyWrite("[");

        for(int i = 0; i < expr.size(); i++){
            expr.get(i).prettyPrint();
            if(i < expr.size() - 1){
                prettyWrite(", ");
            }
        }

        prettyWrite("]");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> listen = new ArrayList<RuntimeValue>();
        for(AspExpr e : expr){
            listen.add(e.eval(curScope));
        }
     
        return new RuntimeListValue(listen);
    }
}