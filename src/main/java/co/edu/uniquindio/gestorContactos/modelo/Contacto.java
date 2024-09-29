package co.edu.uniquindio.gestorContactos.modelo;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contacto {
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String apellido;
    private String numeroTelefono;
    private LocalDateTime cumpleanos;
    private String correo;
    private String url;


}
