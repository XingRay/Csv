package com.xingray.csv;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvTest {

    public static final long DAY_IN_MILLS = 24 * 3600 * 1000;

    @Test
    public void studentWriteTest() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            if (i % 5 != 0) {
                student.setAge(i);
                student.setName("student" + i);
                student.setBirthday(System.currentTimeMillis() - 100L * DAY_IN_MILLS);
                student.setRecordDate(new Date(System.currentTimeMillis()));
            }

            students.add(student);
        }
        List<ExportConfigItem> items = new ArrayList<>();

        ExportConfigItem item;

        item = new ExportConfigItem();
        item.setIndex(0);
        item.setEnable(true);
        item.setFieldName("name");
        item.setExportName("studentName");
        item.setDefaultValue("unknown name");
        item.setFormat("%s");
        items.add(item);

        item = new ExportConfigItem();
        item.setIndex(1);
        item.setEnable(true);
        item.setFieldName("age");
        item.setExportName("studentAge");
        item.setDefaultValue("unknown age");
        item.setFormat("%d");
        items.add(item);

        item = new ExportConfigItem();
        item.setIndex(2);
        item.setEnable(true);
        item.setFieldName("recordDate");
        item.setExportName("studentRecordDate");
        item.setDefaultValue("unknown record date");
        item.setFormat("yyyy/MM/dd");
        items.add(item);

        item = new ExportConfigItem();
        item.setIndex(3);
        item.setEnable(true);
        item.setFieldName("birthday");
        item.setExportName("studentBirthday");
        item.setDefaultValue("unknown birthday date");
        item.setDateType(ExportConfigItem.DATA_TYPE_TIME_MILLS);
        item.setFormat("yyyy-MM-dd");
        items.add(item);


        String path = CsvTest.class.getClassLoader().getResource("").getPath() + "students.csv";
        System.out.println("path: " + path);
        CsvUtil.write(students, Student.class, path, items);
    }

    @Test
    public void teacherWriteTest() {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Teacher teacher = new Teacher();
            if (i % 5 != 0) {
                teacher.setAge(i);
                teacher.setName("teacher" + i);
                teacher.setBirthday(System.currentTimeMillis() - 100L * DAY_IN_MILLS);
                teacher.setRecordDate(new Date(System.currentTimeMillis()));
            }

            teachers.add(teacher);
        }

        String path = CsvTest.class.getClassLoader().getResource("").getPath() + "teachers.csv";
        System.out.println("path: " + path);
        CsvUtil.write(teachers, Teacher.class, path);
    }
}
