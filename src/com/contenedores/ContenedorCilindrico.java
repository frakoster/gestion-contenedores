package com.contenedores;

import javafx.beans.property.*;

public class ContenedorCilindrico {
    private final IntegerProperty id;
    private final DoubleProperty volumen;
    private final DoubleProperty altura;
    private final DoubleProperty radio;

    public ContenedorCilindrico(int id, double volumen, double altura, double radio) {
        this.id = new SimpleIntegerProperty(id);
        this.volumen = new SimpleDoubleProperty(volumen);
        this.altura = new SimpleDoubleProperty(altura);
        this.radio = new SimpleDoubleProperty(radio);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public double getVolumen() {
        return volumen.get();
    }

    public DoubleProperty volumenProperty() {
        return volumen;
    }

    public double getAltura() {
        return altura.get();
    }

    public DoubleProperty alturaProperty() {
        return altura;
    }

    public double getRadio() {
        return radio.get();
    }

    public DoubleProperty radioProperty() {
        return radio;
    }

    @Override
    public String toString() {
        return "Contenedor{" +
               "id=" + getId() +
               ", volumen=" + getVolumen() +
               ", altura=" + getAltura() +
               ", radio=" + getRadio() +
               '}';
    }
}
