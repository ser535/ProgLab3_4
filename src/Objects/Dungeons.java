package Objects;

import Exceptions.CoordsException;

public class Dungeons {

    @Override
    public String toString() {
        return name;
    }

    final String name;
    public int doorsAmount;
    int illumination;
    final int volume;
    public int height;

    public Obj[] staticObjs; // массив статических объектов в подземелье

    public Dungeons(String name, int doorsAmount, int illumination, int volume, int height) {
        this.name = name;
        this.doorsAmount = doorsAmount;
        this.illumination = illumination;
        this.volume = volume;
        this.height = height;

        staticObjs = new Obj[this.volume];
    }

    public int getIllumination() {
        return illumination;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public int getVolume() {
        return this.volume;
    }

    public void addObj(Obj obj, int coords) {
        try {
            if (staticObjs[coords] == null) {
                staticObjs[coords] = obj;
                obj.coords = coords;
            } else System.out.println("Здесь уже есть объект!");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неправильно заданы координаты!!--");
        }



    }

    // если несколько объектов на одних координатах - делать массив в массиве?
    public void removeObj(int coords) {
        staticObjs[coords] = null;
    }

    public Obj getObj(int coords) {
        return this.staticObjs[coords];
    }


}
