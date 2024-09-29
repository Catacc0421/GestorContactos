package co.edu.uniquindio.gestorContactos.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactoPrincipal {
    private final List<Contacto> contactos;

    public ContactoPrincipal() {
        contactos = new ArrayList<>();
    }

    /**
     * Método para crear un contacto nuevo
     * @param nombre nombre del contacto
     * @param apellido apellido del contacto
     * @param numeroTelefono número de teléfono de contacto
     * @param cumpleanos fecha de cumpleaños del contacto
     * @param correo correo electrónico del contacto
     * @param url url de la foto de perfil del contacto
     * @throws Exception si alguno de los campos está vacío
     */
    public void agregarContacto(String nombre, String apellido, String numeroTelefono, LocalDate cumpleanos, String correo, String url) throws Exception {

        if(nombre.isEmpty() || apellido.isEmpty() || numeroTelefono.isEmpty() || correo.isEmpty() || url.isEmpty())
            throw new Exception("Todos los campos son obligatorios");

        Contacto contacto = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .numeroTelefono(numeroTelefono)
                .cumpleanos(cumpleanos.atStartOfDay()) //Convierte la fecha a LocalDateTime
                .correo(correo)
                .url(url)
                .build();

        contactos.add(contacto);
    }
    public ArrayList<String> listarOpciones() {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("NOMBRE");
        categorias.add("TELEFONO");


        return categorias;
    }
    public List<Contacto> listarContactos() {
        return contactos;
    }

}


