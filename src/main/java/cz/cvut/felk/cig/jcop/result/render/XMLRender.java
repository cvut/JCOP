/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.problem.ConfigurationMap;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;
import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;
import org.apache.ecs.xml.XML;
import org.apache.ecs.xml.XMLDocument;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;


/**
 * Renders result into a HTML file.
 * <p/>
 * Root element is result. It contains experiment element (with date) and then one entry for every result entry.
 * <p/>
 * Each entry has several attributes (algorithm, problem, optimize-counter, exception, cpu-, user-, system- and
 * clock-time) and if any result was found, then element best-solution with attribute fitness and elements operations
 * and attributes.
 * <p/>
 * Operations contains several operation elements, each with label ind index. Attributes contains several attribute
 * elements, each with index, value and human-readable-value.
 *
 * @author Ondrej Skalicka
 */
public class XMLRender implements Render {
    /**
     * Stream to print result to.
     */
    protected PrintStream printStream;

    /**
     * Creates render which will write XML to designated file.
     *
     * @param outputFile file to write XML to
     * @throws IOException if cannot create {@link java.io.PrintStream} from file
     */
    public XMLRender(File outputFile) throws IOException {
        this.printStream = new PrintStream(outputFile);
    }

    public void render(Result result) throws IOException {
        XMLDocument doc = new XMLDocument();
        XML root = new XML("result");

        XML experiment = new XML("experiment");
        experiment.addAttribute("date", new Date());
        root.addElement(experiment);

        for (ResultEntry resultEntry : result.getResultEntries()) {
            XML entry = new XML("entry");
            entry.addAttribute("algorithm", resultEntry.getAlgorithm());
            entry.addAttribute("problem", resultEntry.getProblem());
            entry.addAttribute("optimize-counter", resultEntry.getOptimizeCounter());
            entry.addAttribute("exception", resultEntry.getException() == null ? "" : resultEntry.getException().getClass().getSimpleName());

            PreciseTimestamp start = resultEntry.getStartTimestamp();
            PreciseTimestamp stop = resultEntry.getStopTimestamp();

            entry.addAttribute("cpu-time", start.getCpuTimeSpent(stop));
            entry.addAttribute("system-time", start.getSystemTimeSpent(stop));
            entry.addAttribute("user-time", start.getUserTimeSpent(stop));
            entry.addAttribute("clock-time", start.getClockTimeSpent(stop));

            if (resultEntry.getBestConfiguration() != null) {
                XML solution = new XML("best-solution");

                solution.addAttribute("fitness", resultEntry.getBestFitness());

                XML operations = new XML("operations");

                for (OperationHistory operationHistory : resultEntry.getBestConfiguration().getOperationHistory().getChronologicalList()) {
                    XML operation = new XML("operation");
                    operation.addAttribute("label", operationHistory.getOperation().toString());
                    operation.addAttribute("index", operationHistory.getCounter());
                    operations.addElement(operation);
                }
                solution.addElement(operations);

                // configuration attributes
                XML attributes = new XML("attributes");
                ConfigurationMap configurationMap = resultEntry.getProblem().getConfigurationMap();
                for (int i = 0; i < resultEntry.getBestConfiguration().getDimension(); ++i) {
                    Integer integer = resultEntry.getBestConfiguration().valueAt(i);
                    XML attribute = new XML("attribute");
                    attribute.addAttribute("index", i);
                    attribute.addAttribute("value", integer);
                    attribute.addAttribute("human-readable-value", configurationMap.map(integer, i));
                    attributes.addElement(attribute);
                }

                solution.addElement(attributes);

                entry.addElement(solution);
            }

            root.addElement(entry);
        }

        doc.addElement(root);

        doc.output(this.printStream);
    }
}
