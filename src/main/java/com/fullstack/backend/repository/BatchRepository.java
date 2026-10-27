package com.fullstack.backend.repository;

import com.fullstack.backend.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {

    /**
     * Find all batches where any enrolled student has the given roll number (case-insensitive).
     */
    @Query("{ 'students.rollNumber': { $regex: '^?0$', $options: 'i' } }")
    List<Batch> findByStudentRollNumberIgnoreCase(String rollNumber);

    /**
     * Standard query matching roll number directly.
     */
    List<Batch> findByStudentsRollNumber(String rollNumber);
}
