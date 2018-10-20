package hoxo.util;

import com.google.common.collect.Lists;

import java.util.List;

public class Result<T> {
    private T entity;
    private final List<String> errors;

    private Result() {
        errors = Lists.newArrayList();
    }

    public static <T> Result<T> cons() {
        return new Result<>();
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public boolean isSuccessful() {
        return errors.isEmpty() && entity != null;
    }

    public boolean isFailed() {
        return !isSuccessful();
    }

    public List<String> getErrors() {
        return errors;
    }

    public Result<T> addError(String error) {
        errors.add(error);
        return this;
    }
}
