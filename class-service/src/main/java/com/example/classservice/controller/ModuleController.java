package com.example.classservice.controller;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classservice.exception.NotFoundException;
import com.example.classservice.model.Module;
import com.example.classservice.service.ModuleService;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService module_service;

    public ModuleController(ModuleService moduleService){
        this.module_service = moduleService;
    }

    @GetMapping("")
    public List<Module> all() {
        return module_service.listAllModules();
    }

    @GetMapping("/{id}")
    EntityModel<Module> one(@PathVariable Integer id) {
        Module module = module_service.getModule(id);
        if(module == null){
            throw new NotFoundException(id);
        }

        return EntityModel.of(module, linkTo(methodOn(ModuleController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ModuleController.class).all()).withRel("modules"));
    }

    @GetMapping("/course/{id}")
    public List<Module> getAllByCourse(@PathVariable Integer id){
        return module_service.getCourseModule(id);
    }

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Module> add(@RequestBody Module module) {
        Module persistedModule = module_service.saveModule(module);

        return ResponseEntity.created(URI.create(String.format("/modules/%s", persistedModule.getModules_name())))
                .body(persistedModule);
    }

    @DeleteMapping("/{id}")
    void deleteModules(@PathVariable Integer id) {
        module_service.deleteModule(id);
    }
}
