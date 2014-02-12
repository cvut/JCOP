/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.result.Result;

import java.io.IOException;

/**
 * Renders are used to format and render Result of solver to output.
 * <p/>
 * Common outputs are console, HTML, XML, csv, txt.
 *
 * @author Ondrej Skalicka
 */
public interface Render {
    /**
     * Renders result to some output.
     */
    public void render(Result result) throws IOException;
}
