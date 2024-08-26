package org.george.car.service.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class TemplateUtil<T> {

    public T get(Optional<T> opt) {
        T entity = null;
        if (opt.isPresent()) {
            entity = opt.get();
        }
        return entity;
    }

    public List<T> list(Iterable<T> iterableList) {
        return StreamSupport.stream(iterableList.spliterator(), true).toList();
    }
}