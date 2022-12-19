package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue{
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "String";
    }

    @Override
    public String toString() {
        return "'" + stringValue + "'";
    }


    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeStringValue(stringValue + v.getStringValue("+ operand", where));
        }
        runtimeError("'+' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {

        if(v instanceof RuntimeIntValue){
            String str = stringValue;
            String ferdig = "";

            for(int i = 0; i < v.getIntValue("* operand", where); i++){
                ferdig += str;
            }

            return new RuntimeStringValue(ferdig);
        }
        runtimeError("'*' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(v.getStringValue("== operand", where) == stringValue){
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("'==' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }
        
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(stringValue.compareTo(v.getStringValue("> operand", where)) > 0){
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("'>' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }


    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(stringValue.compareTo(v.getStringValue(">= operand", where)) >= 0){
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }

        runtimeError("'>=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }


    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(stringValue.length());

        /*runtimeError("'len' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!*/
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            char c = stringValue.charAt((int)v.getIntValue("subscription", where));
            String s = "";
            s += c;
            System.out.println("stringliteral sub: " + s);
            return new RuntimeStringValue(s);
        }

        runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }


    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(stringValue.compareTo(v.getStringValue("< operand", where)) < 0){
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("'<' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }
                    
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(stringValue.compareTo(v.getStringValue("<= operand", where)) <= 0){
                return new RuntimeBoolValue(true);
            } else {
                return new RuntimeBoolValue(false);
            }
        }
        runtimeError("'<=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(! getBoolValue("not operand", where));

        /*runtimeError("'not' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!*/
    }

    @Override
        public boolean getBoolValue(String what, AspSyntax where) {
        if (stringValue == "") {
            return false;
        } else {
            return true;
        }
        /*
        runtimeError("Type error: "+what+" is not a Boolean!", where);
        return false;  // Required by the compiler!*/
    }

    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            if(stringValue == v.getStringValue("!= operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } 
        runtimeError("'!=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }
}
