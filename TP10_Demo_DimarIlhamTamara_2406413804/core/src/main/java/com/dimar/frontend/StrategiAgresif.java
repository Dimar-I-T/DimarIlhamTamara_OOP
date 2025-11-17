package com.dimar.frontend;

public class StrategiAgresif implements StrategiBertarung {
    private String modeBertarung;

    public StrategiAgresif() {
        modeBertarung = "Agresif";
    }

    @Override
    public void bertarung() {
        System.out.println("Sedang dalam mode Agresif.");
    }

    @Override
    public String getModeBertarung() {
        return modeBertarung;
    }
}
