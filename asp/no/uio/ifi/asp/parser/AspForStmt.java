package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspForStmt extends AspCompoundStmt{
    AspName name = null;
    AspExpr expr = null;
    AspSuite suite = null;

    AspForStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspForStmt parse(Scanner s){
        enterParser("For Statement");
        AspForStmt a = new AspForStmt(s.curLineNum());

        skip(s, TokenKind.forToken);

        a.name = AspName.parse(s);

        skip(s, TokenKind.inToken);

        a.expr = AspExpr.parse(s);

        skip(s, TokenKind.colonToken);

        a.suite = AspSuite.parse(s);

        leaveParser("For Statement");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("for ");

        name.prettyPrint();

        prettyWrite(" in ");

        expr.prettyPrint();

        prettyWrite(":");

        suite.prettyPrint();  
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        /*Evaluer expr skjekk at den er en liste
        for hvert element i listen:
            tilornde elemementet til variabelen <name>. (mer om tilordning senere idag.)
            evaluer suite
        
            naar vi har evaluert hver element er vi ferdig.
        husk: oppdater skopet til i!!!!
        */

        RuntimeValue evaluert = expr.eval(curScope);

        if(evaluert instanceof RuntimeListValue){ 
            ArrayList<RuntimeValue> liste = ((RuntimeListValue) evaluert).hentListe();
            for (int i = 0; i < liste.size(); i++){
                trace("for " + (i + 1) + ": " + name.ss + " = " + liste.get(i));
                
                curScope.assign(name.ss, liste.get(i)); //oppdaterer imaginary i i for lokken :)
                suite.eval(curScope); //saa lenge vi opptaderer i skal vi gjore innholdet, suite, i for lokken hver gang
            }
        }
        return null;  //returnerer null fordi denne metoden skal bare oppdatere skopet 
    }
    
}
