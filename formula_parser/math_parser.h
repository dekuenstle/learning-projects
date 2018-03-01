//
//  math_parser.h
//  Math Parser
//
//  Created by David-Elias Künstle on 24.09.11.
//

#ifndef Math_Parser_math_parser_h
#define Math_Parser_math_parser_h

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>

enum ParenthesisState {
    InsideParenthesisState,
    OutsideParenthesisState,
    SyntaxErrorParenthesisState
};
// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //


// main function to evaluate a string
double evaluateString(char *expression);

#endif
