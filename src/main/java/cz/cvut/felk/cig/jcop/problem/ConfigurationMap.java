/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * ConfigurationMap is used if problem requires attributes attributes different type than Integer. Then configurations
 * still uses Integers, but user output should be generated using mapping (for example Integer -> String).
 *
 * @author Ondrej Skalicka
 */
public interface ConfigurationMap {

    /**
     * Maps one attributes value to another representation.
     * <p/>
     * Common representation is String or Integer.
     * <p/>
     * Note that {@link IdentityConfigurationMap} is default mapping which just returns the same value as supplied
     *
     * @param value value of attributes attribute to be mapped
     * @param index index of attributes attribute to be mapped
     * @return mapped object
     * @throws IndexOutOfBoundsException if index is out of bounds
     * @see cz.cvut.felk.cig.jcop.problem.IdentityConfigurationMap default implementation
     */
    public Object map(Integer value, int index) throws IndexOutOfBoundsException;

}