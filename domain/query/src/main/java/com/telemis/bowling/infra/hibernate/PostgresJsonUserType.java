package com.telemis.bowling.infra.hibernate;

import com.telemis.bowling.query.common.ColumnDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Properties;

/**
 * @author timfulmer
 */
public class PostgresJsonUserType extends CollectionReturningUserType implements ParameterizedType {

    private final ObjectMapper mapper = new ObjectMapper();

    private Class<?> resultClass;
    private Class<?> collectionType;

    /**
     * Return the SQL type codes for the columns mapped by this type. The
     * codes are defined on <tt>java.sql.Types</tt>.
     *
     * @return int[] the typecodes
     * @see java.sql.Types
     */
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    /**
     * The class returned by <tt>nullSafeGet()</tt>.
     *
     * @return Class
     */
    @Override
    public Class returnedClass() {
        if (collectionType == null) {
            return resultClass;
        } else {
            return collectionType;
        }

    }

    /**
     */
    @Override
    public Object nullSafeGet(final ResultSet rs,
                              final String[] names,
                              final SessionImplementor session,
                              final Object owner) throws HibernateException, SQLException {
        if (rs.getObject(names[0]) != null) {
            try {
                final String content = rs.getObject(names[0]).toString();
                if (collectionType != null) {
                    final CollectionType javaType =
                            mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) collectionType, resultClass);
                    return mapper.readValue(content, javaType);
                } else {
                    return mapper.readValue(content, returnedClass());
                }
            } catch (IOException e) {
                throw new HibernateException("Error while getting value from database", e);
            }
        }
        return null;
    }

    /**
     * See http://www.pateldenish.com/2013/05/inserting-json-data-into-postgres-using-jdbc-driver.html
     */
    @Override
    public void nullSafeSet(final PreparedStatement st,
                            final Object value,
                            final int index,
                            final SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
            return;
        }

        try {
            PGobject jsonObject = new PGobject();
            jsonObject.setType(ColumnDefinition.JSON);
            jsonObject.setValue(mapper.writeValueAsString(value));
            st.setObject(index, jsonObject, Types.OTHER);
        } catch (JsonProcessingException e) {
            throw new HibernateException("Error while setting value in database", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object deepCopyValue(final Object value) {
        // only collections are properly deep copied at present, and the superclass handles that
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterValues(final Properties parameters) {
        final String resultClassName = parameters.getProperty("resultClass");
        try {
            resultClass = Class.forName(resultClassName);
        } catch (ClassNotFoundException cfne) {
            throw new HibernateException("resultClass class not found", cfne);
        }

        final String collectionTypeName = parameters.getProperty("collectionType");
        if (collectionTypeName != null) {
            try {
                collectionType = Class.forName(collectionTypeName).asSubclass(Collection.class);
            } catch (ClassNotFoundException cfne) {
                throw new HibernateException("collectionType class not found", cfne);
            } catch (ClassCastException cce) {
                throw new HibernateException("collectionType is not a subclass of Collection", cce);
            }
        }
    }

    public Class<?> getResultClass() {
        return resultClass;
    }

    public Class<?> getCollectionType() {
        return collectionType;
    }
}
