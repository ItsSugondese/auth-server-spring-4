package com.lazy.authserver.generic.controller;

import com.lazy.authserver.generic.api.BaseEntity;
import com.lazy.authserver.generic.api.GenericService;
import com.lazy.authserver.generic.pojo.ActiveToggle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;


public abstract class GenericSoftDeleteBaseController<T extends BaseEntity, ID extends Serializable> extends GenericCrudController<T, ID> {

    @Autowired
    private GenericService<T, Long> genericService;

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'update')")
    @PutMapping(value = "/toggle")
    public ResponseEntity<?> toggle(@RequestBody ActiveToggle data) {
        if (data.isStatus())
            genericService.activeById(data.getId());
        else
            genericService.deleteById(data.getId());
        return ResponseEntity.ok(
                successResponse(customMessageSource.get(data.isStatus() ? "crud.active" : "crud.inactive", moduleName),
                        null)
        );
    }

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'update')")
    @PutMapping(value = "/toggle/{id}")
    public ResponseEntity<?> toggle(@PathVariable(value = "id") Long id) {
        genericService.toggle(id);
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.active", moduleName),
                        null)
        );
    }
}
