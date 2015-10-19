package rest;

/**
 * Created by Shary on 18/10/2015.
 */
public class Paciente {
    private Integer PacienteId;
    private String Nombre;
    private String Telefono;
    private String Direccion;
    private int Edad;
    private String Etapa;
    private Integer NombreFoto;
    private String UrlFoto;
    private String FotoBase64;
    private String TutorId;

    public Integer getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        PacienteId = pacienteId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public String getEtapa() {
        return Etapa;
    }

    public void setEtapa(String etapa) {
        Etapa = etapa;
    }

    public Integer getNombreFoto() {
        return NombreFoto;
    }

    public void setNombreFoto(Integer nombreFoto) {
        NombreFoto = nombreFoto;
    }

    public String getUrlFoto() {
        return UrlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        UrlFoto = urlFoto;
    }

    public String getFotoBase64() {
        return FotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        FotoBase64 = fotoBase64;
    }

    public String getTutorId() {
        return TutorId;
    }

    public void setTutorId(String tutorId) {
        TutorId = tutorId;
    }
}
