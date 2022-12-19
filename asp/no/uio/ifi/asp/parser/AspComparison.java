package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspComparison extends AspSyntax{
    ArrayList<AspTerm> t = new ArrayList<AspTerm>();
    ArrayList<AspCompOpr> co = new ArrayList<AspCompOpr>();

    AspComparison(int n) {
        super(n);
        
    }

    static AspComparison parse(Scanner s) {
        enterParser("Comparison");
        AspComparison a = new AspComparison(s.curLineNum());
        

        a.t.add(AspTerm.parse(s));

        while (s.isCompOpr()) {
            a.co.add(AspCompOpr.parse(s));
            a.t.add(AspTerm.parse(s));
        }

        leaveParser("Comparison");

        return a;
    }

    @Override
    void prettyPrint() {



        t.get(0).prettyPrint();
        
        for (int i = 1; i < t.size(); i++){
            
            prettyWrite(" ");

            co.get(i-1).prettyPrint();

            prettyWrite(" ");
            
            t.get(i).prettyPrint();
        }
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {

        //term gitt i forelsening og denne er helt lik

        RuntimeValue v = t.get(0).eval(curScope);

        for (int i = 1; i < t.size(); i++) {
            TokenKind k = co.get(i-1).tk;
            v = t.get(i - 1).eval(curScope);

            switch (k) {
                case lessToken:
                    v = v.evalLess(t.get(i).eval(curScope), this); break;
                case greaterToken:
                    v = v.evalGreater(t.get(i).eval(curScope), this); break;
                case doubleEqualToken:
                    v = v.evalEqual(t.get(i).eval(curScope), this); break;
                case greaterEqualToken:
                    v = v.evalGreaterEqual(t.get(i).eval(curScope), this); break;
                case lessEqualToken:
                    v = v.evalLessEqual(t.get(i).eval(curScope), this); break;
                case notEqualToken:
                    v = v.evalNotEqual(t.get(i).eval(curScope), this); break;
                default:
                    Main.panic("Illegal comp operator " + k + "!");
            }
            if(!v.getBoolValue("comp opr", this)){
                return v;
            }

        }

        return v;
    }
    
}
