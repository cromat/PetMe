package com.example.computer.petme.modules;

/**
 * Created by computer on 18.4.2015..
 */

public class Oglas {
    private int id;
    private String naziv;
    private String opis;
    private int idUser;
    private String datum;
    private String petname;
    private String vrsta;
    private String pasmina;
    private Boolean spol;
    private String velicina;
    private String starost;
    private String boja;
    private String brMob;
    private String zupanija;
    private String mjesto;

    public Oglas() {
    }

    public Oglas(int id, String naziv, String opis, int idUser, String datum, String petname, String vrsta, String pasmina, Boolean spol,
                 String velicina, String starost, String boja, String brMob, String zupanija, String mjesto) {
        this.naziv = naziv;
        this.opis = opis;
        this.petname = petname;
        this.pasmina = pasmina;
        this.velicina = velicina;
        this.boja = boja;
        this.idUser = idUser;
        this.starost = starost;
        this.spol = spol;
        this.vrsta = vrsta;
        this.datum = datum;
        this.id = id;
        this.brMob = brMob;
        this.zupanija = zupanija;
        this.mjesto = mjesto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getPasmina() {
        return pasmina;
    }

    public void setPasmina(String pasmina) {
        this.pasmina = pasmina;
    }

    public Boolean getSpol() {
        return spol;
    }

    public void setSpol(Boolean spol) {
        this.spol = spol;
    }

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        this.velicina = velicina;
    }

    public String getStarost() {
        return starost;
    }

    public void setStarost(String starost) {
        this.starost = starost;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public String getBrMob() {
        return brMob;
    }

    public void setBrMob(String brMob) {
        this.brMob = brMob;
    }

    public String getZupanija() {return zupanija;}

    public void setZupanija(String zupanija) {this.zupanija = zupanija;}

    public String getMjesto() {return mjesto;}

    public void setMjesto(String mjesto) {this.mjesto = mjesto;}
}
