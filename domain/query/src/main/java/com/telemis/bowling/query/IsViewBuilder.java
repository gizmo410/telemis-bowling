package com.telemis.bowling.query;

import com.telemis.bowling.domain.api.IsEntityBuilder;

import java.io.Serializable;

/**
 * Contract for any View Builder.
 *
 * @since 16/06/14
 */
public interface IsViewBuilder<K extends Serializable, T> extends IsEntityBuilder<T> {

    /**
     * Defines the given identifier as View identifier.
     *
     * @param identifier The identifier to be assigned to the View to be constructed.
     * @return The instance of the builder.
     */
    IsViewBuilder<K, T> withId(K identifier);

}
