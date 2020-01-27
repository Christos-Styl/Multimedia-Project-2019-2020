package gr.ntua.multimediaproject.dockingstations;

import java.util.Objects;

public class DockingSpace {
    private String id;
    private DockingSpaceState dockingSpaceState;

    public DockingSpace() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DockingSpaceState getDockingSpaceState() {
        return dockingSpaceState;
    }

    public void setDockingSpaceState(DockingSpaceState dockingSpaceState) {
        this.dockingSpaceState = dockingSpaceState;
    }



    @Override
    public String toString() {
        return "DockingSpace{" +
                "id='" + id + '\'' +
                ", dockingSpaceState=" + dockingSpaceState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DockingSpace that = (DockingSpace) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
