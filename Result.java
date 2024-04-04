import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Result {
    public static List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        List<Function<List<Double>, Double>> functions = SNAEFunctions.get_functions(system_id);

        List<Double> currentApproximations = initial_approximations;
        double epsilon = 1e-5;

        while (true) {
            List<Double> nextApproximations = new ArrayList<>();

            for (int i = 0; i < number_of_unknowns; i++) {
                double sum = 0.0;
                for (Function<List<Double>, Double> function : functions) {
                    List<Double> updatedArgs = new ArrayList<>(currentApproximations);
                    updatedArgs.set(i, function.apply(updatedArgs));
                    sum += updatedArgs.get(i);
                }
                nextApproximations.add(currentApproximations.get(i) - sum / functions.size());
            }

            boolean isConverged = true;
            for (int i = 0; i < number_of_unknowns; i++) {
                if (Math.abs(currentApproximations.get(i) - nextApproximations.get(i)) > epsilon) {
                    isConverged = false;
                    break;
                }
            }

            if (isConverged) {
                return nextApproximations;
            } else {
                currentApproximations = nextApproximations;
            }
        }
    }
}