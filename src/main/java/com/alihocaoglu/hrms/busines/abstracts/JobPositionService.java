package com.alihocaoglu.hrms.busines.abstracts;

import com.alihocaoglu.hrms.entities.concretes.JobPosition;

import java.util.List;

public interface JobPositionService {
    List<JobPosition> getAll();
}
