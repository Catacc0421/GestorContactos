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
                .id( UUID.randomUUID().toString() ) //Genera un id aleatorio
                .nombre(nombre)
                .apellido(apellido)
                .numeroTelefono(numeroTelefono)
                .cumpleanos(cumpleanos.atStartOfDay()) //Convierte la fecha a LocalDateTime
                .correo(correo)
                .url(url)
                .build();

        contactos.add(contacto);
    }

    /**
     * Método que permite al usuario editar un contacto de su lista de contactos
     * @param id para buscar el contacto
     * @param nombre nombre del contacto
     * @param apellido apellido del contacto
     * @param numeroTelefono telefono del contacto
     * @param url url del contacto
     * @param correo correo electronico del contacto
     * @param cumpleanos cumpleaños del contacto
     * @throws Exception excepción para validar que no hayan campos vacíos
     */

    public void editarContacto(String id, String nombre, String apellido, String numeroTelefono, String url, String correo, LocalDate cumpleanos) throws Exception{

        if(nombre.isEmpty() || apellido.isEmpty() || numeroTelefono.isEmpty() || correo.isEmpty() || url.isEmpty())
            throw new Exception("Todos los campos son obligatorios");

        int posContacto = obtenerContacto(id);

        if(posContacto == -1){
            throw new Exception("No existe el id proporcionado");
        }

        Contacto contactoGuardado = contactos.get(posContacto);
        contactoGuardado.setNombre(nombre);
        contactoGuardado.setApellido(apellido);
        contactoGuardado.setCorreo(correo);
        contactoGuardado.setUrl(url);
        contactoGuardado.setNumeroTelefono(numeroTelefono);
        contactoGuardado.setCumpleanos(cumpleanos.atStartOfDay()); //Convierte la fecha a LocalDateTime

        //Actualiza la nota en la lista de notas
        contactos.set(posContacto, contactoGuardado);
    }
    public void eliminarContacto(String id) throws Exception{
        int posNota = obtenerContacto(id);

        if(posNota == -1){
            throw new Exception("No existe el id proporcionado");
        }

        contactos.remove( contactos.get(posNota) );
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
    private int obtenerContacto(String id){

        for (int i = 0; i < contactos.size(); i++) {
            if( contactos.get(i).getId().equals(id) ){
                return i;
            }
        }

        return -1;
    }

}


