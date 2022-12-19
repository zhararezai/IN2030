package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspSmallStmt extends AspSyntax{

    AspSmallStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspSmallStmt parse(Scanner s){
        enterParser("Small Statement");
        AspSmallStmt ss = null;
        if (s.curToken().kind == TokenKind.returnToken) 
            ss = AspReturnStmt.parse(s);
        else if (s.curToken().kind == TokenKind.passToken)
            ss = AspPassStmt.parse(s);
        else if (s.curToken().kind == TokenKind.globalToken)
            ss = AspGlobalStmt.parse(s);
        else if (s.anyEqualToken()) 
            ss = AspAssignment.parse(s);
        else
            ss = AspExprStmt.parse(s);
        
        leaveParser("Small Statement");
        return ss;
    }
    
}
