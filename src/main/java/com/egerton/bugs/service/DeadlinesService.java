package com.egerton.bugs.service;

import com.egerton.bugs.Model.Deadlines;
import com.egerton.bugs.repositories.DeadlinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeadlinesService {

    @Autowired
    private DeadlinesRepository deadlinesRepository;

    public Deadlines getTimeliness(int id){
        return deadlinesRepository.findOne(id);
    }

    public void save(Deadlines deadlines){
        deadlinesRepository.save(deadlines);
    }

    public List<Deadlines> getAllTimeliness(){
        return  deadlinesRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

}
