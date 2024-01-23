package Objects;

import Exceptions.CoordsException;
import Exceptions.IlluminationException;
import Interfaces.Coordinates;
import Interfaces.Property;

import java.util.ArrayList;
//import Interfaces.Property;

public class Human extends Obj implements Coordinates, Property {

    @Override
    public String toString() {
        return name;
    }

    private final String name;
    int height;
    int fatigue; // усталость
    int coords;
    public Dungeons dungeon;
    public boolean canSee;
    public boolean haveKey;

    boolean isSitDown;
    ArrayList<Obj> inventory;

    public Human(String name, int height, int fatigue, int coords, Dungeons dungeon) {
        super(name, coords, dungeon);
        this.dungeon = dungeon;
        this.name = name;
        this.height = height;
        this.fatigue = fatigue;
        this.coords = coords;
        this.canSee = false;
        this.haveKey = false;
        inventory = new ArrayList<>();
        this.isSitDown = false;
    }

    // Сложные действия //

    public void start() {

        System.out.println("Уровень освещенности: " + this.dungeon.getIllumination());
        System.out.println("Координаты героя: " + getCoordinates());
        try {
            checkIllumination(this.dungeon);
        } catch (IlluminationException e) {
            System.out.println("Exception! " + e.getMessage());
        }

        this.setProperties("Красивая ", "before");
    }

    public void checkMainDungeon() {
        for (int i = 0; i < this.dungeon.volume - 2; i++) {
            this.walk(1);
            this.whatIsHere();
        }
        this.setCoordinates(0);
    }

    public void checkDungeon() {
        for (int i = 0; i < this.dungeon.volume - 1; i++) {
            this.walk(1);
            this.whatIsHere();
        }
        this.setCoordinates(0);
    }

    public void enterTheDungeon() {
        this.whatIsHereWithEnter();
        checkDungeon();
        this.whatIsHereWithEnter();
    }


    public void finaly() {
        this.setCoordinates(9);
        this.whatIsHereWithEnter();
        for (int i = 0; i < this.dungeon.volume - 1; i++) {
            this.walk(1);
            this.whatIsHereWithEnter();
        }
        this.sitDown();
        this.whatIsHereWithEnter();
        this.standUp();
    }


    // Действия //

    public void walk(int x) {
        String dir;
        if (this.coords + x < 0 || this.coords + x > this.dungeon.volume) {
            System.out.println("Тупик!");
            return;
        }
        if (x > 0) dir = " вперед";
        else if (x < 0) dir = " назад";
        else {
            System.out.println(name + " стоит на месте!");
            return;
        }
        System.out.println(super.name + " идет на " + Math.abs(x) + " метров" + dir);
        this.coords += x;
        this.tired(1);
    }

    public void enter(Doors door) {
        if (!door.isOpen) {
            System.out.println("Дверь закрыта!!");
            return;
        }
        if (door.dungBehind == null) {
            System.out.println("К сожалению, дверь никуда не ведет!");
            return;
        }
        if (door.height < this.height) {
            System.out.println("Дверь слишком маленькая, нужно присесть!");
            return;
        }
        if (this.dungeon == door.dungBehind) {
            this.dungeon = door.dungeon;
            System.out.println(name + " входит в " + this.dungeon + "...");
            setCoordinates(door.getCoordinates());


        } else {
            this.dungeon = door.dungBehind;
            System.out.println(name + " входит в " + this.dungeon + "...");
            setCoordinates(0);
        }
        // АБСОЛЮТНО ГЕНИАЛЬНАЯ система прохода в дверь
        // если мы в 1 подземелье - проходим во 2, если во 2 - проходим в 1
    }
    // нет проверки, есть ли перед нами дверь, надо бы пофиксить

    public void sitDown() {
        if (!isSitDown) {
            this.height /= 2;
            System.out.println(super.name + " присела на корточки");
            this.isSitDown = true;
            this.tired(1);
        }
    }

    public void standUp() {
        if (isSitDown) {
            if (this.height * 2 <= this.dungeon.height) {
                this.height *= 2;
                System.out.println(super.name + " встала на ноги");
                this.isSitDown = false;
                this.tired(1);
            }
            else System.out.println("Невозможно встать! Слишком низко!");
        }
    }

    public void tryToOpenWithKey(Doors door, Keys Key) { // попытка открыть дверь, принимает имя ключа, который используется
        if (!door.isOpen) {
//            if (getCoordinates() != door.getCoordinates() || this.dungeon != door.dungeon) {
//                System.out.println("Здесь ничего нет!");
//                return;
//            }
            if (!this.haveKey) {
                System.out.println("У " + super.name + " нет ключа!");
                return;
            }
            System.out.println(super.name + " пытается открыть " + door + " ключом " + Key.name);
            this.tired(1);
            if (door.key.equals(Key.key)) {
                System.out.println("Дверь открылась!");
                door.isOpen = true;
            } else {
                System.out.println("Ключ не подходит!");
            }
        }
    }

    public void tryToOpenWithAnyKey(Doors door) { // попытка открыть дверь, перебирает ВСЕ ключи в инвентаре
        if (!door.isOpen) {
//            if (getCoordinates() != door.getCoordinates() || this.dungeon != door.dungeon) {
//                System.out.println("Здесь ничего нет!");
//                return;
//            }
            if (!this.haveKey) {
                System.out.println("У " + super.name + " нет ключа!");
                return;
            }
            for (int i = 0; i <= this.inventory.size() - 1; i++) {
                Obj obj = this.inventory.get(i);
                if (obj instanceof Keys) {
                    System.out.println(super.name + " пытается открыть " + door + " ключом " + obj);
                    if (door.key.equals(((Keys) obj).key)) {
                        System.out.println("Дверь открылась!");
                        door.isOpen = true;
                        break;
                    }
                }
            }
            if (!door.isOpen) System.out.println("Никакой ключ в вашем инвентаре не подходит!");
        }
    }

    public void tryToOpen(Doors door) { // попытка открыть дверь без ключа
//        if (getCoordinates() != door.getCoordinates() || this.dungeon != door.dungeon) {
//            System.out.println("Здесь ничего нет!");
//            return;
//        }
        System.out.println(super.name + " пытается открыть " + door);

        if (door.isOpen) System.out.println("Дверь открыта!");
        else System.out.println("Дверь заперта!");
    }

    public void whatIsHere() { // хотим узнать, какой объект лежит по нашим координатам, делаем что-то
        Obj obj = this.dungeon.getObj(this.getCoordinates());
        if (obj instanceof Doors) {
            tryToOpen((Doors) obj);
            tryToOpenWithAnyKey((Doors) obj);
        } else if (obj != null) { // любой объект кроме двери можно положить в инвентарь - что мы и делаем
            System.out.println("Ух ты! это " + obj + "!");
            addToInventory(obj);
            this.haveKey = true;
            dungeon.removeObj(this.getCoordinates());
        } else System.out.println("Здесь ничего нет!");
    }

    public void whatIsHereWithEnter() { // хотим узнать, какой объект лежит по нашим координатам, делаем что-то
        Obj obj = this.dungeon.getObj(this.getCoordinates());
        if (obj instanceof Doors) {
            tryToOpen((Doors) obj);
            tryToOpenWithAnyKey((Doors) obj);
            if (((Doors) obj).isOpen) {
                enter((Doors) obj);
            }
        } else if (obj != null) { // любой объект кроме двери можно положить в инвентарь - что мы и делаем
            System.out.println("Ух ты! это " + obj + "!");
            addToInventory(obj);
            this.haveKey = true;
            dungeon.removeObj(this.getCoordinates());
        }
    }

    public void tired(int x) {
        this.fatigue += x;
        if (this.fatigue >= 30) {
            System.out.println(super.name + " устала, нужно отдохнуть!");
            this.fatigue = 0;
            try {
                Thread.sleep(10000); // останавливает текущий поток на X миллисекунд (10 секунд)
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void checkIllumination(Dungeons dungeon) throws IlluminationException {
        if (dungeon.illumination >= 10) {
            this.canSee = true;
            System.out.println(super.name + " хорошо всё видит!");
        } else {
            this.canSee = false;
            throw new IlluminationException(super.name + " ничего не видит!");
        }
    }


    // Сеттеры и геттеры //

    public void lookInventory() {
        System.out.println(inventory);
    }

    public Obj getFromInventory(int num) {
        return inventory.get(num);
    }

    private void addToInventory(Obj obj) {
        inventory.add(obj);
        if (obj instanceof Keys) {
            haveKey = true;
        }
        System.out.println(obj + " добавлен в инвентарь по индексу " + (inventory.size() - 1));
    }

    @Override
    public void setCoordinates(int newCords) throws CoordsException {
        if (newCords >= 0 && newCords < this.dungeon.volume) {
            this.coords = newCords;
            System.out.println(super.name + " теперь в координатах " + this.coords);
        } else {
            throw new CoordsException("Неверно заданы координаты!");
        }
    }

    @Override
    public int getCoordinates() {
        return this.coords;
    }

    @Override
    public void setProperties(String property, String BorA) {
        super.name = this.name;
        if (BorA.equals("after")) {
            super.name = super.name + property;
        } else if (BorA.equals("before")) {
            super.name = property + super.name;
        }
    }















    /*
    public void addToInv(Obj obj){
        if (inventory[coords] != null) {
            staticObj[coords] = obj;
        }
        else System.out.println("Здесть уже есть объект!");
    }
    // если несколько объектов на одних координатах - делать массив в массиве

    public Obj whatIsHere(int coords){
        return this.staticObj[coords];
    }
    */


}
