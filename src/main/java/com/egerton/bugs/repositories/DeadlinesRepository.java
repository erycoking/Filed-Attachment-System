package com.egerton.bugs.repositories;

import com.egerton.bugs.Model.Deadlines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadlinesRepository extends JpaRepository<Deadlines, Integer> {
}
