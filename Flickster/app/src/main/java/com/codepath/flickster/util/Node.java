package com.codepath.flickster.util;

import org.json.JSONObject;

class Node {
    int key;
    JSONObject value;
    Node prev;
    Node next;

    public Node(int key, JSONObject value) {
        this.key = key;
        this.value = value;
    }
}