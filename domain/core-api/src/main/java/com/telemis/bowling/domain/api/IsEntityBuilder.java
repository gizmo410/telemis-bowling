package com.telemis.bowling.domain.api;

/**
 * Contract for any entity builder.
 *
 * @since 16/06/14
 */
public interface IsEntityBuilder<T> {

    /**
     * Builds a new instance of the defined type T.
     *
     * @return The new instance of type T.
     */
    T build();

}
