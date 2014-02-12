/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import com.csvreader.CsvWriter;
import cz.cvut.felk.cig.jcop.problem.ConfigurationMap;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;
import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;

import java.io.*;
import java.nio.charset.Charset;

/**
 * CSVRender writes results in CSV format to a file, console or other stream.
 * <p/>
 * Uses CsvWriter to write into .csv file.
 *
 * @author Ondrej Skalicka
 * @see com.csvreader.CsvWriter
 */
public class CSVRender implements Render {
    /**
     * Stream to print result to.
     * <p/>
     * Default is System.out, but could be replaced with a file or any other.
     */
    protected OutputStream outputStream = System.out;
    /**
     * Delimiter in CSV.
     */
    protected char delimiter = ',';
    /**
     * Charset to use in output file
     */
    protected Charset charset = Charset.forName("UTF-8");

    /**
     * Creates CSVRender which prints to System.out.
     */
    public CSVRender() {
        this(System.out);
    }

    /**
     * Creates CSVRender which writes to given print stream.
     *
     * @param outputStream stream to write to
     */
    public CSVRender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Creates CSVRender which writes to given file.
     *
     * @param file file to write to
     * @throws FileNotFoundException if file is not found
     */
    public CSVRender(File file) throws FileNotFoundException {
        this(new PrintStream(file));
    }

    public void render(Result result) throws IOException {
        CsvWriter csvWriter = new CsvWriter(this.outputStream, this.delimiter, this.charset);

        // basics
        csvWriter.write("Algorithm");
        csvWriter.write("Problem");
        // times
        csvWriter.write("CPU Time [ms]");
        csvWriter.write("System Time [ms]");
        csvWriter.write("User Time [ms]");
        csvWriter.write("Clock Time [ms]");
        // optimize stats
        csvWriter.write("Optimize counter");
        csvWriter.write("Optimize/sec (CPU) [1/s]");
        csvWriter.write("Optimize/sec (Clock) [1/s]");
        // solution
        csvWriter.write("Exception");
        csvWriter.write("Best solution");
        csvWriter.write("Best solution (human readable)");
        csvWriter.write("Depth");
        csvWriter.write("Fitness");
        csvWriter.write("Operations");

        csvWriter.endRecord();

        for (ResultEntry resultEntry : result.getResultEntries()) {
            PreciseTimestamp start = resultEntry.getStartTimestamp();
            PreciseTimestamp stop = resultEntry.getStopTimestamp();

            // basics
            csvWriter.write(resultEntry.getAlgorithm().toString());
            csvWriter.write(resultEntry.getProblem().toString());
            // times
            csvWriter.write(Long.toString(start.getCpuTimeSpent(stop)));
            csvWriter.write(Long.toString(start.getSystemTimeSpent(stop)));
            csvWriter.write(Long.toString(start.getUserTimeSpent(stop)));
            csvWriter.write(Long.toString(start.getClockTimeSpent(stop)));
            // optimize stats
            csvWriter.write(Long.toString(resultEntry.getOptimizeCounter()));
            csvWriter.write(Long.toString(resultEntry.getOptimizeCounter() * 1000L / start.getCpuTimeSpent(stop)));
            csvWriter.write(Long.toString(resultEntry.getOptimizeCounter() * 1000L / start.getClockTimeSpent(stop)));

            csvWriter.write(resultEntry.getException() == null ? "none" : resultEntry.getException().getClass().toString());

            // solution
            if (resultEntry.getBestConfiguration() == null) {
                csvWriter.write("none");
                csvWriter.write("-");
                csvWriter.write("-");
                csvWriter.write("-");
            } else {
                StringBuffer stringBufferHumanReadable = new StringBuffer("[");
                StringBuffer stringBuffer = new StringBuffer("[");
                ConfigurationMap configurationMap = resultEntry.getProblem().getConfigurationMap();
                for (int i = 0; i < resultEntry.getBestConfiguration().getDimension(); ++i) {
                    stringBuffer.append(i == 0 ? "" : ", ").append(configurationMap.map(resultEntry.getBestConfiguration().valueAt(i), i));
                    stringBufferHumanReadable.append(i == 0 ? "" : ", ").append(resultEntry.getBestConfiguration().valueAt(i));
                }
                stringBuffer.append("]");
                stringBufferHumanReadable.append("]");
                csvWriter.write(stringBuffer.toString());
                csvWriter.write(stringBufferHumanReadable.toString());
                csvWriter.write(Long.toString(resultEntry.getBestConfiguration().getOperationHistory().getCounter()));
                csvWriter.write(Double.toString(resultEntry.getBestFitness()));

                for (OperationHistory operationHistory : resultEntry.getBestConfiguration().getOperationHistory().getChronologicalList()) {
                    csvWriter.write(operationHistory.getOperation().toString());
                }
            }


            csvWriter.endRecord();
        }


        csvWriter.flush();
    }

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
