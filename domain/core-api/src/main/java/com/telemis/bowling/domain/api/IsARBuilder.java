package com.telemis.bowling.domain.api;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

import java.io.Serializable;

/**
 * Contract for any Aggregrate Root Builder.
 *
 * @since 16/06/14
 */
public interface IsARBuilder<K extends Serializable, T extends AbstractAnnotatedAggregateRoot> extends IsEntityBuilder<T> {

    /**
     * Defines the given identifier as AR identifier.
     *
     * @param aggregateIdentifier The identifier to be assigned to the AR to be constructed.
     * @return The instance of the builder.
     */
    IsARBuilder<K, T> withId(K aggregateIdentifier);

}
