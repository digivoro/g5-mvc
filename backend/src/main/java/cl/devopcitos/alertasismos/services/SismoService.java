package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.repositories.SismoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SismoService {

    private final List<Sismo> sismosProcesados = new ArrayList<>();
    private static final int MAX_SISMOS_PROCESADOS = 30;

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

    // **Nuevo método** para guardar una lista de sismos
    public List<Sismo> saveAll(List<Sismo> sismos) {
        return sismoRepository.saveAll(sismos);
    }

    public boolean sismoYaProcesado(Sismo nuevoSismo) {
        for (Sismo sismoProcesado : sismosProcesados) {
            if (sismoProcesado.getLocalidadId().equals(nuevoSismo.getLocalidadId()) &&
                    sismoProcesado.getFecha().equals(nuevoSismo.getFecha()) &&
                    sismoProcesado.getProfundidad().equals(nuevoSismo.getProfundidad()) &&
                    sismoProcesado.getMagnitud().equals(nuevoSismo.getMagnitud())) {
                return true;
            }
        }
        return false;
    }

    public void agregarSismoProcesado(Sismo nuevoSismo) {
        if (sismosProcesados.size() >= MAX_SISMOS_PROCESADOS) {
            sismosProcesados.remove(0);
        }
        sismosProcesados.add(nuevoSismo);
    }

    public String extraerLocalidad(String refGeografica) {
        if (refGeografica != null && refGeografica.contains(" de ")) {
            return refGeografica.substring(refGeografica.indexOf(" de ") + 4).trim();
        }
        return refGeografica;
    }

}
