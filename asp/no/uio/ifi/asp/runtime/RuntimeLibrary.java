// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        //-- Must be changed in part 4:

        /* i bibloteket:
        vi gjor kall paa bibloteksfunksjoner saa likt saa mulig vanlige funkjsoner.
        vi assigner de universelt definerte funkjsonene(biblotekfunkjsonene) i libraryet(som er overste curscope)
        
        da overrider vi eval func call for aa gjøre det bibloteksfunkjsonen vil at vi skal gjore isteden for vanlig behandling av en func
        da maa vi ogsaa gjore check num params slik som i vanlig func call
    
        */
        
        //kode hentet fra forelesning uke 45
        //len, returnerer lengden paa feks en liste
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
                // actualparams blir her en arraylist med feks et liste objekt
                    checkNumParams(actualParams, 1, "len", where); //vi skal bare ha et feks liste objekt
                    return actualParams.get(0).evalLen(where); //returnerer lengden av feks listen
                }});

        //kode fra forelesning uke 45
        //print(v1, v2, ...), printer ut parametere v1, v2 , ..., med " " mellom, resultatet er none
        //actual params blir listen inni () til print
        assign("print", new RuntimeFunc("print"){ 
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
                for(int i = 0; i < actualParams.size(); i++){ 
                    if(i > 0) System.out.print(" "); //blank mellom parameterne
                    System.out.print(actualParams.get(i).toString());
                }
                System.out.println();
                return new RuntimeNoneValue();
            }});

       

        //float, omformer parameteren (en int, float, eller sting) til float
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //vi skal bare ha et objekt i lista
                checkNumParams(actualParams, 1, "float", where);

                RuntimeValue v = actualParams.get(0);

                double doubleValue = 0.0;

                if (v instanceof RuntimeStringValue){
                    //vi basiccly gjor stringen "3.0" om til 3.0
                    try{
                        doubleValue = Double.parseDouble(v.getStringValue("float", where));
                    }catch (Exception e){
                        runtimeError("String " + v.showInfo() + " is not a legal float", where);
                    }


                } else if (v instanceof RuntimeIntValue){
                    doubleValue = (double) v.getIntValue("float", where);
                }else if (v instanceof RuntimeFloatValue){
                    doubleValue = v.getFloatValue("float", where);
                }else{
                    runtimeError("Type error: parameter to float is neither number nor text string", where);
                }
                return new RuntimeFloatValue(doubleValue);
            }
        });

        //int, omformer parameteren (en int, float, eller sting) til int
        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //vi skal bare ha et objekt i lista

                checkNumParams(actualParams, 1, "int", where);

                RuntimeValue v = actualParams.get(0);

                long intValue = 0;

                if (v instanceof RuntimeStringValue){
                    //vi basiccly gjor stringen "3" om til 3
                    try{
                        intValue = Long.parseLong(v.getStringValue("int", where));
                    }catch (Exception e){
                        runtimeError("String " + v.showInfo() + " is not a legal int", where);
                    }


                } else if (v instanceof RuntimeIntValue){
                    intValue = v.getIntValue("int",where);

                }else if (v instanceof RuntimeFloatValue){
                    intValue = (long) v.getFloatValue("int", where);

                }else{
                    runtimeError("Type error: parameter to int is neither number nor text string", where);
                }
                return new RuntimeIntValue(intValue);
            }
        });


        //str, string, omformer parameteren (any) til string
        assign("str", new RuntimeFunc("str"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //vi skal bare ha et objekt i lista
                checkNumParams(actualParams, 1, "str", where);

                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });

        //range, returnerer en liste med tall fra parameter v1 til parameter v2 - 1.
        //actual params innholder v1 og v2 som dermed er int.
        assign("range", new RuntimeFunc("range"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //vi skal bare ha to objekt i lista
                checkNumParams(actualParams, 2, "range", where);
                long start = actualParams.get(0).getIntValue("range", where); //v1
                long end = actualParams.get(1).getIntValue("range", where); //v2

                ArrayList<RuntimeValue> range = new ArrayList<>();
                for (long i = start; i<end; i++){ // < fordi v2-1
                    range.add(new RuntimeIntValue(i));
                }
                return new RuntimeListValue(range);
            }
        });

        //input, ber om input fra bruker/tastaturet, resultatet er en string
        assign("input", new RuntimeFunc("input"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                //vi skal bare ha et objekt i lista                
                checkNumParams(actualParams, 1, "input", where);
                System.out.print(actualParams.get(0));
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });

    }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
	    if (actArgs.size() != nCorrect)
	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
        }
}
