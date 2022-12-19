// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;


/* 
Hvordan holde styr på alle variablene.
Svar: hashmap.
Det globale programmet i asp utgjør et skop, navnerom der vi
	*Deklarerer variabler
	*Får tak i variablenes verdi

Representasjon av et asp-skop
Vi trenger:
	*En navnetabell decls , hashmappet
	*En metode assign for tilordning
	*En metode find for å vinne variables verdi. Og om den er deklarert. Hvis den ikke er deklarert feilmelding.

når vi kaller på funkjsoner får vi et nytt skop som eksisterer så lenge funken er aktiv. Blir som en lenkeliste med nestepeker.
Om en varibaler er ukjent i det kautelle skopet kan det være kjent i et ytre skop. 
Så antar vi skjekker fra innerste skop til ytterste til vi finner variabelen, hvis ikke feilmelding. 
når vi kaller en funkjson oppretter vi et nytt skop for hvert kall.
Antar at vi da også på assigne outer i vårt nye skop til det vi er i.

hvilke skop har vi:
* bibloteket md 7 predefinerte funkjsoner (det ytterste??)
* globalt skop 
* funkjsoner, når de blir kaldt.

globalscopes outer peker på libScope

*/

// For part 4:
import java.util.ArrayList;
import java.util.HashMap;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeScope {
    private RuntimeScope outer;
    private HashMap<String,RuntimeValue> decls = new HashMap<>(); //names og verdi til skopet
    private ArrayList<String> globalNames = new ArrayList<>(); //global så man skal få tak i de i alle skop

    public RuntimeScope() {
	// Used by the library (and when testing expressions in part 3)
	outer = null;
    }

    public RuntimeScope(RuntimeScope oScope) {
	// Used by all user scopes
	outer = oScope;
    }


    public void assign(String id, RuntimeValue val) {
		decls.put(id, val);
    }

    public RuntimeValue find(String id, AspSyntax where) {
		if (globalNames.contains(id)) { //skjekker om en deklarasjon er deklarert i golbalnames
			RuntimeValue v = Main.globalScope.decls.get(id);
			if (v != null){ 
				return v;
			}
		} else { //ellers leter vanslig gjennom skopene.
			RuntimeScope scope = this;
			//leter gjennom scopene
			while (scope != null) { //bilbioteket sitt scope = null
				RuntimeValue v = scope.decls.get(id);
				if (v != null) 
					return v;
				scope = scope.outer;
			}
		}
		RuntimeValue.runtimeError("Name " + id + " not defined!", where);
			return null;  // Required by the compiler.
    }

    public boolean hasDefined(String id) {
		return decls.get(id) != null;
    }

    public boolean hasGlobalName(String id) {
		return globalNames.contains(id);
    }

    public void registerGlobalName(String id) {
		globalNames.add(id);
    }
}
