package com.dimar.frontend;

public class StrategiDefensif implements StrategiBertarung{
    private String modeBertarung;

    public StrategiDefensif() {
        modeBertarung = "Defensif";
    }

    @Override
    public void bertarung() {
        System.out.println("Sedang dalam mode Defensif.");
    }

    @Override
    public String getModeBertarung() {
        return modeBertarung;
    }
}
