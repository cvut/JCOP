/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;

import java.io.IOException;

/**
 * This simple render prints stackTrace for every exception in results.
 *
 * @author Ondrej Skalicka
 */
public class ExceptionRender implements Render {
    public void render(Result result) throws IOException {
        for (ResultEntry resultEntry : result.getResultEntries()) {
            if (resultEntry.getException() != null) {
                System.out.printf("Problem %s/Algorithm %s got Exception %s\n", resultEntry.getProblem(), resultEntry.getAlgorithm(), resultEntry.getException().getClass().getSimpleName());
                resultEntry.getException().printStackTrace();
            }
        }
    }
}
