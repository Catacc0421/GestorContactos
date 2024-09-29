package co.edu.uniquindio.gestorContactos.modelo;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Contacto {

    private String nombre;
    private String apellido;
    private String numeroTelefono;
    private LocalDateTime cumpleanos;
    private String correo;
    private String url;

    /**
     * Obtiene la diferencia entre la fecha actual y la fecha de cumpleaños
     * @return Diferencia en días, horas, minutos y segundos
     */
    public String obtenerDiferencia() {
        LocalDateTime ahora = LocalDateTime.now();
        long diferencia = Duration.between(ahora, cumpleanos).getSeconds();
        long dias = diferencia / 86400;
        long horas = (diferencia % 86400) / 3600;
        long minutos = ((diferencia % 86400) % 3600) / 60;
        long segundos = ((diferencia % 86400) % 3600) % 60;
        return dias + " días, " + horas + " horas, " + minutos + " minutos, " + segundos + " segundos";
    }

}
