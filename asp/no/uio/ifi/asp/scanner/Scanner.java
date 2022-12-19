
//2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }


    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }


    private void readNextLine() {
		curLineTokens.clear();

		boolean erEof = false;

		// Read the next line:
		String line = null;

		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
				erEof = true;

				while (indents.pop() > 0) {
					curLineTokens.add(new Token(dedentToken, curLineNum()));
					Main.log.noteToken(new Token(dedentToken));
				}
	
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}
		

		//-- Must be changed in part 1:

		if(erEof){
			curLineTokens.add(new Token(eofToken, this.curLineNum()));
			Main.log.noteToken(curLineTokens.get(0));
			return;
		}
		

		if(line.trim().isEmpty()){
			return;
		}else if(line.trim().charAt(0) == '#'){
			return;
		}

		//indent kode basert paa algoritmen i kompendiet
		line = this.expandLeadingTabs(line);
		int n = this.findIndent(line);

		if(n > indents.peek()){
			indents.push(n);
			curLineTokens.add(new Token(TokenKind.indentToken, this.curLineNum()));
		} else{
			while(n < indents.peek()){
				indents.pop();
				curLineTokens.add(new Token(TokenKind.dedentToken, this.curLineNum()));
			}
		}
		if(n != indents.peek()){
			System.out.println("indenteringsfeil paa linje: " + this.curLineNum());
			System.exit(-1);
		}

		
		if(!erEof){
			String stringen = "";
			int indeks = 0;
			Token token = null;

			if(line != null){
				char[] charDeler = line.toCharArray();

				while(indeks < charDeler.length){
					if(token == null){
						if(charDeler[indeks] == '#'){
							break;
						}
						//assigner tokens, int, name, string.
						if(this.isDigit(charDeler[indeks]) || charDeler[indeks] == '0'){
							stringen += charDeler[indeks];
							token = new Token(TokenKind.integerToken, this.curLineNum());
						} else if(this.isLetterAZ(charDeler[indeks])){
							stringen += charDeler[indeks];
							token = new Token(TokenKind.nameToken, this.curLineNum());
						} else if((int)charDeler[indeks] == 39 || (int)charDeler[indeks] == 34){
							stringen += charDeler[indeks];
							token = new Token(TokenKind.stringToken, this.curLineNum());
						} else if(charDeler[indeks] != ' '){ //hvis ikke mellomrom
							int nesteIndeks = indeks + 1;
							stringen += charDeler[indeks];
							if(nesteIndeks < charDeler.length){ //behandler hvis = eller // kommer rett etter name
								if(charDeler[nesteIndeks] == '=' || charDeler[indeks] == '/' && charDeler[nesteIndeks] == '/'){
									stringen += charDeler[nesteIndeks];
									indeks++;
								}
							}
							token = new Token(TokenKind.nameToken, this.curLineNum());
							token.name = stringen;
							//hvis ikke nametoken --> operator
							token.checkResWords(TokenKind.astToken, TokenKind.semicolonToken);

							curLineTokens.add(token);
							/*
							if (token.kind == TokenKind.semicolonToken) {
								curLineTokens.add(new Token(TokenKind.newLineToken, this.curLineNum()));
								curLineTokens.add(new Token(TokenKind.newLineToken, this.curLineNum()));
							}*/
							stringen = "";
							token = null;
						}
					} else {
						if(token.kind == TokenKind.integerToken){
							int teller = indeks; //denne starter etter forste digit
							boolean erFloat = false;

							while (teller < charDeler.length && this.isDigit(charDeler[teller])  || teller < charDeler.length && charDeler[teller] == '.') {
								stringen += charDeler[teller];
								if (charDeler[teller] == '.') {
									token.kind = floatToken;
									erFloat = true;
								}
								teller++;
							}

							if(erFloat){
								token.floatLit = Double.parseDouble(stringen);
								curLineTokens.add(token);
							} else {
								token.integerLit = Integer.parseInt(stringen);
								curLineTokens.add(token);
							}


							stringen = "";
							token = null;
							indeks = teller -1;
						} else if(token.kind == TokenKind.stringToken){
							char tegn = stringen.charAt(0);

							if(charDeler[indeks] != tegn){
								stringen += charDeler[indeks];
							} else {
								String strLit = stringen.substring(1);
								token.stringLit = strLit;
								curLineTokens.add(token);
								stringen = "";
								token = null;
							}
						} else if(token.kind == TokenKind.nameToken){
							if(this.isDigit(charDeler[indeks]) || this.isLetterAZ(charDeler[indeks]) || charDeler[indeks] == '_'){
								stringen += charDeler[indeks];
							} else {
								token.name = stringen;
								token.checkResWords(TokenKind.andToken, TokenKind.yieldToken);
								curLineTokens.add(token);
								stringen = "";
								token = null;
								indeks--;
							}
						}
					}
					indeks++;
				}

				if(token != null){
					if(token.kind == TokenKind.integerToken){
						token.integerLit = Integer.parseInt(stringen);
						curLineTokens.add(token);
					} else if(token.kind == TokenKind.floatToken){
						token.floatLit = Double.parseDouble(stringen);
						curLineTokens.add(token);
					} else if(token.kind == TokenKind.nameToken){
						token.name = stringen;
						token.checkResWords(TokenKind.andToken, TokenKind.yieldToken);
						curLineTokens.add(token);
					} else {
						System.out.println("scanner error i readNextLine() paa linje : " + this.curLineNum());
						System.exit(-1);
					}
				}
			} else {
				return;
			}
		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));


		for (Token t: curLineTokens) 
			Main.log.noteToken(t);
	}


    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
	//-- Must be changed in part 1:
	int n = 0; //teller 
	String mellomrom = "";
		for(char symbol : s.toCharArray()){
			if(symbol == ' '){ //hvis tegnet er en blank
				mellomrom += " ";
				n++; //oeker n med 1
			} else if(symbol == '\t'){//hvis tegnet er en TAB
				int antBlanke = 4 - (n % 4);
				//ERSTATT DEN MED ANTBLANKE ANTALL BLANKE
				s.replace('\t', ' '); //erstatter foerst tab med en blank
				for(int i = 1; i < antBlanke; i++){
					s = " " + s; //bygger deretter paa med flere blanke
				}
				n = n + 4 - (n % 4);
			}  else {
				int i = s.indexOf(symbol);
				mellomrom += s.substring(i);
				break;
			}
		}
		return mellomrom;
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }

	public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		boolean erCompOpr = false;

		if(k == TokenKind.lessToken
		|| k == TokenKind.greaterToken
		|| k == TokenKind.doubleEqualToken 
		|| k == TokenKind.greaterEqualToken
		|| k == TokenKind.lessEqualToken
		|| k == TokenKind.notEqualToken){
			erCompOpr = true;
		}
		return erCompOpr;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		boolean erFactorPrefix = false;

		if(k == TokenKind.plusToken
		|| k == TokenKind.minusToken){
			erFactorPrefix = true;
		}
		return erFactorPrefix;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:

		boolean erFactorOpr = false;

		if(k == TokenKind.astToken
		|| k == TokenKind.slashToken
		|| k == TokenKind.percentToken
		|| k == TokenKind.doubleSlashToken){
			erFactorOpr = true;
		}

		return erFactorOpr;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:

		boolean erTermOpr = false;

		if(k == TokenKind.minusToken
		|| k == TokenKind.plusToken){
			erTermOpr = true;
		}

		
		return erTermOpr;
    }

	
    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	    if (t.kind == semicolonToken) return false;
	}
	return false;
    }
}
