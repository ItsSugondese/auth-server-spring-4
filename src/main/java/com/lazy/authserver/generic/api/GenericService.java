package com.lazy.authserver.generic.api;

import com.lazy.authserver.generic.pojo.GetRowsRequest;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface GenericService<T extends BaseEntity, ID extends Serializable> {
    int NUMBER_OF_PERSONS_PER_PAGE = 10;

    public T findById(ID id);

    public boolean isExist(ID id);

    public List<T> getAll();

    public List<T> findMany(int firstResult, int maxResults);

    public long getRowCount();

    public abstract T create(T newInstance);

    public abstract void update(T instance);

    public abstract void delete(T persistentObject);

    public abstract void deleteById(ID id);

    public abstract void activeById(ID id);

    public abstract void deleteAll();

    public abstract void createMany(Collection<T> newInstances);

    public abstract void saveMany(Collection<T> instances);

    public abstract void updateMany(Collection<T> instances);

    public abstract List<T> findInactive();

    public abstract List<T> findAllWithInactive();

    public abstract T getOneActive(Long id);

    public abstract Page<T> getPaginated(GetRowsRequest getRowsRequest);

    public abstract boolean checkIdValidity(ID id);

    public abstract void toggle(ID id);


}
