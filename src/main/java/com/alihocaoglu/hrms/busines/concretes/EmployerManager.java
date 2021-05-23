package com.alihocaoglu.hrms.busines.concretes;

import com.alihocaoglu.hrms.busines.abstracts.EmployerService;
import com.alihocaoglu.hrms.core.utilities.results.*;
import com.alihocaoglu.hrms.dataAccess.abstracts.EmployerDao;
import com.alihocaoglu.hrms.entities.concretes.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerManager implements EmployerService {

    private EmployerDao employerDao;

    @Autowired
    public EmployerManager(EmployerDao employerDao) {
        this.employerDao = employerDao;
    }

    @Override
    public DataResult<List<Employer>> getAll() {
        return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),"Data listelendi");
    }

    @Override
    public DataResult<Employer> getByEmail(String email) {
        return new SuccessDataResult<Employer>(this.employerDao.findByEmail(email),"Listelendi");
    }

    @Override
    public Result add(Employer employer) {
        if(getByEmail(employer.getEmail()).getData() != null){
            return new ErrorResult("Bu email zaten kayıtlı");
        }
        this.employerDao.save(employer);
        return new SuccessResult("Kullanıcı eklendi");
    }
}
