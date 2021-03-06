package com.example.demo.service;


import com.example.demo.dao.TreatmentDAO;
import com.example.demo.pojo.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

    @Autowired TreatmentDAO treatmentDAO;

    /**
     * Method to get all treatments
     * @return treatment list
     */
    public List<Treatment> list() {
        return treatmentDAO.findAllByIsDeletedFalse(Sort.by(Sort.Direction.ASC, "treatmentName"));
    }

    /**
     * Method to get treatment by treatmentid
     * @param treatmentid
     * @return treatment
     */
    public Treatment getByTreatmentID(Integer treatmentid) {
        return treatmentDAO.findByTreatmentIDAndIsDeletedFalse(treatmentid);
    }


}
