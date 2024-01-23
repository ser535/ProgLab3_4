package Enums;


public enum Colors {
    RED("красную"),
    BLUE("синюю"),
    GREEN("зеленую"),
    GOLD("золотую");



    final String description;

    Colors(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

}

