package com.example.classservice.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.classservice.exception.NotFoundException;
import com.example.classservice.model.Course;
import com.example.classservice.model.Module;
import com.example.classservice.service.BlobService;
import com.example.classservice.service.ModuleService;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    private BlobService azureBlobAdapter;

    private final ModuleService module_service;

    public ModuleController(ModuleService moduleService) {
        this.module_service = moduleService;
    }

    @GetMapping("")
    public List<Module> all() {
        return module_service.listAllModules();
    }

    @GetMapping("/{id}")
    EntityModel<Module> one(@PathVariable Integer id) {
        Module module = module_service.getModule(id);
        if (module == null) {
            throw new NotFoundException(id);
        }

        return EntityModel.of(module, linkTo(methodOn(ModuleController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ModuleController.class).all()).withRel("modules"));
    }

    @GetMapping("/course/{id}")
    public List<Module> getAllByCourse(@PathVariable Integer id) {
        return module_service.getCourseModule(id);
    }

    @PostMapping(value = "/course/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Module> add(@RequestParam(value = "file") MultipartFile file,@RequestParam(value="name") String module_name, @RequestParam(value = "details") String details,
            @PathVariable("id") Integer course_id) throws IOException {
        
        String url = azureBlobAdapter.upload(file,course_id);
        Course course = new Course();
        course.setId(course_id);
        Module module = new Module(course, module_name, details, url);

        Module persistedModule = module_service.saveModule(module);

        return ResponseEntity.created(URI.create(String.format("/modules/%s", persistedModule.getId())))
                .body(persistedModule);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id)
            throws URISyntaxException {

        Module module = module_service.getModule(id);
        if (module == null) {
            throw new NotFoundException(id);
        }
        String fileName = module.getModule_link_name();

        ByteArrayResource resource = new ByteArrayResource(azureBlobAdapter
                .getFile(fileName));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + fileName + "\"");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers).body(resource);
    }

    @DeleteMapping("/{id}")
    void deleteModules(@PathVariable Integer id) {
        Module tempModule = module_service.getModule(id);
        azureBlobAdapter.deleteBlob(tempModule.getModule_link_name());
        module_service.deleteModule(id);
    }
}
