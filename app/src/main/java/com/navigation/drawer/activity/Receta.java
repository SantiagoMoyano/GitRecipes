/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navigation.drawer.activity;

import java.io.InputStream;

/**
 *
 * @author matias
 */
public class Receta {

    String receta;
    String[] pasos;
    String idUnico;
    String descripcion;
    InputStream path;
    String[] ingrediente;

    public Receta() {

    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public InputStream getPath() {
        return path;
    }

    public void setPath(InputStream path) {
        this.path = path;
    }

    public String[] getPasos() {
        return pasos;
    }

    public void setPasos(String[] pasos) {
        this.pasos = pasos;
    }

    public void setIngrediente(String[] ingrediente){
        this.ingrediente=ingrediente;
    }

    public String[] getIngrediente() {
        return ingrediente;
    }

    @Override
    public String toString() {
        return "Receta{" + "receta=" + receta + ", pasos=" + pasos + '}';
    }





}
