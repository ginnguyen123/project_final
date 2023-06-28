package cg.service;

public interface ExistService {
    boolean isValidService(Class<?> clazz);
    boolean exist(Object id);
}
