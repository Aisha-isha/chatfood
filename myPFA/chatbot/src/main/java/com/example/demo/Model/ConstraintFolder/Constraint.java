package com.example.demo.Model.ConstraintFolder;

import com.example.demo.Model.Meal;

public class Constraint {
    public String variable1;
public   String variable2;
 public  String variable3;
   ConstraintFunction test;

    public Constraint(String variable1, String variable2,String variable3, ConstraintFunction test) {
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.variable3 = variable3;
        this.test = test;
    }

    public boolean test(Meal value1,Meal  value2,Meal  value3) {
        return test.test(value1, value2,value3);
    }
}
