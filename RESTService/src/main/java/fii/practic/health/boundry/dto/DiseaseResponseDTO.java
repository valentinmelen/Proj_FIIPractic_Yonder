package fii.practic.health.boundry.dto;

import java.util.List;

public class DiseaseResponseDTO {
    private Latest latest;
    private List<Location> locations;

    public Latest getLatest() {
        return latest;
    }

    public void setLatest(Latest latest) {
        this.latest = latest;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
