// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	super(n);
    }


    public static AspExpr parse(Scanner s) {
	enterParser("Expr");

	
	AspExpr a = new AspExpr(s.curLineNum());

        while (true) {
            a.andTests.add(AspAndTest.parse(s));
            if (s.curToken().kind != orToken) break;
            skip(s, orToken);

        }
        
	leaveParser("Expr");
	return a;
    }


    @Override
    public void prettyPrint() {
        
        int nPrinted = 0;
        for (AspAndTest ant: andTests) {
            if (nPrinted > 0)
                prettyWrite(" or ");
            ant.prettyPrint(); ++nPrinted;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	    //ligner paa and test som er fra kompendiet
        RuntimeValue v = andTests.get(0).eval(curScope);
        for (int i = 1; i < andTests.size(); ++i) {
            if (v.getBoolValue("or operand",this)) //antar at getboolvalue er samme for or som and
                return v;
            v = andTests.get(i).eval(curScope);
            //trace(v.toString() );
        }

        return v;
    }
}
