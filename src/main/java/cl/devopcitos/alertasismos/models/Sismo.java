package cl.devopcitos.alertasismos.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="sismo")
public class Sismo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String localidad;
    private LocalDateTime fecha;
    private Double profundidad;
    private Double magnitud;

    public Sismo() {
    }

    public Sismo(String localidad, LocalDateTime fecha, Double profundidad, Double magnitud) {
        this.localidad = localidad;
        this.fecha = fecha;
        this.profundidad = profundidad;
        this.magnitud = magnitud;
    }

    public Sismo(Long id, String localidad, LocalDateTime fecha, Double profundidad, Double magnitud) {
        this.id = id;
        this.localidad = localidad;
        this.fecha = fecha;
        this.profundidad = profundidad;
        this.magnitud = magnitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(Double magnitud) {
        this.magnitud = magnitud;
    }

    @Override
    public String toString() {
        return "Sismo{" +
                "id=" + id +
                ", localidad='" + localidad + '\'' +
                ", fechaSismo=" + fecha +
                ", profundidad=" + profundidad +
                ", magnitud=" + magnitud +
                '}';
    }
}
