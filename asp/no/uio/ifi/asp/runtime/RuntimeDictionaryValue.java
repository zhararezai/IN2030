package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictionaryValue extends RuntimeValue{

    HashMap<String, RuntimeValue> map;
    public RuntimeDictionaryValue ( HashMap<String, RuntimeValue> v ) {
        map = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "Dictionary";
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public String showInfo(){
        String str = "{";
        int teller = 0;

        for(String nokkel : map.keySet()){
            
            if (teller < map.size() - 1) {
                str += "'" + nokkel +"'"+ " : " + map.get(nokkel).showInfo() + ", ";
            } else {
                str += "'" + nokkel +"'"+ " : " + map.get(nokkel).showInfo();
            }
            
            teller++;
        }
    

        str += "}";

        return str;
    }

    
    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(map.size());

        /*runtimeError("'len' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!*/
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (map.size() ==  0) {
            return false;
        } else {
            return true;
        }
        /*
        runtimeError("Type error: "+what+" is not a Boolean!", where);
        return false;  // Required by the compiler!*/
    }

    

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(map.containsKey(v.getStringValue("subscription", where))){
                return map.get(v.getStringValue("subscription", where));
            } else{
                System.out.println("fant ikke nokkel til " + v.getStringValue("subscription", where));
                runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
            }
        }

        runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
    
        return new RuntimeBoolValue(! getBoolValue("not operand", where));
        /*
        runtimeError("'not' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!*/
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }else{
            runtimeError("error for ==", where);
            return null;
        }
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(true);
        } else{
            runtimeError("error for !=", where);
            return null;
        }
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {

        if (inx instanceof RuntimeStringValue){
            
            if (map.containsKey(inx.getStringValue("eval assign element dict", where))) {
                map.put(inx.getStringValue("eval assign element dict", where), val);
            }
        }

        runtimeError("Assigning to an element not allowed for "+typeName()+"!", where);
        }
}
