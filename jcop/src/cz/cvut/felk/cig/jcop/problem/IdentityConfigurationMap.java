/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Default mapping for configurations, used if no other is explicitly specified.
 *
 * @author Ondrej Skalicka
 */
public class IdentityConfigurationMap implements ConfigurationMap {
    /**
     * Returns simply copy of value, ignores index.
     *
     * @param value value of attributes attribute to be mapped
     * @param index ignored
     * @return mapped object as Integer
     */
    public Integer map(Integer value, int index) {
        return value;
    }

}