package com.fullstack.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "batches")
public class Batch {

    @Id
    private String id;

    @NotBlank(message = "Batch name is required")
    private String batchName;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotBlank(message = "Start date is required")
    private String startDate;

    private List<Student> students = new ArrayList<>();

    public Batch() {
        this.students = new ArrayList<>();
    }

    public Batch(String batchName, String courseName, String startDate) {
        this.batchName = batchName;
        this.courseName = courseName;
        this.startDate = startDate;
        this.students = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("_id")
    public String get_Id() {
        return id;
    }

    @JsonProperty("_id")
    public void set_Id(String _id) {
        if (this.id == null || this.id.isEmpty()) {
            this.id = _id;
        }
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students != null ? students : new ArrayList<>();
    }

    @JsonProperty("studentCount")
    public int getStudentCount() {
        return students != null ? students.size() : 0;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id='" + id + '\'' +
                ", batchName='" + batchName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", studentsCount=" + (students != null ? students.size() : 0) +
                '}';
    }
}
