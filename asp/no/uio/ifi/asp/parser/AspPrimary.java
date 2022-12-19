package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspPrimary extends AspSyntax {
    AspAtom atom = null;
    ArrayList<AspPrimarySuffix> suf = new ArrayList<>();
   
    AspPrimary(int n) {
        super(n);
    }

    public static AspPrimary parse(Scanner s){
        enterParser("Primary");
        AspPrimary a = new AspPrimary(s.curLineNum());

        a.atom = AspAtom.parse(s); //kaller paa atom.parse()

        while(s.curToken().kind == TokenKind.leftBracketToken || s.curToken().kind == TokenKind.leftParToken){
            a.suf.add(AspPrimarySuffix.parse(s));
        }
        
        leaveParser("Primary");
        return a;
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();

        /*while(!suf.isEmpty()){
            suf.remove(0).prettyPrint();
        }*/

        for(AspPrimarySuffix psuf : suf){
            psuf.prettyPrint();
        }

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //tror riktig
        RuntimeValue v = atom.eval(curScope);
        

        for(AspPrimarySuffix psuf : suf){
            //subscriptions
            if(v instanceof RuntimeDictionaryValue || v instanceof RuntimeListValue || v instanceof RuntimeStringValue){
                v = v.evalSubscription(psuf.eval(curScope), this);
            } else { //arguments og func
                if(v instanceof RuntimeFunc && psuf instanceof AspArguments){
                    ArrayList<RuntimeValue> argliste = new ArrayList<>(); //for aa sende inn liste med arguments til funkjsonkallet aka parameterne til funksjonen
                    AspArguments p = (AspArguments) psuf; //caster til aspargumets for aa faa tak i exprene (e)
                    
                    for (AspExpr ex: p.e) {
                        argliste.add(ex.eval(curScope));
                    }
                    
                    trace("call function " + v.toString() + " with parameters " + argliste.toString());
                    //sender inn evaluata arguments til funkjsonskall
                    v = v.evalFuncCall(argliste, this);

                }
            }
        }
       
        return v;
    }

}