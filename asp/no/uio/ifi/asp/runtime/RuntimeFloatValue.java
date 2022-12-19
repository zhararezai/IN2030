package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue{
    double floatValue;

    public RuntimeFloatValue (double v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        // TODO Auto-generated method stub
        return "integer";
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
        }

    @Override 
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(v.getIntValue("+ operand", where) + floatValue);
        } else if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(v.getFloatValue("+ operand", where) + floatValue);
        }
        runtimeError("'+' undefined for "+ typeName() +"!", where);
	    return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operand", where));
        } else if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("'/' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(floatValue == v.getIntValue("== operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if(floatValue == v.getFloatValue("== operand", where) ){
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
            if(floatValue > v.getIntValue("> operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( floatValue > v.getFloatValue("> operand", where) ){
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
            if(floatValue >= v.getIntValue(">= operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( floatValue >= v.getFloatValue(">= operand", where) ){
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
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
        } else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operand", where)));
        }
        runtimeError("'//' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            if(floatValue < v.getIntValue("< operand", where) ){
                return new RuntimeBoolValue(true);
            } else{
                return new RuntimeBoolValue(false);
            } 
        } else if(v instanceof RuntimeFloatValue){
            if( floatValue < v.getFloatValue("< operand", where) ){
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
                if(floatValue <= v.getIntValue("<= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            } else if(v instanceof RuntimeFloatValue){
                if( floatValue <= v.getFloatValue("<= operand", where) ){
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
                return new RuntimeFloatValue(floatValue - v.getFloatValue("% operand", where)* Math.floor(floatValue / v.getFloatValue("// operand", where)));
            } else if(v instanceof RuntimeIntValue){
                return new RuntimeFloatValue(floatValue - v.getIntValue("% operand", where)* Math.floor(floatValue / v.getIntValue("// operand", where)));
            }
            runtimeError("'%' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }
        
        @Override
        public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
            if(v instanceof RuntimeIntValue){
                return new RuntimeFloatValue(v.getIntValue("* operand", where) * floatValue);
            } else if(v instanceof RuntimeFloatValue){
                return new RuntimeFloatValue(v.getFloatValue("* operand", where) * floatValue);
            }
            runtimeError("'*' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!
        }

        @Override
        public boolean getBoolValue(String what, AspSyntax where) {
        if (floatValue == 0.0) {
            return false;
        } else {
            return true;
        }
        /*
        runtimeError("Type error: "+what+" is not a Boolean!", where);
        return false;  // Required by the compiler!*/
        }

        @Override
        public RuntimeValue evalNegate(AspSyntax where) {
            return new RuntimeFloatValue(- floatValue);

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
                if(floatValue != v.getIntValue("!= operand", where) ){
                    return new RuntimeBoolValue(true);
                } else{
                    return new RuntimeBoolValue(false);
                } 
            } else if(v instanceof RuntimeFloatValue){
                if(floatValue != v.getFloatValue("!= operand", where) ){
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
            return new RuntimeFloatValue(floatValue);
            /*runtimeError("Unary '+' undefined for "+typeName()+"!", where);
            return null;  // Required by the compiler!*/
        }

        @Override
        public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
            RuntimeValue returnValue = null;
            if(v instanceof RuntimeIntValue){
                long intValue =  v.getIntValue("- operand", where);
                returnValue = new RuntimeFloatValue(floatValue - intValue);
                //return new RuntimeFloatValue(floatValue - v.getIntValue("- operand", where));
            } else if(v instanceof RuntimeFloatValue){
                double annenFloat = v.getFloatValue("- operand", where);
                returnValue = new RuntimeFloatValue(floatValue - annenFloat);
                //return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
            }
            runtimeError("'-' i evalSubtract RuntimeFloatValue undefined for "+typeName()+"!", where);
            return returnValue;  // Required by the compiler!
        }
}
