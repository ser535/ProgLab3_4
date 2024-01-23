import Enums.Colors;
import Interfaces.Coordinates;
import Objects.*;


public class Main {
    public static void main(String[] args) {

        //int doorsAmount = (int) (Math.random() * 20) + 1; // случайная генерация кол-ва дверей
        int doorsAmount = 5;
        Dungeons MainDungeon = new Dungeons("Большое подземелье", doorsAmount, 40, doorsAmount + 5, 250);
        final int mainDungeonVolume = MainDungeon.getVolume();

        Human alice = new Human("Алиса", 140, 2, Coordinates.startPos, MainDungeon);

        Doors[] door = new Doors[doorsAmount];
        Colors[] colors = {Colors.GREEN, Colors.RED, Colors.BLUE};
        for (int i = 0; i < MainDungeon.doorsAmount; i++){
            int randColor = (int) (Math.random() * 3); // выбираем случайный цвет двери
            door[i] = new Doors("Дверь №" + (i+1), i+1, MainDungeon, 150, colors[randColor]);
        }

        Dungeons dungeon1 = new Dungeons("1 подземелье",  0,  20,  3, 200);
        door[1].setDungBehind(dungeon1);

        Dungeons dungeon2 = new Dungeons("2 подземелье",  0,  20,  2, 200);
        door[3].setDungBehind(dungeon2);



        Keys goldKey = new Keys("Золотой ключик", 7, "999", MainDungeon);
        Keys key2 = new Keys("обычный ключ 2", 1, "222", door[2].setDungBehindForObj());
        // таким образом мы создали ключ, лежащий за дверью 3 (2+1) лежащий в координате 1
        // "подземелья", находящегося за этой дверью и созданного специально под этот ключ
        door[1].key = "222";
        door[2].isOpen = true;

        Keys key4 = new Keys("обычный ключ 4", 1, "444", door[4].setDungBehindForObj());
        door[3].key = "444";
        door[4].isOpen = true;


        Doors curtain = new Doors("Штора", mainDungeonVolume - 1, MainDungeon, 200, Colors.RED);
        Dungeons corridorBehindCurtain = new Dungeons("коридор", 2, 30, 2, 200);
        curtain.setDungBehind(corridorBehindCurtain);
        curtain.isOpen = true;

        Doors littleDoor = new Doors("Маленькая дверь", 1, "999" , corridorBehindCurtain, 90, Colors.GOLD);
        Dungeons littleDungeon = new Dungeons("крохотная нора", 0, 15, 1000, 90);
        littleDoor.setDungBehind(littleDungeon);



        ///////////////////////////////////////////////////////////////


        alice.start();
        alice.checkMainDungeon();
        alice.setCoordinates(3);
        alice.enterTheDungeon();
        alice.walk(-1);
        alice.enterTheDungeon();
        alice.finaly();



/*
        // перебор дверей с без ключа


        alice.setCoordinates(2);
        alice.tryToOpen(door[1], goldKey);
        // пробуем открыть дверь не имея ключа

        alice.setProperties("", "after");

        for (int i = 0; i < mainDungeonVolume; i++) {
            alice.walk(1);
            alice.tired(1);
            if (alice.getCoordinates() == goldKey.getCoordinates()) {
                System.out.println("Вау! Это же золотой ключик!!!");
                alice.haveKey = true;
                break;
            }
        }

//        try {
//            alice.setCoordinates(-1);
//        } catch (CoordsException e){
//            System.out.println("Exception! " + e.getMessage());
//        }
        // проверка работы исключений


        for (int i = 0; i < MainDungeon.doorsAmount; i++){
            alice.walk(1);
            alice.tired(1);

            if (alice.canSee) {
                alice.tryToOpen(door[i], goldKey);
                alice.tired(1);
            }
        }
        // перебор дверей с золотым ключом

        System.out.println("Ничего не открылось :(\nИдем дальше!");

        for (int i = 0; i < mainDungeonVolume; i++) {
            alice.walk(1);
            alice.tired(1);
            if (alice.getCoordinates() == curtain.getCoordinates()){
                System.out.println("Ух ты! " + curtain + "! Что же за ней...");
                break;
            }
        }

 */
    }
}

