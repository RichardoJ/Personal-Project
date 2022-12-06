package com.example.classservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.classservice.model.Module;
import com.example.classservice.repository.ModuleRepository;

@Service
@Transactional
public class ModuleService {
    private final ModuleRepository modulesrepo;

    public ModuleService(ModuleRepository modulesRepository){
        this.modulesrepo = modulesRepository;
    }


    public List<Module> listAllModules(){
        return modulesrepo.findAll();
    }

    public Module saveModule(Module module){
        return modulesrepo.save(module);
    }

    public Module getModule(Integer id) {
        Optional<Module> tmp = modulesrepo.findById(id);
        if(tmp.isPresent()){
            return tmp.get();
        }else{
            return null;
        }
    }

    public List<Module> getCourseModule(Integer course_id){
        List<Module> tmp = modulesrepo.findModulesByCourseId(course_id);
        if(tmp.isEmpty()){
            return null;
        }else{
            return tmp;
        }
    }

    public void deleteModule(Integer id){
        modulesrepo.deleteById(id);
    }

}
