package ee.andres.cafeteria.service;

public interface CommonService<T>{
    void save(T t);
    void update(T t);
    void delete(T t);
}
