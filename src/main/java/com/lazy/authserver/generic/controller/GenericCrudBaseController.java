package com.lazy.authserver.generic.controller;

import com.lazy.authserver.generic.api.BaseEntity;
import com.lazy.authserver.generic.api.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;


public abstract class GenericCrudBaseController<T extends BaseEntity, ID extends Serializable> extends BaseController {

    @Autowired
    private GenericService<T, ID> genericService;

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'view')")
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> list() {
        List<T> list = genericService.getAll();
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.get_all", moduleName),
                        list)
        );
    }

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'view')")
    @GetMapping(value = "/getAllWithInActive")
    public ResponseEntity<?> listWithInActive() {
        List<T> list = genericService.findAllWithInactive();
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.get_all", moduleName),
                        list)
        );
    }

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'delete')")
    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        genericService.deleteById(id);
        return ResponseEntity.ok(
                successResponse(customMessageSource.get("crud.delete", moduleName),
                        null)
        );
    }

    //    @PreAuthorize("hasPermission(#this.this.permissionName,'view')")
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<?> get(@PathVariable ID id) {
        T t = genericService.findById(id);
        return ResponseEntity.ok(
                successResponse(customMessageSource.get(t == null ? "crud.not_exits" : "crud.get", moduleName),
                        t)
        );
    }
//
//    @PreAuthorize("hasPermission(#this.this.permissionName,'view')")
//    @GetMapping(value = "/getPaginated")
//    public ResponseEntity<?> getPaginated(GetRowsRequest paginatedRequest) throws IOException {
//        Page<T> page = genericService.getPaginated(paginatedRequest);
//        return ResponseEntity.ok(
//                successResponse(customMessageSource.get("crud.get", customMessageSource.get(moduleName)),
//                        page)
//        );
//    }
}