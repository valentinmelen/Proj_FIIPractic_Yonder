package fii.practic.health.boundry.dto;

public class Latest {
    private Integer confirmed;
    private Integer deaths;
    private Integer recovered;

    public synchronized Integer getConfirmed() {
        return confirmed;
    }

    public synchronized void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public synchronized Integer getRecovered() {
        return recovered;
    }

    public synchronized void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }
}
