package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> lt = new ArrayList<AspStringLiteral>();
    ArrayList<AspExpr> et = new ArrayList<AspExpr>();

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {
        enterParser("Dict display");
        AspDictDisplay a = new AspDictDisplay(s.curLineNum());

        boolean f = false;

        //jernbanediagram dict display
        skip(s, TokenKind.leftBraceToken);

        while(s.curToken().kind != TokenKind.rightBracketToken) {
            /*if(s.curToken().kind != TokenKind.stringToken){
                parserError("Expected String literal for key in dict, but found " + s.curToken().kind + "!", s.curLineNum());
            }*/
            a.lt.add(AspStringLiteral.parse(s));
            skip(s, TokenKind.colonToken);
            a.et.add(AspExpr.parse(s));

            if (s.curToken().kind != TokenKind.commaToken)
                break;

            skip(s, commaToken);
            /*
            while(s.curToken().kind == TokenKind.commaToken) {
                skip(s, TokenKind.commaToken);
                if(s.curToken().kind != TokenKind.stringToken){
                    parserError("Expected String literal for key in dict, but found " + s.curToken().kind + "!", s.curLineNum());
                }


                a.lt.add(AspStringLiteral.parse(s));
                skip(s, TokenKind.colonToken);
                a.et.add(AspExpr.parse(s));
            }*/
        }    

        skip(s, TokenKind.rightBraceToken); 

        leaveParser("Dict display");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("{");
        /*while (!lt.isEmpty()) {
            lt.remove(0).prettyPrint();
            prettyWrite(": ");
            et.remove(0);

            if (lt.size() - 1 != 0)
                prettyWrite(", ");
        }*/

        for(int i = 0; i < lt.size(); i++){
            if(i != 0){
                prettyWrite(", ");
            }

            lt.get(i).prettyPrint();
            prettyWrite(" : ");
            et.get(i).prettyPrint();
        }
        prettyWrite("}");
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<String, RuntimeValue> mappen = new HashMap<>();
        
        for(int i = 0; i < lt.size(); i++){
            mappen.put(lt.get(i).ss, et.get(i).eval(curScope));
        }
     
        return new RuntimeDictionaryValue(mappen);  
    }
}
