package com.alexlloyd.configservice.model;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Overall wrapper of all Responses to make them consistent.
 *
 * @param <T> Anything.
 */
@JsonInclude(value = Include.NON_NULL)
public class Response<T> {
    private final ResponseType type;
    private final T data;
    private final Collection<Exception> errors;

    /**
     * Constructor.
     * @param type the type of response. Was the API call successful?
     * @param data the data this response will hold.
     */
    private Response(ResponseType type, T data) {
        this.type = type;
        this.data = data;
        this.errors = null;
    }

    private Response(ResponseType type, Collection<Exception> errors) {
        this.type = type;
        this.data = null;
        this.errors = errors;
    }

    @JsonGetter(value = "response")
    public ResponseType getType() {
        return this.type;
    }

    @JsonGetter(value = "data")
    public T getData() {
        return this.data;
    }

    @JsonGetter(value = "errors")
    public Collection<Exception> getErrors() {
        return this.errors;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseType.SUCCESS, data);
    }

    public static Response<String> error(Collection<Exception> exceptions) {
        return new Response<>(ResponseType.ERROR, exceptions);
    }

    public static Response error(Exception exception) {
        return error(Collections.singleton(exception));
    }

    public static Response failure(Collection<Exception> exceptions) {
        return new Response(ResponseType.FAILURE, exceptions);
    }

    public static Response failure(Exception exception) {
        return failure(Collections.singleton(exception));
    }
}
