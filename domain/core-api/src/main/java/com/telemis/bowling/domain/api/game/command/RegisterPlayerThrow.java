package com.telemis.bowling.domain.api.game.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;

/**
 * @since 06/07/14
 */
public class RegisterPlayerThrow implements Serializable {

    private static final long serialVersionUID = -5124998863543907835L;

    @TargetAggregateIdentifier
    private String gameId;

    private int numberOfSkittlesDown;
}
