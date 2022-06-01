


public class Horse {
    private String name;
    private double speed;
    private double distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Horse(String name, double speed, double distance) {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }

    void move() {  //рандомайзер хода лошади
        distance += speed * Math.random();
    }

    void print() {   // тут рисуется траектория и шаги лошадей
        StringBuilder track = new StringBuilder();
        for (int i = 0; i < (int) distance; i++) {
            track.append(".");
        }
        System.out.println(track + name);
    }
}
