package com.pegadaian.vms;

public class VisitorData {

    private String itemNama, itemPerusahaan, itemTelp, itemEmail, itemTujuan, itemHost, itemFoto, key;

    public VisitorData(String itemNama, String itemPerusahaan, String itemTelp, String itemEmail
            , String itemFoto, String itemTujuan, String itemHost) {

        this.itemNama = itemNama;
        this.itemPerusahaan = itemPerusahaan;
        this.itemTelp = itemTelp;
        this.itemEmail = itemEmail;
        this.itemFoto = itemFoto;
        this.itemTujuan = itemTujuan;
        this.itemHost = itemHost;
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

    public String getItemFoto() {
        return itemFoto;
    }

    public String getItemTujuan() {
        return itemTujuan;
    }

    public String getItemHost() {
        return itemHost;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
