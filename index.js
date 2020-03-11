class Token {
    line = undefined;
    value = undefined;
    constructor(value, line) {
        this.value = value;
        this.line = line;
    }
}
class NumberToken extends Token {
    constructor(value, line) {
        super(value, line);
    }
}
class IndentifierToken extends Token {
    constructor(value, line) {
        super(value, line);
    }
}
class StringToken extends Token {
    constructor(value, line) {
        super(value, line);
    }
}
Token.EOF = Symbol.for("EOF");
Token.EOL = Symbol.for("EOL");

class Lexer {
    regex = /\s*((\/\/.*)|([0-9]+)|([_A-Za-z]\w*|==|<=|>=|&&|\|\||\W)|("(\\"|\\\\|\\n|[^"])*"))/g;
    queue = [];
    code = "";
    lines = [];
    lineNum = 0;

    constructor(code) {
        this.queue = [];
        this.lineNum = 0;
        this.code = code;
        this.lines = code.split("\n");
    }
    read() {
        if (this._searchInQueue(0))
            return this.queue.shift();
        else
            return Token.EOF;
    }
    peek(i) {
        if (this._searchInQueue(i))
            return this.queue[i];
        else
            return Token.EOF;
    }
    _searchInQueue(index) {
        while (index >= this.queue.length) {
            if (this.lines.length > this.lineNum)
                this._readLine();
            else
                return false;
        }
        return true;
    }
    _readLine() {
        let line = this.lines[this.lineNum];
        if (line) {
            let match;
            while (match = this.regex.exec(line)) {
                this._addToken(this.lineNum, match);
            }
        }
        this.lineNum++;
    }
    _addToken(lineNum, match) {
        if (!match[1] || match[2])
            return;
        let token;
        if (match[3])
            token = new NumberToken(parseInt(match[3]), lineNum);
        else if (match[4])
            token = new IndentifierToken(match[4], lineNum);
        else if (match[5])
            token = new StringToken(match[5], lineNum);
        this.queue.push(token);
    }
}


let lexer = new Lexer(`
while i < 10 {
    sum = sum + i
    i = i + 1
}
sum
`);
let token;
while ((token = lexer.read()) !== Token.EOF) {
    console.log(token.value);
}