package Objects;

import Exceptions.CoordsException;
import Interfaces.Coordinates;

import java.text.DecimalFormat;

public class Keys extends Obj implements Coordinates {

    // Перезапись

    @Override
    public String toString(){
        return name;
    }

    public String key;
    final String name;
    int coords;
    Dungeons dungeon;

    public Keys(String name, int coords, Dungeons dungeon) { // случайный ключ
        super(name, coords, dungeon);
        this.name = name;
        this.key = new DecimalFormat("000").format(Math.random() * 1000);
        this.coords = coords;
        this.dungeon = dungeon;
    }
    public Keys(String name, int coords, String key, Dungeons dungeon) { // задаем ключ сами
        super(name, coords, dungeon);
        this.name = name;
        this.key = key;
        this.coords = coords;
        this.dungeon = dungeon;
    }


                                        // Геттеры и сеттеры //

    public String getKey() {
        return this.key;
    }
    public void setKey(String newKey) {
        this.key = newKey;
    }
    @Override
    public void setCoordinates(int newCords) {
        try {
            this.coords = newCords;
            this.dungeon.removeObj(this.coords);
            this.dungeon.addObj(this, newCords);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неверно заданы координаты!!");
        }
    }
    @Override
    public int getCoordinates(){
        return this.coords;
    }


}
