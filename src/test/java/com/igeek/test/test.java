package com.igeek.test;

import java.io.File;


class A{
    public int f(int a){
        return a + 1;
    }
}

public class test extends A {

    public int f(int a,char c){
        return a + 2;
    }

    public static void main(String[] args) {
        /*File file = new File("C:\\Users\\30624\\Pictures\\ico/littleyellowperson.jpg");
        System.out.println(file.length());*/

        System.out.println(new test().f(0));
    }

    public void modify(){
        int i,j,k;
        i = 100;
        while (i > 0){
            j = i * 2;
            System.out.println("The value of j is :" + j);
            //k = k + 1;//Variable 'k' might not have bean initialized.
            i--;
        }
    }
}
