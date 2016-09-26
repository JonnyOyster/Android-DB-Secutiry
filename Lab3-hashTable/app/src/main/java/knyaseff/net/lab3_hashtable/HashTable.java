package knyaseff.net.lab3_hashtable;

/**
 * Created by kvpshka on 19.09.2016.
 */
public class HashTable {
    private String keyString;
    private String value;
    private int positionOfRelative;
    private int key;


    private HashTable() {}

    HashTable(String keyString, String value) {
        this.keyString = keyString;
        this.value = value;
        this.key = getHashKey(keyString);
    }

    HashTable(String keyString, String value, int positionOfRelative) {
        this.keyString = keyString;
        this.value = value;
        this.positionOfRelative = positionOfRelative;
        this.key = getHashKey(keyString);
    }

    public int getHashKey(String keyString) {
        return keyString.hashCode() % 10;
    }

    public void setRelativePosition(int relativePosition) {
        this.positionOfRelative = relativePosition;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPositionOfRelative() {
        return positionOfRelative;
    }

    public void setPositionOfRelative(int positionOfRelative) {
        this.positionOfRelative = positionOfRelative;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
