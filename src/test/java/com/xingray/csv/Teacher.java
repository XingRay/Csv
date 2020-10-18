package com.xingray.csv;

import com.xingray.csv.annotations.DataType;
import com.xingray.csv.annotations.Default;
import com.xingray.csv.annotations.Format;
import com.xingray.csv.annotations.Index;

import java.util.Date;

public class Teacher {

    @Default("unknown name")
    @Format("teacher-%s")
    @Index(1)
    private String name;

    @Default("unknown age")
    @Index(2)
    private Integer age;


    @Default("unknown birthday")
    @DataType(DataType.TIME_MILLS)
    @Format("yyy-MM-dd")
    @Index(4)
    private Long birthday;

    @Default("unknown record date")
    @Format("yyyy/MM/dd")
    @Index(3)
    private Date recordDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", recordDate=" + recordDate +
                '}';
    }
}
