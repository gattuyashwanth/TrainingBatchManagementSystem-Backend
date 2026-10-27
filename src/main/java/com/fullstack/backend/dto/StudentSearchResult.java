package com.fullstack.backend.dto;

public class StudentSearchResult {

    private String name;
    private String studentName;
    private String rollNumber;
    private String batchId;
    private String batchName;
    private String courseName;
    private String startDate;

    public StudentSearchResult() {
    }

    public StudentSearchResult(String studentName, String rollNumber, String batchId, String batchName, String courseName, String startDate) {
        this.name = studentName;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.batchId = batchId;
        this.batchName = batchName;
        this.courseName = courseName;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.studentName = name;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
        this.name = studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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
}
