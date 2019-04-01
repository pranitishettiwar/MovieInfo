package com.codepath.flickster.util;

import java.util.HashMap;

import org.json.JSONObject;

public class Cache {

    private static Cache instance;
    HashMap<Integer, Node> map = null;
    private int capacity = 5;
    private Node head = null;
    private Node end = null;

    private Cache() {
        this.map = new HashMap<>(capacity);
    }

    // Only one instance for whole application
    public static Cache getInstance() {

        if (instance == null) {

            instance = new Cache();
        }

        return instance;

    }

    public JSONObject get(int key) {
        if (!map.containsKey(key)) {
            return null;
        }

        Node t = map.get(key);

        remove(t);
        setHead(t);

        return t.value;
    }

    public void put(int key, JSONObject value) {
        if (map.containsKey(key)) {
            Node t = map.get(key);

            remove(t);
            setHead(t);
        } else {
            if (map.size() >= capacity) {
                map.remove(end.key);
                remove(end);
            }
        }

        Node t = new Node(key, value);
        setHead(t);
        map.put(key, t);
    }

    private void remove(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            end = node.prev;
        }
    }

    private void setHead(Node node) {
        if (head != null) {
            head.prev = node;
        }

        node.next = head;
        node.prev = null;
        head = node;

        if (end == null) {
            end = head;
        }
    }

}