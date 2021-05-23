package com.alihocaoglu.hrms.busines.concretes;

import com.alihocaoglu.hrms.busines.abstracts.CandidateService;
import com.alihocaoglu.hrms.busines.abstracts.NationalValidationService;
import com.alihocaoglu.hrms.core.utilities.results.*;
import com.alihocaoglu.hrms.dataAccess.abstracts.CandidateDao;
import com.alihocaoglu.hrms.entities.concretes.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateManager implements CandidateService {

    private CandidateDao candidateDao;
    private NationalValidationService nationalValidationService;

    @Autowired
    public CandidateManager(CandidateDao candidateDao, NationalValidationService nationalValidationService) {
        this.candidateDao = candidateDao;
        this.nationalValidationService=nationalValidationService;
    }

    @Override
    public DataResult<List<Candidate>> getAll() {
        return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(),"Data listelendi");
    }

    @Override
    public DataResult<Candidate> getByNationalNumber(String nationalNumber) {
        return new SuccessDataResult<Candidate>(this.candidateDao.findByNationalNumber(nationalNumber),"Listelendi");
    }

    @Override
    public DataResult<Candidate> getByEmail(String email) {
        return new SuccessDataResult<Candidate>(this.candidateDao.findByEmail(email),"Listelendi");
    }

    @Override
    public Result add(Candidate candidate) {
        if(getByNationalNumber(candidate.getNationalNumber()).getData() != null){
            return new ErrorResult("Bu kimlik numarası zaten kayıtlı");
        }else if(getByEmail(candidate.getEmail()).getData() != null){
            return new ErrorResult("Bu email zaten kayıtlı");
        }

        if(nationalValidationService.validate(candidate)){
            this.candidateDao.save(candidate);
            return new SuccessResult("Kayıt yapıldı");
        }else{
            return new ErrorResult("Kullanıcı bilgileri hatalı");
        }

    }
}
