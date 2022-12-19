package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;



public class AspFactor extends AspSyntax {
    //* / % //
    ArrayList<AspFactorOpr> fo = new ArrayList<AspFactorOpr>();
    ArrayList<AspPrimary> p = new ArrayList<AspPrimary>();
    ArrayList<AspFactorPrefix> fp = new ArrayList<AspFactorPrefix>();



    AspFactor(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspFactor parse(Scanner s) {
    
        enterParser("Factor");

        AspFactor a = new AspFactor(s.curLineNum());

        if (s.isFactorPrefix()) {
            a.fp.add(AspFactorPrefix.parse(s));
        } else {
            a.fp.add(null);
        }
        a.p.add(AspPrimary.parse(s));

        while (s.isFactorOpr()) {
            a.fo.add(AspFactorOpr.parse(s)); //legger til * / % //

            if (s.isFactorPrefix()) {
                a.fp.add(AspFactorPrefix.parse(s));
            } else {
                a.fp.add(null);
            }
            a.p.add(AspPrimary.parse(s));

        }

       

        leaveParser("Factor");
        return a;

    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < p.size(); i++) {
            if (fp.get(i) != null) {
                fp.get(i).prettyPrint();
            }
            
            p.get(i).prettyPrint();

            if (i < fo.size())
                fo.get(i).prettyPrint();
        } 
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue { 
        //factor = (+-) primary(feks et tall) fctor opr(feks *) primary = - 6 * 3 
        RuntimeValue v = p.get(0).eval(curScope); //evaluerer primary 

        if(fp.get(0) != null){ //hvis factorprefix ikke er null = vi har factorprefix
            TokenKind k = fp.get(0).k; //henter factorprefix-klassen sin tokenkind

            switch(k){ //finner ut av hvilken type
                case plusToken:
                    v = v.evalPositive(this);
                    break;
                case minusToken:
                    v = v.evalNegate(this);
                    break;
                default:
                    Main.panic("Illeagl factor prefix " + k + "!");
            }
        }
        

        for (int i = 1; i < p.size();i++) {//looper oss gjennom primary 
            RuntimeValue primary = p.get(i).eval(curScope); //evaluerer et og et element i primary listen
            TokenKind k = null;

            if(fp.get(i) != null){ //hvis factorprefix ikke er null = vi har factorprefix
                k = fp.get(i).k; //henter tokenkind'et
      
            switch (k) { //sjekker for hvilket symbol 
                case minusToken:
                    primary = primary.evalNegate(this); 
                    break;
                case plusToken:
                    primary = primary.evalPositive(this); 
                    break;
                default:
                    Main.panic("Illeagl factor prefix " + k + "!");
            }
        }

            TokenKind kk = fo.get(i - 1).tk;//factoroperator sin tokenkind
            
            switch (kk) { //sjekker for hvilken tokenkind
                case astToken:
                    v = v.evalMultiply(primary, this); break;
                case slashToken:
                    v = v.evalDivide(primary, this); break;
                case percentToken:
                v = v.evalModulo(primary, this); break;
                case doubleSlashToken:
                v = v.evalIntDivide(primary, this); break;
                default:
                    Main.panic("Illegal term operator " + k + "!");
            }
        }
        return v; 
    } 
}
