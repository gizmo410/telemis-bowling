package com.telemis.bowling.domain.api;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandExecutionException;

import java.util.concurrent.TimeUnit;

/**
 * Contract that defines a gateway to push commands and metadata to the command bus.
 */
public interface CommandGateway {

    /**
     * Sends the given command but do not wait for the unit of work to finish.
     *
     * @param command The command to be sent.
     */
    void sendAndForget(Object command);

    /**
     * Sends the given command and wait for the unit of work to finish.
     * /!\ Caution /!\ It will keep waiting and could thus lead to a
     *
     * @param command The command to be sent.
     * @param <R>     The return type.
     * @return The return value that is passed when the unit of work has ended.
     */
    <R> R sendAndWait(Object command);

    /**
     * Sends the given command and wait for it to execute.
     * The result of the execution is returned when available.
     * This method will block until a result is available, or the given timeout was reached, or until the Thread is interrupted.
     * When the timeout is reached or the thread is interrupted, this method returns null.
     * If command execution resulted in an exception, it is wrapped in a CommandExecutionException.
     *
     * @param command The command to be sent.
     * @param timeout The amount of time the thread is allows to wait for the result
     * @param unit    The unit in which timeout is expressed
     * @param <R>     The return type.
     * @return The return value that is passed when the unit of work has ended.
     * @throws CommandExecutionException This exception will be thrown whenever the timeout occurs.
     */
    <R> R sendAndWait(Object command, long timeout, TimeUnit unit) throws CommandExecutionException;

    /**
     * Sends the given command, and have the result of the command's execution reported to the given callback.
     *
     * @param command  The command to be sent.
     * @param callback The callback to notify when the command has been processed.
     * @param <R>      The return type.
     */
    <R> void send(Object command, CommandCallback<R> callback);

}
