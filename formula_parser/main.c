//
//  main.c
//  Math Parser
//
//  Created by David-Elias Künstle on 24.09.11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#include <math_parser.h>

int main(int argc, char *argv[])
{
    char* formula = "1+3*6";
    double result = evaluateString(formula);
    printf("Evaluate %s: %.3f", formula, result);
}
