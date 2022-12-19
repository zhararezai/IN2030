package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> expr = new ArrayList<AspExpr>();
    ArrayList<AspSuite> suite = new ArrayList<AspSuite>();

    AspIfStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspIfStmt parse(Scanner s) {
        enterParser("If Statement");
        AspIfStmt a = new AspIfStmt(s.curLineNum());

        skip(s, TokenKind.ifToken);

        a.expr.add(AspExpr.parse(s));

        skip(s, TokenKind.colonToken);

        a.suite.add(AspSuite.parse(s));

        while(s.curToken().kind == TokenKind.elifToken || 
        s.curToken().kind == TokenKind.elseToken) {

            if(s.curToken().kind == TokenKind.elifToken){
                skip(s, TokenKind.elifToken);
                a.expr.add(AspExpr.parse(s));
                skip(s, TokenKind.colonToken);

                a.suite.add(AspSuite.parse(s));
                
            } else if (s.curToken().kind == TokenKind.elseToken) {
                skip(s, TokenKind.elseToken);
                skip(s, TokenKind.colonToken);

                a.suite.add(AspSuite.parse(s));
            } 

        }
        leaveParser("If Statement");
        return a;
    }

    @Override
    void prettyPrint() {
        
        prettyWrite("if ");
        expr.get(0).prettyPrint();
        prettyWrite(": ");
        
        suite.get(0).prettyPrint();

        for (int i= 1; i < expr.size(); i++) {
            
            prettyWrite("elif ");
            expr.get(i).prettyPrint();
            prettyWrite(": ");

            
            suite.get(i).prettyPrint();

        }

        for (int j= 1; j < suite.size(); j++) {
            prettyWrite("else:");

            
            suite.get(j).prettyPrint();

        }
        

        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        RuntimeValue v = null;
        for (int i = 0; i < expr.size() ; i ++) {
           
            if (expr.get(i).eval(curScope).getBoolValue("eval if stmt", this)){
                trace("If: ...");
                v = suite.get(i).eval(curScope);
                return null;
            }

        }

        if (expr.size() < suite.size()){//betyr at vi har en else
            trace("else: ...");
            v = suite.get(suite.size()-1).eval(curScope);
        } 
        return null;
        /* 
        evaluer alle expr, til du finner en som er sann
        evaluer tilhørende suite. så ferdig
        Hvis ingen expr er sann evaluer else grenens suite. hvis det er noen else sa

        bruk trace
        */ 
        
    }
    
}
