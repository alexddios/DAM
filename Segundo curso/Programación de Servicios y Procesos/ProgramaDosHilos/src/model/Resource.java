package model;

public class Resource {
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void add() {
        count++;
    }

    public void remove() {
        count--;
    }
}
