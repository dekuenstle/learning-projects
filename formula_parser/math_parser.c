#include "math_parser.h"

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
// private functions
double eval(char *expression);
unsigned int lastStrPos(char *str, char character);
int isOperator(char *expr, unsigned int pos);
void lStrOfPos(char* target, char *source, unsigned int pos);
void rStrOfPos(char* target, char *source, unsigned int pos);
enum ParenthesisState parenthesisState(char *str);
void replaceCharacter(char *str, char toReplace, char character);
// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //


double evaluateString(char *expression)
{
    /*  // !!!!!! BUG !!!!!!
    // delete space
    char str[strlen(expression)];
    char *pch = strtok (expression," ");
    while (pch != NULL)
    {
        strcat(str, pch);
        pch = strtok (NULL, " ,.-");
    }
    expression = str;
    */  // !!!!!!!!!!!!!!!!!
    
    // replace comma (for european dot)
    replaceCharacter(expression, ',' , '.' );
    
    // evaluate the string
    return eval(expression);
}


double eval(char *expression)
{
    // Supported operators 
    char ops[5] = {'-','/','^','E','\0'};
    
    // removes start end end parenthesis
    while (expression[0] == '(' 
           && expression[strlen(expression)-1] == ')') {
        rStrOfPos(expression, expression, 0);
        lStrOfPos(expression, expression, (unsigned int)strlen(expression)-1);
    }
    
    int a;
    for(a = 0; a < 4; a++){ 
        char operator = ops[a];
        
        unsigned int pos;
        char substr[strlen(expression)+1];
        strcpy(substr, expression);
        
        // Do until operator outside parenthesis has been found
        do {
            
            // search for position of operator
            if(operator == '-'){
                char nexpr[strlen(substr)+1];
                strcpy(nexpr, substr);
                // tests for unary  -/+
                do {
                    unsigned int pos1 = lastStrPos(nexpr, '-');
                    unsigned int pos2 = lastStrPos(nexpr, '+');
                    if(pos1 > pos2) pos = pos1;
                    else {pos = pos2;} 
                    lStrOfPos(nexpr, expression, pos);
                } while (pos && !isOperator(expression,  pos));
            }
            else if(operator == '/'){
                unsigned int pos1 = lastStrPos(substr, '/');
                unsigned int pos2 = lastStrPos(substr, '*');
                if(pos1 > pos2) pos = pos1;
                else {pos = pos2;} 
            }
            else pos = lastStrPos(substr, operator);
                if (pos)
                    lStrOfPos(substr, expression, pos);
            
        } while (pos  
                 && (parenthesisState(substr) 
                     != OutsideParenthesisState));
        
        operator = expression[pos];
          
        
        // Operator found
        if (pos > 0) {
            
            // get the strings left an right of the operator
            char part1[pos+1];        
            lStrOfPos(part1, expression, pos);
            
            char part2[strlen(expression) - pos];
            rStrOfPos(part2, expression, pos);

            double v1, v2;
    
            v1 = eval(part1);
            v2 = eval(part2);

            switch (operator) {
                case '-':
                    return v1 - v2;
                    break;
                
                case '+':
                    return v1 + v2;
                    break;
                    
                case '/':
                    return v1 / v2;
                    break;
                    
                case '*':
                    return v1 * v2;
                    break;
                    
                case '^':
                    return pow(v1, v2);
                    break;
                case 'E':
                    return v1 * pow(10.0, v2);
                    break;
                
            }
        }

    }
    // No operator found -> single number
    return strtod(expression, NULL);    
}

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

unsigned int lastStrPos(char *str, char character)
{
    // returns the very last index position of a single character in the string
    // if the string doesnt contain the character, it returns NULL
    char *pos = strrchr(str, character);
    if (pos != NULL){
        unsigned int a = (unsigned int)(pos-str);
        return a;
    }
    return 0;
}

void lStrOfPos(char* target, char *source, unsigned int pos)
{
    // target will become the part of source left of index pos
    strncpy(target, source, pos);
    target[pos] = '\0'; 
}


void rStrOfPos(char* target, char *source, unsigned int pos)
{
    // target will become the part of source right of index pos
    source = source + pos + 1;
    strcpy(target, source);

}

void replaceCharacter(char *str, char toReplace, char character)
{
    // replace every character a in the string with the character b
    int a; int len = (unsigned int)strlen(str);
    for (a = 0; a < len; a++) {
        if (str[a] == toReplace)
            str[a] = character;
    }
}

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

int isOperator(char *expr, unsigned int pos)
{
    // detects, if an '+' '-' is an operator, or an unary. 
    // if it's an operator, it has to follow "0123456789.)"
    char str[13] = "0123456789.)";
    int a;
    int test = 0;
    
    for (a = 0; a < 12; a++) {
        if(expr[pos-1] == str[a]){
            test = 1;
            break;}
    }

    return test;
    
}


enum ParenthesisState parenthesisState(char *str)
{
    // count parenthesis
    unsigned int cntOpen = 0;
    unsigned int cntClose = 0;
    int a; unsigned long b = strlen(str);
    for (a = 0; a < b; a++) {
        if (str[a] == '(')
            cntOpen++;
        else if (str[a] == ')')
            cntClose++;    }
    // '(' > ')' -> operator right of the string is inside parenthesis
    if (cntOpen > cntClose)
        return InsideParenthesisState;
    // '(' < ')' -> Some terrible Syntax Error 
    else if (cntOpen < cntClose)
        return SyntaxErrorParenthesisState;
    // '(' < ')' -> operator right of the string is outside parenthesis
    else return OutsideParenthesisState;
}
