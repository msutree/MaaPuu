package com.maapuu.mereca.background.employee.bean;


import java.util.Comparator;

public class PinyinClientComparator implements Comparator<ClientBean> {

    public int compare(ClientBean o1, ClientBean o2) {
        if (o1.getFirstLetter().equals("@") || o2.getFirstLetter().equals("#")) {
            return -1;
        } else if (o1.getFirstLetter().equals("#") || o2.getFirstLetter().equals("@")) {
            return 1;
        } else {
            return o1.getFirstLetter().compareTo(o2.getFirstLetter());
        }
    }

}
