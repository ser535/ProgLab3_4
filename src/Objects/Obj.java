package Objects;

public abstract class Obj {
    String name;
    int coords;
    Dungeons dungeon;

    public Obj(String name, int coords, Dungeons dungeon) {
        this.name = name;
        this.coords = coords;
        this.dungeon = dungeon;
        dungeon.addObj(this, coords);
    }

}

