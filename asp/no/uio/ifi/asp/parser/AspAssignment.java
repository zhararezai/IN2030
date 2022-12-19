package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspAssignment extends AspSmallStmt{
    AspName name = null;
    ArrayList<AspSubscription> ss = new ArrayList<AspSubscription>();
    AspExpr expr = null;

    AspAssignment(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspAssignment parse(Scanner s) {
        enterParser("Assignment");

        AspAssignment a = new AspAssignment(s.curLineNum());

        a.name = AspName.parse(s);

        while (s.curToken().kind != TokenKind.equalToken) {
            a.ss.add(AspSubscription.parse(s));
        }
        
        skip(s, equalToken);

        a.expr = AspExpr.parse(s);
        
        leaveParser("Assignment");
        return a;
    }

    @Override
    void prettyPrint() {
        
        name.prettyPrint();
        
        for (int i = 0; i< ss.size(); i++) {
            ss.get(i).prettyPrint();
        }
        

        prettyWrite(" = ");

        expr.prettyPrint();
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //sikkert ikke riktig
        //WWWWWTTTTFFFFFFFFFFF
        RuntimeValue v = expr.eval(curScope);
        int sub = ss.size();

        if (sub == 0) { //enkle variabler uten subscription
            curScope.assign(name.ss, v);
            trace(name.ss + " = " + v.showInfo()); 
        } else { //hvis subscription --> list eller dict
            String str = ""; //denne stringen skal brukes i trace()
            RuntimeValue v2 = name.eval(curScope); //evaluerer namet
            RuntimeValue evalIndeks = ss.get(0).eval(curScope); //evaluerer indeks 

            str += name.toString();

            //loope oss gjennom subscriptions med evalsubscription som returnerer elementet paa indeks vi sender inn
            for(int i = 0; i < ss.size() - 1; i++){
                str += "[" + evalIndeks.showInfo() + "]";
                v2 = v2.evalSubscription(evalIndeks, this); //her lagres elementet som skal byttes ut i evalAssignElem()
                evalIndeks = ss.get(i).eval(curScope); //oppdaterer indeks
            }

            str += "[" + evalIndeks.toString() + "]";
            v2.evalAssignElem(evalIndeks, v, this);
            str += " = " + v.showInfo();

            trace(str);    
        }
    
        return null; //metoden oppdaterer skopet. Derfor returnerer vi bare null

        //om vi har en subscription paa name, at name er en liste eller dict 
        //saa maa vi ogsaa bruke curscope evalassignelem for aa oppdatere scopet.
        //om det ikke er subscription skal vi bruke assign.
        
    }
    
}
