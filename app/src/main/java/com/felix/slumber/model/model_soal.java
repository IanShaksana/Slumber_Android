package com.felix.slumber.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class model_soal {
    private Integer angka_1;
    private Integer angka_2;
    private Integer angka_3;
    private Integer angka_4;


    private String tanda_1;
    private String tanda_2;


    public model_soal(Integer angka_1, Integer angka_2, Integer angka_3, String tanda_1, String tanda_2, Integer angka_4) {
        this.angka_1 = angka_1;
        this.angka_2 = angka_2;
        this.angka_3 = angka_3;
        this.tanda_1 = tanda_1;
        this.tanda_2 = tanda_2;
        this.angka_4 = angka_4;
    }
}
