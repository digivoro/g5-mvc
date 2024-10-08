package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.repositories.SismoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SismoService {

    @Autowired
    private SismoRepository sismoRepository;

    //create
    public Sismo createSismo(Sismo sismo){
        return sismoRepository.save(sismo);
    }

    //read
    public List<Sismo> getAllSismos(){
        return sismoRepository.findAll();
    }

    public Sismo getSismoById(Long id){
        Optional<Sismo> sismo = sismoRepository.findById(id);
        return sismo.orElse(null);
    }

    //update
    public Sismo updateSismo(Long id, Sismo sismo){
        Sismo newSismo = getSismoById(id);
        if (newSismo != null){
            newSismo.setLocalidad(sismo.getLocalidad());
            newSismo.setMagnitud(sismo.getMagnitud());
            newSismo.setFecha(sismo.getFecha());
            newSismo.setProfundidad(sismo.getProfundidad());
            return sismoRepository.save(newSismo);
        }
        return null;
    }

    //delete
    public boolean deleteSismo(Long id){
        if (sismoRepository.existsById(id)) {
            sismoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
