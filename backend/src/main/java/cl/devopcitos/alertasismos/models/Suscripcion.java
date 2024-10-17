package cl.devopcitos.alertasismos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "suscripcion")
public class Suscripcion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Relación con la tabla Localidad, una suscripción está asociada a una sola localidad
  @ManyToOne
  @JoinColumn(name = "localidad_id", referencedColumnName = "id")
  private Localidad localidad;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String nombre;

  @Column
  private Boolean activo;

  public Suscripcion() {
  }

  public Suscripcion(Localidad localidad, String email, String nombre, Boolean activo) {
    this.localidad = localidad;
    this.email = email;
    this.nombre = nombre;
    this.activo = activo;
  }

  public Suscripcion(Long id, Localidad localidad, String email, String nombre, Boolean activo) {
    this.id = id;
    this.localidad = localidad;
    this.email = email;
    this.nombre = nombre;
    this.activo = activo;
  }

  // Getters y Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Localidad getLocalidad() {
    return localidad;
  }

  public void setLocalidad(Localidad localidad) {
    this.localidad = localidad;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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
            ", localidad='" + localidad.getNombre() + '\'' +
            ", email=" + email +
            ", nombre=" + nombre +
            ", activo=" + activo +
            '}';
  }
}
