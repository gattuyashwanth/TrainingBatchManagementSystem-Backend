package com.fullstack.backend.service;

import com.fullstack.backend.dto.StudentSearchResult;
import com.fullstack.backend.exception.DuplicateRollNumberException;
import com.fullstack.backend.exception.ResourceNotFoundException;
import com.fullstack.backend.model.Batch;
import com.fullstack.backend.model.Student;
import com.fullstack.backend.repository.BatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {

    private static final Logger log = LoggerFactory.getLogger(BatchService.class);

    private final BatchRepository batchRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * Retrieve all batches with enrolled students and counts.
     */
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    /**
     * Create a new training batch.
     */
    public Batch createBatch(Batch batch) {
        if (batch.getBatchName() == null || batch.getBatchName().trim().isEmpty()) {
            throw new IllegalArgumentException("Batch name is required");
        }
        if (batch.getCourseName() == null || batch.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name is required");
        }
        if (batch.getStartDate() == null || batch.getStartDate().trim().isEmpty()) {
            throw new IllegalArgumentException("Start date is required");
        }

        batch.setBatchName(batch.getBatchName().trim());
        batch.setCourseName(batch.getCourseName().trim());
        batch.setStartDate(batch.getStartDate().trim());
        if (batch.getStudents() == null) {
            batch.setStudents(new ArrayList<>());
        }

        Batch saved = batchRepository.save(batch);
        log.info("Created new batch: id={}, name={}", saved.getId(), saved.getBatchName());
        return saved;
    }

    /**
     * Retrieve a specific batch by ID.
     */
    public Batch getBatchById(String batchId) {
        return batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with id: " + batchId));
    }

    /**
     * Add a student to an existing batch, validating against duplicate roll numbers within the same batch.
     */
    public Batch addStudentToBatch(String batchId, Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student details are required");
        }
        String name = student.getName() != null ? student.getName() : student.getStudentName();
        String rollNumber = student.getRollNumber() != null ? student.getRollNumber() : student.getRollNo();

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number cannot be empty");
        }

        Batch batch = getBatchById(batchId);

        String trimmedRollNumber = rollNumber.trim();
        String trimmedName = name.trim();

        // Check for duplicate roll numbers within the same batch
        boolean existsInBatch = batch.getStudents().stream()
                .anyMatch(s -> {
                    String existingRoll = s.getRollNumber() != null ? s.getRollNumber() : s.getRollNo();
                    return existingRoll != null && existingRoll.trim().equalsIgnoreCase(trimmedRollNumber);
                });

        if (existsInBatch) {
            log.warn("Duplicate roll number '{}' detected in batch '{}'", trimmedRollNumber, batch.getBatchName());
            throw new DuplicateRollNumberException("This roll number already exists in this batch.");
        }

        Student newStudent = new Student(trimmedName, trimmedRollNumber);
        batch.getStudents().add(newStudent);

        Batch updated = batchRepository.save(batch);
        log.info("Added student '{}' (Roll: {}) to batch id={}, total students={}",
                trimmedName, trimmedRollNumber, batchId, updated.getStudents().size());
        return updated;
    }

    /**
     * Search for students by roll number across all batches.
     */
    public List<StudentSearchResult> searchByRollNumber(String rollNumber) {
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number search query cannot be empty");
        }

        String query = rollNumber.trim();
        List<Batch> allBatches = batchRepository.findAll();
        List<StudentSearchResult> results = new ArrayList<>();

        for (Batch batch : allBatches) {
            if (batch.getStudents() != null) {
                for (Student student : batch.getStudents()) {
                    String sRoll = student.getRollNumber() != null ? student.getRollNumber() : student.getRollNo();
                    String sName = student.getName() != null ? student.getName() : student.getStudentName();
                    if (sRoll != null && sRoll.trim().equalsIgnoreCase(query)) {
                        results.add(new StudentSearchResult(
                                sName,
                                sRoll,
                                batch.getId(),
                                batch.getBatchName(),
                                batch.getCourseName(),
                                batch.getStartDate()
                        ));
                    }
                }
            }
        }

        if (results.isEmpty()) {
            log.info("No student found with roll number: {}", query);
            throw new ResourceNotFoundException("Student not found");
        }

        return results;
    }

    /**
     * Get student count for a specific batch.
     */
    public int getStudentCount(String batchId) {
        Batch batch = getBatchById(batchId);
        return batch.getStudents() != null ? batch.getStudents().size() : 0;
    }
}
