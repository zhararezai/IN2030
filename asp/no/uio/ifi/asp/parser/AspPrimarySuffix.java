package no.uio.ifi.asp.parser;




import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspPrimarySuffix extends AspSyntax {
    

    AspPrimarySuffix(int n) {
        super(n);
    }

    public static AspPrimarySuffix parse(Scanner s) {
        enterParser("Primary Suffix");
        
        AspPrimarySuffix a = null;

        switch (s.curToken().kind) {
            case leftParToken:
            a = AspArguments.parse(s); 
            break;

            case leftBracketToken:
            a = AspSubscription.parse(s);
            break;

            default:
            parserError("Expected an expression atom but found a " + s.curToken().kind + "!", s.curLineNum());


        }
        leaveParser("Primary Suffix");
        return a;
    }
    
}