package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {

    //legger runtime objektet i skop???
    AspFuncDef def; //peker til hvor i treet den er...?
    RuntimeScope defScope; //hva var curscope når denne funkjsonen ble opprettet.
    String name; //for feilmeldinger
    
    //to konstruktører...

    public RuntimeFunc(String n){
        name = n;
    }

    public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String na){
        this.def = def;
        this.defScope = defScope;
        name = na;
    }


    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "func";
    }

    @Override
    public RuntimeValue evalFuncCall (ArrayList<RuntimeValue> actPars, AspSyntax where) {

        RuntimeScope newScope = new RuntimeScope(defScope);

        //skjekk antall aktuelle parmaetere mot formelle parametere
        if (def.hentListe().size() -1 == actPars.size()) { //-1 fordi forste navn er navnet paa funksjonen
            
            for (int i = 0; i < actPars.size(); i ++) {
                String forparam = def.hentnavn(i+1); //+1 fordi forste navn er navnet paa funksjonen
                newScope.assign(forparam, actPars.get(i));   
            }
           
        } else {
            runtimeError("Wrong amount of func arguments", where);
        }


        //fra forelesning. 
        //loser problemet med tilbakehopp
        try {
            def.hentBodySuite().eval(newScope); //gjor funkjsonen fra imaginary koden
        } catch (RuntimeReturnValue rrv){
            return rrv.value;
        }
        return new RuntimeNoneValue(); //om det ikke er noe return i funksjonen
    }

    public String toString() {
        return name;
    }
}