/*
 * @(#)CollectionReturningUserType.java     23 Feb 2009
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

import org.hibernate.HibernateException;

import java.util.Collection;

/**
 * A abstract class for {@link org.hibernate.usertype.UserType UserTypes} that <u>may</u> return mutable collections
 * (but may also return any other object types).
 * <p/>
 * {@link #deepCopy(Object) Deep copies} collections to ensure any changes to the
 * collection are tracked, so the collection is correctly updated. Recursively clones collections
 * that contain collections, but <strong>not safe for collections that contain themselves</strong>.
 *
 * @author anph
 * @since 23 Feb 2009
 */
public abstract class CollectionReturningUserType extends MutableUserType {

    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object deepCopy(final Object value) throws HibernateException {
        /*
         * For non-collections, delegate; for collections, return a new collection containing
         * the members of the initial collection.
         */
        if (!(value instanceof Collection)) {
            return deepCopyValue(value);
        }

        /*
         * Have to use a non-generic form because otherwise there is no way to get the two ? to match
         * in the add call below.
         */
        final Collection<?> collection = (Collection) value;
        final Collection collectionClone = CollectionFactory.newInstance(collection.getClass());

        /*
         * Recurse on the members of the collection (which may themselves be collections!)
         * XXX: will cause an endless loop for collections which contain themselves!
         */
        for (Object member : collection) {
            collectionClone.add(deepCopy(member));
        }

        return collectionClone;
    }

    /**
     * Creates a deep copy of the given value.
     * <p/>
     * See {@link org.hibernate.usertype.UserType#deepCopy(Object)}.
     *
     * @param value the object to be copied
     * @return a deep copy of the given object
     */
    protected abstract Object deepCopyValue(Object value);
}
