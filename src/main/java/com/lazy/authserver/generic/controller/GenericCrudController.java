package com.lazy.authserver.generic.controller;

import com.lazy.authserver.generic.api.BaseEntity;
import com.lazy.authserver.generic.api.GenericService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;


public abstract class GenericCrudController<T extends BaseEntity, ID extends Serializable> extends GenericCrudBaseController<T, ID> {

    @Autowired
    private GenericService<T, ID> genericService;

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'create')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@Valid @RequestBody T entity) {
        T t = genericService.create(entity);
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.create", moduleName),
                        t.getId())
        );
    }

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'update')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@Valid @RequestBody T entity) {
        genericService.update(entity);
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.update", moduleName),
                        null)
        );
    }

}
