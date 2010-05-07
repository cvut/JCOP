/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.problem.ConfigurationMap;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;
import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Renders results into console (or other printStream) in order in which they were added to Result.
 * <p/>
 * Example (if {@link #OUTPUT_STANDARD} is supplied):
 * <p/>
 * <pre>
 * === Algorithm MyAlgorithm used on problem Knapsack, settings default ===
 *   CPU Time:                      16 [ms]
 *   System Time:                    0 [ms]
 *   User Time:                     15 [ms]
 *   Clock Time:                    31 [ms]
 *   Optimize counter:              28 [-]
 *   Optimize/sec (CPU):          1750 [1/s]
 *   Optimize/sec (Clock):         903 [1/s]
 *   Best solution:         Configuration{attributes=[0, 0, 0, 0, 0, 0, 0, 0,
 * 1, 1, 0, 0, 1, 1, 1], operationHistory={23 items}}
 *   Depth:                         23 [-]
 *   Fitness:                    454,0 [-]
 *   Ended without exception
 * </pre>
 *
 * @author Ondrej Skalicka
 */
public class SimpleRender implements Render {
    /**
     * Prints standard information about every result entry.
     */
    public static final int OUTPUT_STANDARD = 0;
    /**
     * Prints only best solution, CPU time, optimize counter and fitness.
     */
    public static final int OUTPUT_MINI = 1;
    /**
     * Prints full information about every result entry.
     * <p/>
     * This includes all operations in operation history which are not included in {@link #OUTPUT_STANDARD} and also
     * uses {@link cz.cvut.felk.cig.jcop.problem.ConfigurationMap} of current problem to create human-readable values.
     */
    public static final int OUTPUT_FULL = 2;
    /**
     * Output level defined for this render.
     */
    protected int outputLevel;
    /**
     * Stream to print result to.
     * <p/>
     * Default is System.out, but could be replaced with a file or any other.
     */
    protected PrintStream printStream = System.out;

    /**
     * Creates new simple render with given level.
     * <p/>
     * Level can be any OUTPUT_* constant of SimpleRender.
     *
     * @param outputLevel how output should be formatted
     * @throws IllegalArgumentException if invalid level is supplied
     */
    public SimpleRender(int outputLevel) throws IllegalArgumentException {
        switch (outputLevel) {
            case SimpleRender.OUTPUT_STANDARD:
            case SimpleRender.OUTPUT_MINI:
            case SimpleRender.OUTPUT_FULL:
                this.outputLevel = outputLevel;
                break;
            default:
                throw new IllegalArgumentException("Simple render does not recognize outputLevel " + outputLevel);
        }
    }

    /**
     * Creates new simple render with level {@link #OUTPUT_STANDARD}.
     *
     * @throws IllegalArgumentException if invalid level is supplied
     */
    public SimpleRender() {
        this(SimpleRender.OUTPUT_STANDARD);
    }

    /**
     * Creates new simple render with output level and print stream specified.
     *
     * @param outputLevel how output should be formatted
     * @param printStream print stream to write to
     * @throws IllegalArgumentException if invalid level is supplied
     */
    public SimpleRender(int outputLevel, PrintStream printStream) throws IllegalArgumentException {
        this(outputLevel);
        this.printStream = printStream;
    }

    /**
     * Creates new simple render with output level and print stream set to a file.
     * <p/>
     * Note that SimpleRender will overwrite contents of supplied file. If you create several renders with same File,
     * their contents will overwrite each other. In such case, create PrintStream beforehand and use {@link
     * #SimpleRender(int, java.io.PrintStream)} instead.
     *
     * @param outputLevel how output should be formatted
     * @param file        file to write to
     * @throws FileNotFoundException    If the given file object does not denote an existing, writable regular file and
     *                                  a new regular file of that name cannot be created, or if some other error occurs
     *                                  while opening or creating the file
     * @throws IllegalArgumentException if invalid level is supplied
     * @see java.io.PrintStream#PrintStream(java.io.File) print stream from file
     */
    public SimpleRender(int outputLevel, File file) throws FileNotFoundException, IllegalArgumentException {
        this.outputLevel = outputLevel;
        this.printStream = new PrintStream(file);
    }

    /**
     * Creates new simple render with output level and print stream set to a file.
     * <p/>
     * Note that SimpleRender will overwrite contents of supplied file. If you create several renders with same File,
     * their contents will overwrite each other. In such case, create PrintStream beforehand and use {@link
     * #SimpleRender(int, java.io.PrintStream)} instead.
     *
     * @param file        file to write to
     * @throws FileNotFoundException    If the given file object does not denote an existing, writable regular file and
     *                                  a new regular file of that name cannot be created, or if some other error occurs
     *                                  while opening or creating the file
     * @throws IllegalArgumentException if invalid level is supplied
     * @see java.io.PrintStream#PrintStream(java.io.File) print stream from file
     */
    public SimpleRender(File file) throws FileNotFoundException, IllegalArgumentException {
        this.outputLevel = SimpleRender.OUTPUT_STANDARD;
        this.printStream = new PrintStream(file);
    }

    public void render(Result result) {
        for (ResultEntry resultEntry : result.getResultEntries()) {
            switch (this.outputLevel) {
                case SimpleRender.OUTPUT_STANDARD:
                    this.printStandard(resultEntry);
                    break;
                case SimpleRender.OUTPUT_MINI:
                    this.printMini(resultEntry);
                    break;
                case SimpleRender.OUTPUT_FULL:
                    this.printFull(resultEntry);
                    break;
            }
        }
    }

    /**
     * Prints minimal information about entry.
     * <p/>
     * Example:
     * <p/>
     * <pre>
     * === (MyAlgorithm, Knapsack, default) ===
     *   CPU Time: 16 ms. Optimizations: 28. Fitness: 454,0. Ended without
     * exception. Best solution: Configuration{attributes=[0, 0, 0, 0, 0, 0, 0,
     * 0, 1, 1, 0, 0, 1, 1, 1], operationHistory={23 items}}.
     * </pre>
     *
     * @param resultEntry result entry to be printed
     */
    protected void printMini(ResultEntry resultEntry) {
        PreciseTimestamp start = resultEntry.getStartTimestamp();
        PreciseTimestamp stop = resultEntry.getStopTimestamp();

        printStream.println();

        printStream.printf("=== (%s, %s) ===\n", resultEntry.getAlgorithm(), resultEntry.getProblem());
        printStream.printf("  CPU Time: %7d ms. ", start.getCpuTimeSpent(stop));
        printStream.printf("Optimizations: %7d. ", resultEntry.getOptimizeCounter());
        if (resultEntry.getBestConfiguration() != null)
            printStream.printf("Fitness: %7.1f. ", resultEntry.getBestFitness());
        else printStream.printf("Fitness: %s. ", "none");
        printStream.printf("Best solution: %s. ", resultEntry.getBestConfiguration());
        if (resultEntry.getException() == null)
            printStream.println("Ended without exception.");
        else
            printStream.printf("Ended with exception: %s.\n", resultEntry.getException().getClass().getSimpleName());
    }

    /**
     * Prints all information about entry.
     * <p/>
     * Example:
     * <p/>
     * <pre>
     * === Algorithm MyAlgorithm [] used on problem Knapsack, settings default
     * [file=knap_4.txt] ===
     *   CPU Time:                      16 [ms]
     *   System Time:                    0 [ms]
     *   User Time:                     15 [ms]
     *   Clock Time:                    31 [ms]
     *   Optimize counter:              28 [-]
     *   Optimize/sec (CPU):          1750 [1/s]
     *   Optimize/sec (Clock):         903 [1/s]
     *   Best solution:         Configuration{attributes=[0, 0, 0, 0, 0, 0, 0,
     * 0, 1, 1, 0, 0, 1, 1, 1], operationHistory={23 items}}
     *   Depth:                         23 [-]
     *   Fitness:                    454,0 [-]
     *   Operation history:
     *           0. Empty knapsack created
     *           1. AddOperation{knapsackItem=KnapsackItem{index=3, weight=3,
     * price=223}}
     *           2. AddOperation{knapsackItem=KnapsackItem{index=2, weight=88,
     * price=192}}
     *           3. AddOperation{knapsackItem=KnapsackItem{index=1, weight=42,
     * price=136}}
     *   Ended without exception
     * </pre>
     *
     * @param resultEntry result entry to be printed
     */
    protected void printFull(ResultEntry resultEntry) {
        PreciseTimestamp start = resultEntry.getStartTimestamp();
        PreciseTimestamp stop = resultEntry.getStopTimestamp();

        printStream.println();

        printStream.printf("=== Algorithm %s used on problem %s ===\n", resultEntry.getAlgorithm(), resultEntry.getProblem());
        printStream.printf("  CPU Time:              %10d [ms]\n", start.getCpuTimeSpent(stop));
        printStream.printf("  System Time:           %10d [ms]\n", start.getSystemTimeSpent(stop));
        printStream.printf("  User Time:             %10d [ms]\n", start.getUserTimeSpent(stop));
        printStream.printf("  Clock Time:            %10d [ms]\n", start.getClockTimeSpent(stop));

        printStream.printf("  Optimize counter:      %10d [-]\n", resultEntry.getOptimizeCounter());
        printStream.printf("  Optimize/sec (CPU):    %10d [1/s]\n", resultEntry.getOptimizeCounter() * 1000L / start.getCpuTimeSpent(stop));
        printStream.printf("  Optimize/sec (Clock):  %10d [1/s]\n", resultEntry.getOptimizeCounter() * 1000L / start.getClockTimeSpent(stop));


        if (resultEntry.getBestConfiguration() != null) {
            ConfigurationMap map = resultEntry.getProblem().getConfigurationMap();

            printStream.print("  Best solution: [");
            for (int i = 0; i < resultEntry.getBestConfiguration().getDimension(); ++i) {
                printStream.printf("%s%s", i == 0 ? "" : ", ", map.map(resultEntry.getBestConfiguration().valueAt(i), i));
            }
            printStream.println("]");
            printStream.printf("  Depth:                 %10d [-]\n", resultEntry.getBestConfiguration().getOperationHistory().getCounter());
            printStream.printf("  Fitness:               %10.1f [-]\n", resultEntry.getBestFitness());
            printStream.println("  Operation history:");
            for (OperationHistory operationHistory : resultEntry.getBestConfiguration().getOperationHistory().getChronologicalList()) {
                printStream.printf("        %4d. %s\n", operationHistory.getCounter(), operationHistory.getOperation().toString());
            }
        }

        if (resultEntry.getException() == null)
            printStream.println("  Ended without exception");
        else
            printStream.printf("  Ended with exception:  %10s\n", resultEntry.getException());
    }

    /**
     * Prints standard information about entry.
     * <p/>
     * Example:
     * <p/>
     * <pre>
     * === Algorithm MyAlgorithm used on problem Knapsack, settings default ===
     *   CPU Time:                      16 [ms]
     *   System Time:                    0 [ms]
     *   User Time:                     15 [ms]
     *   Clock Time:                    31 [ms]
     *   Optimize counter:              28 [-]
     *   Optimize/sec (CPU):          1750 [1/s]
     *   Optimize/sec (Clock):         903 [1/s]
     *   Best solution:         Configuration{attributes=[0, 0, 0, 0, 0, 0, 0,
     * 0, 1, 1, 0, 0, 1, 1, 1], operationHistory={23 items}}
     *   Depth:                         23 [-]
     *   Fitness:                    454,0 [-]
     *   Ended without exception
     * </pre>
     *
     * @param resultEntry result entry to be printed
     */
    protected void printStandard(ResultEntry resultEntry) {
        PreciseTimestamp start = resultEntry.getStartTimestamp();
        PreciseTimestamp stop = resultEntry.getStopTimestamp();

        printStream.println();

        printStream.printf("=== Algorithm %s used on problem %s ===\n", resultEntry.getAlgorithm(), resultEntry.getProblem());
        printStream.printf("  CPU Time:              %10d [ms]\n", start.getCpuTimeSpent(stop));
        printStream.printf("  System Time:           %10d [ms]\n", start.getSystemTimeSpent(stop));
        printStream.printf("  User Time:             %10d [ms]\n", start.getUserTimeSpent(stop));
        printStream.printf("  Clock Time:            %10d [ms]\n", start.getClockTimeSpent(stop));

        printStream.printf("  Optimize counter:      %10d [-]\n", resultEntry.getOptimizeCounter());
        printStream.printf("  Optimize/sec (CPU):    %10d [1/s]\n", resultEntry.getOptimizeCounter() * 1000 / start.getCpuTimeSpent(stop));
        printStream.printf("  Optimize/sec (Clock):  %10d [1/s]\n", resultEntry.getOptimizeCounter() * 1000 / start.getClockTimeSpent(stop));

        printStream.printf("  Best solution:         %10s\n", resultEntry.getBestConfiguration());

        if (resultEntry.getBestConfiguration() != null) {
            printStream.printf("  Depth:                 %10d [-]\n", resultEntry.getBestConfiguration().getOperationHistory().getCounter());
            printStream.printf("  Fitness:               %10.1f [-]\n", resultEntry.getBestFitness());
        }

        if (resultEntry.getException() == null)
            printStream.println("  Ended without exception");
        else
            printStream.printf("  Ended with exception:  %10s\n", resultEntry.getException());
    }
}
