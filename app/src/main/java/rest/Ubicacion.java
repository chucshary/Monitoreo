package rest;

import android.text.method.DateTimeKeyListener;

import java.text.DateFormat;

/**
 * Created by Shary on 18/10/2015.
 */
public class Ubicacion {
    private int UbicacionId;
    private String Pais;
    private String Estado;
    private String Ciudad;
    private String Direccion;
    private String Latitud;
    private String Longitud;
    private DateFormat Fecha;
    private int PacienteId;

    public int getUbicacionId() {
        return UbicacionId;
    }

    public void setUbicacionId(int ubicacionId) {
        UbicacionId = ubicacionId;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public DateFormat getFecha() {
        return Fecha;
    }

    public void setFecha(DateFormat fecha) {
        Fecha = fecha;
    }

    public int getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(int pacienteId) {
        PacienteId = pacienteId;
    }
}
