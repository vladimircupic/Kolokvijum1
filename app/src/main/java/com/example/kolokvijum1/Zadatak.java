package com.example.kolokvijum1;

public class Zadatak {

    private String naziv;
    private String vreme;

    public Zadatak(String naziv, String vreme) {
        this.naziv = naziv;
        this.vreme = vreme;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVreme() {
        return vreme;
    }
}
