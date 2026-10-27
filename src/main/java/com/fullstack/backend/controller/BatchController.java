package com.fullstack.backend.controller;

import com.fullstack.backend.dto.StudentSearchResult;
import com.fullstack.backend.model.Batch;
import com.fullstack.backend.model.Student;
import com.fullstack.backend.service.BatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    /**
     * 1. GET /api/batches
     * Return all batches, including their enrolled students and student counts.
     */
    @GetMapping
    public ResponseEntity<List<Batch>> getAllBatches() {
        List<Batch> batches = batchService.getAllBatches();
        return ResponseEntity.ok(batches);
    }

    /**
     * 2. POST /api/batches
     * Create a new batch.
     */
    @PostMapping
    public ResponseEntity<Batch> createBatch(@Valid @RequestBody Batch batch) {
        Batch createdBatch = batchService.createBatch(batch);
        return new ResponseEntity<>(createdBatch, HttpStatus.CREATED);
    }

    /**
     * 5. GET /api/batches/search?rollNumber={rollNumber}
     * Search for a student by roll number across all batches.
     * Placed before /{batchId} to ensure URL mapping precedence.
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentSearchResult>> searchStudentByRollNumber(
            @RequestParam("rollNumber") String rollNumber) {
        List<StudentSearchResult> results = batchService.searchByRollNumber(rollNumber);
        return ResponseEntity.ok(results);
    }

    /**
     * 3. GET /api/batches/{batchId}
     * Return the details of one batch and its enrolled students.
     */
    @GetMapping("/{batchId}")
    public ResponseEntity<Batch> getBatchById(@PathVariable("batchId") String batchId) {
        Batch batch = batchService.getBatchById(batchId);
        return ResponseEntity.ok(batch);
    }

    /**
     * 4. POST /api/batches/{batchId}/students
     * Add a student to the selected batch.
     * Validate duplicate roll numbers within that batch.
     */
    @PostMapping("/{batchId}/students")
    public ResponseEntity<Batch> addStudentToBatch(
            @PathVariable("batchId") String batchId,
            @Valid @RequestBody Student student) {
        Batch updatedBatch = batchService.addStudentToBatch(batchId, student);
        return new ResponseEntity<>(updatedBatch, HttpStatus.CREATED);
    }

    /**
     * 6. GET /api/batches/{batchId}/students/count
     * Return the number of students in the selected batch.
     */
    @GetMapping("/{batchId}/students/count")
    public ResponseEntity<Map<String, Object>> getStudentCount(@PathVariable("batchId") String batchId) {
        int count = batchService.getStudentCount(batchId);
        Map<String, Object> response = new HashMap<>();
        response.put("batchId", batchId);
        response.put("count", count);
        response.put("studentCount", count);
        return ResponseEntity.ok(response);
    }
}
