package com.fullstack.backend.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class Student {

    @NotBlank(message = "Student name is required")
    @JsonAlias({"studentName", "name", "student_name"})
    private String name;

    @NotBlank(message = "Roll number is required")
    @JsonAlias({"rollNo", "rollNumber", "roll_number", "rollno"})
    private String rollNumber;

    public Student() {
    }

    public Student(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    // Aliases to guarantee compatibility with any property naming
    @JsonProperty("studentName")
    public String getStudentName() {
        return name;
    }

    public void setStudentName(String studentName) {
        if (this.name == null || this.name.isEmpty()) {
            this.name = studentName;
        }
    }

    @JsonProperty("rollNo")
    public String getRollNo() {
        return rollNumber;
    }

    public void setRollNo(String rollNo) {
        if (this.rollNumber == null || this.rollNumber.isEmpty()) {
            this.rollNumber = rollNo;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(rollNumber, student.rollNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollNumber);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNumber='" + rollNumber + '\'' +
                '}';
    }
}
