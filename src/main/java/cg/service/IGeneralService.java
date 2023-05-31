package cg.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<E,T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Boolean existById(Long id);

    T save(E e);

    void delete(E e);

    void deleteById(Long id);
}
