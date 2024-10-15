package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.services.SismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sismos")
public class SismoController {

    @Autowired
    private SismoService sismoService;

    @GetMapping
    public List<Sismo> getAllSismos(){
        return sismoService.getAllSismos();
    }

    @PostMapping
    public Sismo createSismo(@RequestBody Sismo sismo){
        return sismoService.createSismo(sismo);
    }

    @GetMapping("/{id}")
    public Sismo oneSismo(@PathVariable Long id){
        return sismoService.getSismoById(id);
    }

    @PutMapping("/{id}")
    public Sismo updateSismo(@PathVariable Long id, @RequestBody Sismo sismo){
        Sismo updatedSismo = sismoService.getSismoById(id);
        updatedSismo.setLocalidad(sismo.getLocalidad());
        updatedSismo.setMagnitud(sismo.getMagnitud());
        return sismoService.createSismo(updatedSismo);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSismo(@PathVariable Long id){
        sismoService.deleteSismo(id);
        return true;
    }

}
