package pl.codepride.dailyadvisor.userservice.service;

import pl.codepride.dailyadvisor.userservice.service.Exceptions.DataRepositoryException;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {

    T create(T t) throws DataRepositoryException;

    void delete(ID id) throws DataRepositoryException;

    List<T> findAll();

    Optional<T> findById(ID id);

    T update(T t) throws DataRepositoryException;
}
