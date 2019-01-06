package com.alexlloyd.response.model;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * Used to wrap json response.
 *
 * The idea behind is for serialization. It can be used to wrap objects to give provide more semantics.
 * E.g. wrapping the Config object so that the Json response becomes:
 * "config": { ...config object }
 *
 * @param <T> Anything.
 */
public class DataWrapper<T> {
    private Map<String, T> properties;

    public DataWrapper(String name, T property) {
        properties = Collections.singletonMap(name, property);
    }

    public DataWrapper(T property) {
        this(property.getClass().getSimpleName().toLowerCase(), property);
    }

    @JsonAnyGetter
    public Map<String, T> getProperties() {
        return properties;
    }
}
