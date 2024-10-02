package co.edu.uniquindio.gestorContactos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if(nombre.isEmpty() || apellido.isEmpty() || numeroTelefono.isEmpty() || correo.isEmpty() || url.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }
        if (!esCorreoValido(correo)) {
            throw new Exception("El formato del correo no es válido");
        }
        if (!esNumeroValido(numeroTelefono)) {
            throw new Exception("El formato del teléfono no es válido, solo puede contener números");
        }
        if (existeContacto(nombre, apellido, numeroTelefono)) {
            throw new Exception("Otro contacto con el mismo nombre y número ya existe");
        }
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
        if (!esCorreoValido(correo)) {
            throw new Exception("El formato del correo no es válido");
        }
        if (!esNumeroValido(numeroTelefono)) {
            throw new Exception("El formato del teléfono no es válido, solo puede contener números");
        }

        Contacto contactoGuardado = contactos.get(posContacto);
        contactoGuardado.setNombre(nombre);
        contactoGuardado.setApellido(apellido);
        contactoGuardado.setCorreo(correo);
        contactoGuardado.setUrl(url);
        contactoGuardado.setNumeroTelefono(numeroTelefono);
        contactoGuardado.setCumpleanos(cumpleanos.atStartOfDay()); //Convierte la fecha a LocalDateTime

        //Actualiza la nota en la lista de contactos
        contactos.set(posContacto, contactoGuardado);
    }

    /**
     * Método para eliminar un contacto de la lista de contactos
     * @param id se busca con el id que se creó aleatoriamente para cada contacto
     * @throws Exception si no encuentra el contacto lanza excepcion
     */
    public void eliminarContacto(String id) throws Exception{
        int posNota = obtenerContacto(id);

        if(posNota == -1){
            throw new Exception("No existe el id proporcionado");
        }

        contactos.remove( contactos.get(posNota) );
    }

    /**
     * Método para listar las opciones que aparecen en el comboBox del fxml y el controlador
     * @return retorna un arraylist con las dos categorias que existen para el filtro de busqueda
     */
    public ArrayList<String> listarOpciones() {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("NOMBRE");
        categorias.add("TELEFONO");


        return categorias;
    }

    /**
     * Método para listar los contactos
     * @return retorna la lista de contactos
     */
    public List<Contacto> listarContactos() {
        return contactos;
    }

    /**
     * Busca un contacto en la lista de contactos
     * @param id se busca el contacto con el id que se creo aleatoriamente
     * @return retorno
     */
    private int obtenerContacto(String id){

        for (int i = 0; i < contactos.size(); i++) {
            if( contactos.get(i).getId().equals(id) ){
                return i;
            }
        }

        return -1;
    }

    public List<Contacto> buscarContactos(String filtro, String valorBusqueda) throws Exception {
        List<Contacto> contactosFiltrados = new ArrayList<>();

        if (valorBusqueda.isEmpty()) {
            throw new Exception("El valor de búsqueda no puede estar vacío");
        }

        for (Contacto contacto : contactos) {
            switch (filtro.toUpperCase()) {
                case "NOMBRE":
                    if (contacto.getNombre().toLowerCase().contains(valorBusqueda.toLowerCase())) {
                        contactosFiltrados.add(contacto);
                    }
                    break;
                case "TELEFONO":
                    if (contacto.getNumeroTelefono().contains(valorBusqueda)) {
                        contactosFiltrados.add(contacto);
                    }
                    break;
                default:
                    throw new Exception("Filtro no válido");
            }
        }

        return contactosFiltrados;
    }

    /**
     * Verifica que el correo que se ingresó esté en el formato válido
     * @param correo el correo que se ingresa al formulario
     * @return retorno
     */
    private boolean esCorreoValido(String correo) {
        // Patrón de regex para correos electrónicos
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    private boolean esNumeroValido(String numeroTelefono) {
        // Verifica si el número tiene exactamente 10 caracteres
        if (numeroTelefono.length() != 10) {
            return false;
        }

        // Verifica si todos los caracteres son dígitos (del 0 al 9)
        for (int i = 0; i < numeroTelefono.length(); i++) {
            char c = numeroTelefono.charAt(i);
            if (c < '0' || c > '9') {
                return false; // Si encuentra algún carácter que no es dígito, retorna falso
            }
        }

        // Si pasa todas las verificaciones, es un número válido
        return true;
    }
    private boolean existeContacto(String nombre, String apellido, String numeroTelefono) {
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)
                    && contacto.getApellido().equalsIgnoreCase(apellido)
                    && contacto.getNumeroTelefono().equals(numeroTelefono)) {
                return true;
            }
        }
        return false;
    }

}


