package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> e = new ArrayList<AspExpr>();

    AspArguments(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    public static AspArguments parse(Scanner s) {
        enterParser("Arguments");

        AspArguments a = new AspArguments(s.curLineNum());
        skip(s, TokenKind.leftParToken);

   

        while(s.curToken().kind != rightParToken) {
            if (s.curToken().kind == commaToken)
                skip(s, commaToken);

            a.e.add(AspExpr.parse(s));
        }

        skip(s, TokenKind.rightParToken);
        leaveParser("Arguments");
        return a;
    }

    @Override
    void prettyPrint() {
        prettyWrite("(");

        for(int i = 0; i < e.size(); i++){
            e.get(i).prettyPrint();
            if(i < e.size() - 1){
                prettyWrite(", ");
            }
        }
        prettyWrite(")");
    }

    

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //ingen trace
        ArrayList<RuntimeValue> a = new ArrayList<>();
        for(AspExpr expr : e){
            a.add(expr.eval(curScope));
        }

        RuntimeValue v = new RuntimeListValue(a);
        return v;
    }

    @Override
    public String toString() {
        String str = "";

        for (AspExpr a: e) {
            str += a.toString();
        }

        return str;
    }
}
