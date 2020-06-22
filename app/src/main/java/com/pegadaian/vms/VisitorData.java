package com.pegadaian.vms;

public class VisitorData {

    private String itemNama, itemPerusahaan, itemTelp, itemEmail, itemTujuan, itemHost, itemFoto, itemQr, itemCheckin, itemCheckout, key;

    public VisitorData() {
    }

    public VisitorData(String itemNama, String itemPerusahaan, String itemTelp, String itemEmail
            , String itemTujuan, String itemHost, String itemCheckin, String itemCheckout, String itemFoto, String itemQr) {

        this.itemNama = itemNama;
        this.itemPerusahaan = itemPerusahaan;
        this.itemTelp = itemTelp;
        this.itemEmail = itemEmail;
        this.itemTujuan = itemTujuan;
        this.itemHost = itemHost;
        this.itemCheckin = itemCheckin;
        this.itemCheckout = itemCheckout;
        this.itemFoto = itemFoto;
        this.itemQr = itemQr;
    }

    public String getItemNama() {
        return itemNama;
    }

    public String getItemPerusahaan() {
        return itemPerusahaan;
    }

    public String getItemTelp() {
        return itemTelp;
    }

    public String getItemEmail() {
        return itemEmail;
    }

    public String getItemTujuan() {
        return itemTujuan;
    }

    public String getItemHost() {
        return itemHost;
    }

    public String getItemCheckin() {
        return itemCheckin;
    }

    public String getItemCheckout() {
        return itemCheckout;
    }

    public String getItemFoto() {
        return itemFoto;
    }

    public String getItemQr() {
        return itemQr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
