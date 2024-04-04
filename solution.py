#!/bin/python3

import math
import os
import random
import re
import sys


k = 0.4
a = 0.9

def first_function(args: []) -> float:
    return math.sin(args[0])


def second_function(args: []) -> float:
    return (args[0] * args[1]) / 2


def third_function(args: []) -> float:
    return math.tan(args[0]*args[1] + k) - pow(args[0], 2)


def fourth_function(args: []) -> float:
    return a * pow(args[0], 2) + 2 * pow(args[1], 2) - 1


def fifth_function(args: []) -> float:
    return pow(args[0], 2) + pow(args[1], 2) + pow(args[2], 2) - 1


def six_function(args: []) -> float:
    return 2 * pow(args[0], 2) + pow(args[1], 2) - 4 * args[2]


def seven_function(args: []) -> float:
    return 3 * pow(args[0], 2) - 4 * args[1] + pow(args[2], 2)


def default_function(args: []) -> float:
    return 0.0


def get_functions(n: int):
    if n == 1:
        return [first_function, second_function]
    elif n == 2:
        k = 0.4
        a = 0.9
        return [third_function, fourth_function]
    elif n == 3:
        k = 0
        a = 0.5
        return [third_function, fourth_function]
    elif n == 4:
        return [fifth_function, six_function, seven_function]
    else:
        return [default_function]


def solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations):
    eps = 1e-5
    functions = get_functions(system_id)
    x = list(initial_approximations)
    f = [function(x) for function in functions]

    while True:
        W = [[0] * number_of_unknowns for _ in range(number_of_unknowns)]
        for i in range(number_of_unknowns):
            for j in range(number_of_unknowns):
                h = 1e-6
                x_tmp = x[:]
                x_tmp[j] += h
                W[i][j] = (functions[i](x_tmp) - f[i]) / h

        Delta_x = solve_linear_system(W, [-f_i for f_i in f])
        x_new = [x_i + dx_i for x_i, dx_i in zip(x, Delta_x)]

        if max([abs(dx_i) for dx_i in Delta_x]) < eps:
            return x_new

        x = x_new
        f = [function(x) for function in functions]

def solve_linear_system(A, b):
    n = len(A)
    for i in range(n):
        max_row = i
        for j in range(i, n):
            if abs(A[j][i]) > abs(A[max_row][i]):
                max_row = j
        if A[max_row][i] == 0:
            return None
        A[i], A[max_row] = A[max_row], A[i]
        b[i], b[max_row] = b[max_row], b[i]
        for j in range(i + 1, n):
            factor = A[j][i] / A[i][i]
            A[j][i] = 0
            for k in range(i + 1, n):
                A[j][k] -= factor * A[i][k]
            b[j] -= factor * b[i]
    x = [0] * n
    for i in range(n - 1, -1, -1):
        x[i] = b[i] / A[i][i]
        for j in range(i + 1, n):
            b[i] -= A[i][j] * x[j]
    return x


    
if __name__ == '__main__':
    system_id = int(input().strip())

    number_of_unknowns = int(input().strip())

    initial_approximations = []

    for _ in range(number_of_unknowns):
        initial_approximations_item = float(input().strip())
        initial_approximations.append(initial_approximations_item)

    result = solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations)

    print('\n'.join(map(str, result)))
    print('\n')