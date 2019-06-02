package cat.copernic.meteocleta;

public class clima {
    private String weather;
    private int photoWeather;
    public clima(int clima_nuves)
    {

    }
    public clima(String temperature,int photoWeather){
        this.weather = temperature;
        this.photoWeather = photoWeather;
    }


    public String getTemperature() {
        return weather;
    }

    public void setTemperature(String temperature) {
        this.weather = temperature;
    }


    public int getPhotoWeather() {
        return photoWeather;
    }

    public void setPhotoWeather(int photoWeather) {
        this.photoWeather = photoWeather;
    }
}
