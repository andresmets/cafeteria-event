package ee.andres.cafeteria.validator;

public interface BusinessValidator<T> {
    boolean validate(T t);
}
