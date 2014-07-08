/*
 * @(#)CollectionFactory.java     8 Apr 2009
 *
 * Copyright © 2009 Andrew Phillips.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.telemis.bowling.infra.hibernate;

import java.util.*;

/**
 * A factory for {@link java.util.Collection Collections}.
 *
 * @author aphillips
 * @since 8 Apr 2009
 */
public class CollectionFactory {

    /**
     * Creates a new collection instance. The runtime collection subtype of the returned
     * instance matches that of the input collection, but the actual class may differ.
     * <p/>
     * So for a {@link java.util.LinkedList} argument, the result is guaranteed to be a
     * {@link java.util.List}. It may, however, be an {@link java.util.ArrayList} rather than a {@code LinkedList}.
     *
     * @param <E>             the type of the collection elements
     * @param <T>             the type of the collection
     * @param collectionClass the runtime class of the collection
     * @return an instance of a collection that is of the same collection subtype as the
     *         given class
     * @throws IllegalArgumentException if the collection type is not supported
     */
    @SuppressWarnings("unchecked")
    static <E, T extends Collection<E>> T newInstance(final Class<T> collectionClass) {

        if (List.class.isAssignableFrom(collectionClass)) {
            return (T) new ArrayList<E>();
        } else if (Set.class.isAssignableFrom(collectionClass)) {
            return (T) new HashSet<E>();
        } else {
            throw new IllegalArgumentException("Unsupported collection type: " + collectionClass);
        }

    }

}
