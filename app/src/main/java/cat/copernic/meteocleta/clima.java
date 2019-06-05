package cat.copernic.meteocleta;

public class clima {
    private float temperatura, velocitatVent, pressioAtmosferica, humitat;
    private String pluja;
    public clima() {
        this.temperatura = temperatura;
        this.velocitatVent = velocitatVent;
        this.pressioAtmosferica = pressioAtmosferica;
        this.humitat = humitat;
        this.pluja = pluja;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public float getPressioAtmosferica() {
        return pressioAtmosferica;
    }

    public float getVelocitatVent() {
        return velocitatVent;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getHumitat() {
        return humitat;
    }

    public String getPluja() {
        return pluja;
    }

    public void setVelocitatVent(float velocitatVent) {
        this.velocitatVent = velocitatVent;
    }

    public void setPressioAtmosferica(float pressioAtmosferica) {
        this.pressioAtmosferica = pressioAtmosferica;
    }

    public void setHumitat(float humitat) {
        this.humitat = humitat;
    }

    public void setPluja(String pluja) {
        this.pluja = pluja;
    }
}
