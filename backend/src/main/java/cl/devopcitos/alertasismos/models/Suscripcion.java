package cl.devopcitos.alertasismos.models;

import jakarta.persistence.*;

@Entity
@Table(name="suscripcion")
public class Suscripcion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String localidad;
  private String email;
  private String nombre;
  private Boolean activo;

  public Suscripcion() {}

  public Suscripcion(String localidad, String email, String nombre, Boolean activo) {
    this.localidad = localidad;
    this.email = email;
    this.nombre = nombre;
    this.activo = true;
  }

  public Suscripcion(Long id, String localidad, String email, String nombre, Boolean activo) {
    this.id = id;
    this.localidad = localidad;
    this.email = email;
    this.nombre = nombre;
    this.activo = true;
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
  
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  @Override
  public String toString() {
    return "Suscripcion{" +
      "id=" + id +
      ", localidad='" + localidad + '\'' +
      ", email=" + email +
      ", nombre=" + nombre +
      ", activo=" + activo +
      '}';
  }

}
