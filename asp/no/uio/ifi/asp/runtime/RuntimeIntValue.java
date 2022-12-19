package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue (long v) {
        intValue = v;
    }
    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "integer";
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
        }

    @Override 
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
    if (intValue == 0) {
        return false;
    } else {
        return true;
    }
    /*
    runtimeError("Type error: "+what+" is not a Boolean!", where);
    return false;  // Required by the compiler!*/
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        // TODO Auto-generated method stub
        return (double)intValue;
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeIntValue(v.getIntValue("+ operand", where) + intValue);
        } else if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(v.getFloatValue("+ operand", where) + intValue);
        }
        runtimeError("'+' undefined for "+ typeName() +"!", where);
	    return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(intValue / v.getIntValue("/ operand", where));
        } else if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("'/' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(intValue == v.getIntValue("== operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if(intValue == v.getFloatValue("== operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }
        
        runtimeError("'==' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(intValue > v.getIntValue("> operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( intValue > v.getFloatValue("> operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        }
        runtimeError("'>' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(intValue >= v.getIntValue(">= operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( intValue >= v.getFloatValue(">= operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        }
        runtimeError("'>=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operand", where)));
        } else if(v instanceof RuntimeIntValue){
            return new RuntimeIntValue(Math.floorDiv(intValue , v.getIntValue("// operand", where)));
        }
        runtimeError("'//' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(intValue < v.getIntValue("< operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( intValue < v.getFloatValue("< operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        }

        runtimeError("'<' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
        }
    
        @Override
        public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeIntValue){
                if(intValue <= v.getIntValue("<= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            } else if(v instanceof RuntimeFloatValue){
                if( intValue <= v.getFloatValue("<= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            }
        runtimeError("'<=' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
        }


        @Override
        public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeFloatValue){
                return new RuntimeFloatValue(intValue - v.getFloatValue("% operand", where)* Math.floor(intValue / v.getFloatValue("// operand", where)));
            } else if(v instanceof RuntimeIntValue){
                return new RuntimeIntValue(Math.floorMod(intValue , v.getIntValue("// operand", where)));
            }
            runtimeError("'%' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }
        
        @Override
        public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeIntValue){
                return new RuntimeIntValue(v.getIntValue("* operand", where) * intValue);
            } else if(v instanceof RuntimeFloatValue){
                return new RuntimeFloatValue(v.getFloatValue("* operand", where) * intValue);
            }
            runtimeError("'*' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }

        @Override
        public RuntimeValue evalNegate(AspSyntax where) {
            return new RuntimeIntValue(-1 * intValue);

            /*runtimeError("Unary '-' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!*/
        }

        @Override
        public RuntimeValue evalNot(AspSyntax where) {
            return new RuntimeBoolValue(! getBoolValue("not operand", where));
            /*runtimeError("'not' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!*/
        }
        
        @Override
        public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeIntValue){
                if(intValue != v.getIntValue("!= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            } else if(v instanceof RuntimeFloatValue){
                if(intValue != v.getFloatValue("!= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            } else if(v instanceof RuntimeNoneValue){
                return new RuntimeBoolValue(false);
            }
            runtimeError("'!=' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }
        

        @Override
        public RuntimeValue evalPositive(AspSyntax where) {
            return new RuntimeIntValue(intValue);
            /*runtimeError("Unary '+' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!*/
        }

        @Override
        public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeIntValue){
                return new RuntimeIntValue(intValue - v.getIntValue("- operand", where));
            } else if(v instanceof RuntimeFloatValue){
                double annenVerdi = v.getFloatValue("- operand", where);
                return new RuntimeFloatValue(intValue - annenVerdi);
            }
            System.out.println("evalsubtract: " + intValue);

            runtimeError("'-' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }

}
