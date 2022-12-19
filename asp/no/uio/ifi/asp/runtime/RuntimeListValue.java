package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    //lagrer liste-verdien
    ArrayList<RuntimeValue> liste;

    public RuntimeListValue (ArrayList<RuntimeValue> li) {
        liste = li;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "list";
    }

    @Override
    public String toString() {
        return liste.toString();
        }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        
        if(v instanceof RuntimeIntValue){
            ArrayList<RuntimeValue> list = new ArrayList<>();

            for(int i = 0; i < v.getIntValue("* operand", where); i++){
                list.addAll(liste);
            }

            return new RuntimeListValue(list);
        }
        runtimeError("'*' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
        
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (liste.size() ==  0) {
            return false;
        } else {
            return true;
        }
        /*
        runtimeError("Type error: "+what+" is not a Boolean!", where);
        return false;  // Required by the compiler!*/
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(! getBoolValue("not operand", where));
        /*
        runtimeError("'not' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!*/
        }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
 
        if (v instanceof RuntimeIntValue) {
            int index = (int)v.getIntValue("Subscription", where);
            if ( index < liste.size())
                return liste.get((int)v.getIntValue("Subscription", where));
            else {
                runtimeError("List index " + index + " out of bounds", where);
            }
        }

        runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        
        
        

        if (inx instanceof RuntimeIntValue){

            if (inx.getIntValue("eval assign element list", where) < liste.size()) {
                liste.set((int)inx.getIntValue("eval assign element list", where), val);
            }

        } else {
            runtimeError("Assigning to an element not allowed for "+typeName()+"!", where);

        }


        //Hvis inx sender inn elementet ikke indexen. Vi må finne indexen til elementet
        /*int i = liste.indexOf(inx);

        if (i < liste.size()) { //skjekker om lovlig indeks
                liste.set(i, val);
        } else {
            runtimeError("list index out of bounds", where);
        }*/
        
        
        
    }

    public ArrayList<RuntimeValue> hentListe(){
        return liste;
    }

    
}
