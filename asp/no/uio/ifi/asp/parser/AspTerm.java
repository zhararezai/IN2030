package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import static no.uio.ifi.asp.scanner.Token.*;

import java.util.ArrayList;

public class AspTerm extends AspSyntax{
    ArrayList<AspFactor> factor = new ArrayList<AspFactor>();
    ArrayList<AspTermOpr> termop = new ArrayList<AspTermOpr>();


    AspTerm(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspTerm parse(Scanner s) {
        enterParser("Term");
        AspTerm a = new AspTerm(s.curLineNum());

        a.factor.add(AspFactor.parse(s));

        while (s.isTermOpr()) {
            a.termop.add(AspTermOpr.parse(s));
            a.factor.add(AspFactor.parse(s));
        }

        leaveParser("Term");
        return a;
    }

    @Override
    void prettyPrint() {
        
        factor.get(0).prettyPrint();

        for (int i = 0; i < termop.size(); i++){
            prettyWrite(" ");

            termop.get(i).prettyPrint();

            prettyWrite(" ");
            
            factor.get(i+1).prettyPrint();
        }
        
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub

        //gitt i forelesning
        RuntimeValue v = factor.get(0).eval(curScope);

        for (int i = 1; i < factor.size(); i++) {
            TokenKind k = termop.get(i-1).k;
            switch (k) {
                case minusToken:
                v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
                case plusToken:
                v = v.evalAdd(factor.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illeagl term operator " + k + "!");
            }
        }
        return v;
    }
    
}
