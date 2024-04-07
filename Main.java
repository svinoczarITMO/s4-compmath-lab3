import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int system_id = Integer.parseInt(bufferedReader.readLine().trim());

        int number_of_unknowns = Integer.parseInt(bufferedReader.readLine().trim());

        List<Double> initial_approximations = IntStream.range(0, number_of_unknowns).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        List<Double> result = Result.solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"))
                        + "\n"
        );
        bufferedReader.close();
        bufferedWriter.close();
    }
}