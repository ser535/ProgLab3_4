package Objects;

import Enums.Colors;
import Interfaces.Coordinates;

import java.text.DecimalFormat;

public class Doors extends Obj implements Coordinates {

    @Override
    public String toString() {
        return this.color + " " + name;
    }

    @Override
    public void setCoordinates(int newCords) {
        try {
            this.coords = newCords;
            this.dungeon.removeObj(this.coords);
            this.dungeon.addObj(this, newCords);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Неверно заданы координаты!!");
        }

    }

    @Override
    public int getCoordinates() {
        return this.coords;
    }


    public final String name;
    public boolean isOpen;
    public String key;
    public int coords;
    public int height;
    Dungeons dungeon;

    public Dungeons dungBehind;
    public final String color;

    public Doors(String name, int coords, Dungeons dungeon, int height ,Colors color) { // случайный ключ
        super(name, coords, dungeon);
        this.dungeon = dungeon;
        this.height = height;
        this.coords = coords;
        this.name = name;
        this.isOpen = false;
        this.dungBehind = null;
        this.color = color.getDescription();
        this.key = new DecimalFormat("000").format(Math.random() * 1000);
        // создает рандомный ключ для двери в формате 3-х значного числа

    }

    public Doors(String name, int coords, String key, Dungeons dungeon, int height, Colors color) { // задаем ключ сами
        super(name, coords, dungeon);
        this.dungeon = dungeon;
        this.height = height;
        this.coords = coords;
        this.name = name;
        this.isOpen = false;
        this.dungBehind = null;
        this.color = color.getDescription();
        this.key = key;
    }


    public void setDungBehind(Dungeons dungeon) { // создаем подземелье за дверью
        this.dungBehind = dungeon;
        dungeon.addObj(this, 0);
    }

    public Dungeons setDungBehindForObj() {
        // этот метод предназначен для создания "подземелья" для каких-то объектов, находящихся за дверью
        // возвращает созданное подземелье - его мы указываем при создании тех самых объектов
        // в итоге за дверью создается подземелье с 2 ячейчками - в одной дверь, в другой - объект
        // говоря честно - да, костыль. Но на этом этапе я уже загнан в рамки свойств объектов :(
        Dungeons dungeon = new Dungeons("пространство за " + this.name, 1, this.dungeon.illumination, 2, this.dungeon.height);
        this.dungBehind = dungeon;
        dungeon.addObj(this, 0);
        return dungeon;
    }
}



