package hoxo.math.converter;

public interface Converter<F, T> {
    T convert(F object);
}
