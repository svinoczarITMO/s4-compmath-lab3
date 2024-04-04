import java.util.*;
import java.util.function.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.tan;

class SNAEFunctions {
    static double k = 0.4;
    static double a = 0.9;

    private static double first_function(List<Double> args) {
        return sin(args.get(0));
    }

    private static double second_function(List<Double> args) {
        return (args.get(0) * args.get(1)) / 2;
    }

    private static double third_function(List<Double> args) {
        return tan(args.get(0) * args.get(1) + k) - pow(args.get(0), 2);
    }

    private static double fourth_function(List<Double> args) {
        return a * pow(args.get(0), 2) + 2 * pow(args.get(1), 2) - 1;
    }

    private static double fifth_function(List<Double> args) {
        return pow(args.get(0), 2) + pow(args.get(1), 2) + pow(args.get(2), 2) - 1;
    }

    private static double six_function(List<Double> args) {
        return 2 * pow(args.get(0), 2) + pow(args.get(1), 2) - 4 * args.get(2);
    }


    private static double seven_function(List<Double> args) {
        return 3 * pow(args.get(0), 2) - 4 * args.get(1) + pow(args.get(2), 2);
    }

    private static double default_function(List<Double> args) {
        return 0.0;
    }

    /*
    * How to use this function:
    *    List<Function<Double, List<Double>> funcs = SNAEFunctions.get_functions(4);
    *    funcs[0].apply(List.of(0.0001, 0.002));
    */
    public static List<Function<List<Double>, Double>> get_functions(int n) {
        switch (n) {
          case (1):
            return List.of(SNAEFunctions::first_function, SNAEFunctions::second_function);
          case (2):{
              SNAEFunctions.k = 0.4;
              SNAEFunctions.a = 0.9;
              return List.of(SNAEFunctions::third_function, SNAEFunctions::fourth_function);
          }
          case (3):{
              SNAEFunctions.k = 0.0;
              SNAEFunctions.a = 0.5;
              return List.of(SNAEFunctions::third_function, SNAEFunctions::fourth_function);
          }
          case (4):
            return List.of(SNAEFunctions::fifth_function, SNAEFunctions::six_function, SNAEFunctions::seven_function);
          default:
            return List.of(SNAEFunctions::default_function);
        }
    }
}