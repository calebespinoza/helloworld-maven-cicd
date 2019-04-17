package com.baccredomatic.helloworld;

public class Main {
    Credomatic obj = new Credomatic();
    String message = obj.getMsg();
    public static void main(String[] args) {
        System.out.println("Hello world\n");
    }

    public void printMessage(){
        System.out.println(obj.getMsg());
    }
}