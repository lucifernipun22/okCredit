package com.example.okcredit.Views.values;

import com.example.okcredit.Data.local.CustomerEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class zc {
    public static void main(String[] args) {
        List<CustomerEntity> list = new ArrayList<>();
        Collections.sort(list, new Comparator<CustomerEntity>() {
            @Override
            public int compare(CustomerEntity o1, CustomerEntity o2) {
                return 0;
            }
        });

    }
}
