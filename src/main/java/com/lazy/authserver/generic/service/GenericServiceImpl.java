package com.lazy.authserver.generic.service;

import com.lazy.authserver.generic.api.BaseEntity;
import com.lazy.authserver.generic.api.GenericRepository;
import com.lazy.authserver.generic.api.GenericService;
import com.lazy.authserver.generic.api.GenericSoftDeleteRepository;
import com.lazy.authserver.generic.pojo.GetRowsRequest;
import com.lazy.authserver.utils.NullAwareBeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public abstract class GenericServiceImpl<T extends BaseEntity, ID extends Serializable>
        implements GenericService<T, ID> {

    protected static final Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

    protected static String[] ALL_STATUSES;//= new String[]{ObjectStatusValues.STATUS_ACTIVE,ObjectStatusValues.STATUS_APPROVED,ObjectStatusValues.STATUS_CANCELLED,ObjectStatusValues.STATUS_COMPLETED,ObjectStatusValues.STATUS_EXCEPTION,ObjectStatusValues.STATUS_INELIGIBLE,ObjectStatusValues.STATUS_INVALID,ObjectStatusValues.STATUS_PROPOSED,ObjectStatusValues.STATUS_REJECTED,ObjectStatusValues.STATUS_SUSPENDED,ObjectStatusValues.STATUS_SUBMITTED,ObjectStatusValues.STATUS_TERMINATED,ObjectStatusValues.STATUS_VERIFIED};

    private GenericRepository<T, ID> repository;

    public GenericServiceImpl(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public GenericServiceImpl(GenericSoftDeleteRepository<T, ID> repository) {
        this.repository = repository;
    }

    public static Map<String, List<Object>> scalarListToMap(String[] fields, List<Object[]> result) {
        Map<String, List<Object>> retval = null;
        if (result != null && !result.isEmpty()) {
            retval = new HashMap<String, List<Object>>();
            for (int r = 0; r < result.size(); r++) {
                for (int f = 0; f < fields.length; f++) {
                    if (r == 0) {
                        retval.put(fields[f], new ArrayList<Object>());
                    }
                    List<Object> l = retval.get(fields[f]);
                    if (fields.length > 1) {
                        Object[] vals = (Object[]) result.get(r);
                        l.add(vals[f]);
                    } else {
                        l.add(result.get(r));
                    }
                }
            }
        }
        return retval;
    }

    @Override
    public T getOneActive(Long id) {
        if (repository instanceof GenericSoftDeleteRepository)
            return ((GenericSoftDeleteRepository<T, ID>) repository).getOne((ID) id);
        return null;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findMany(final int firstResult, int maxResults) {
        Pageable pageSpecification = PageRequest.of(firstResult, maxResults > 0 ? maxResults : NUMBER_OF_PERSONS_PER_PAGE);
        return repository.findAll(pageSpecification).getContent();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isExist(ID id) {
        return repository.existsById(id);
    }

    @Override
    public long getRowCount() {
        return repository.count();
    }

    @Override
    public T create(T newInstance) {
        return repository.save(newInstance);
    }

    @Override
    public void update(T transientObject) {
        T update = this.findById((ID) transientObject.getId());
        BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
        try {
            // copy non null data
            beanUtilsBean.copyProperties(update, transientObject); // destination <- source
        } catch (Exception e) {
//			e.printStackTrace();
            throw new RuntimeException("Id doesn't Exists");
        }
        repository.save(update);
    }

    @Override
    public void delete(T persistentObject) {
        repository.delete(persistentObject);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void activeById(ID id) {
        if (repository instanceof GenericSoftDeleteRepository)
            ((GenericSoftDeleteRepository<T, ID>) repository).activeById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void createMany(Collection<T> newInstances) {
        saveMany(newInstances);
    }

    @Override
    public void saveMany(Collection<T> instances) {
        repository.saveAll(instances);
    }

    @Override
    public void updateMany(Collection<T> instances) {
        saveMany(instances);
    }

    @Override
    public List<T> findInactive() {
        if (repository instanceof GenericSoftDeleteRepository)
            return ((GenericSoftDeleteRepository<T, ID>) repository).findInactive();
        else
            return null;
    }

    @Override
    public List<T> findAllWithInactive() {
        if (repository instanceof GenericSoftDeleteRepository)
            return ((GenericSoftDeleteRepository<T, ID>) repository).findAllWithInactive();
        else
            return null;
    }

    @Override
    public boolean checkIdValidity(ID id) {
        return ((GenericSoftDeleteRepository<T, ID>) repository).check(id) == 1 ? true : false;
    }

    @Override
    public void toggle(ID id) {
        if (repository instanceof GenericSoftDeleteRepository)
            ((GenericSoftDeleteRepository<T, ID>) repository).toggle(id);
    }

    @Override
    public Page<T> getPaginated(GetRowsRequest getRowsRequest) {
        return null;
    }
}