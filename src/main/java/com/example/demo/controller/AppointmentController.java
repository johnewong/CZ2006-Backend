package com.example.demo.controller;

import com.example.demo.pojo.Appointment;
import com.example.demo.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Appointment management")
@RestController
@RequestMapping(value = "appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @ApiOperation(value = "api to get all appointment by patientid")
    @GetMapping("/getbypatientid/{patientid}")
    public List<Appointment> getbypatientid(@PathVariable("patientid") Integer patientid) throws Exception {
        return appointmentService.getbypatientid(patientid);
    }

    @ApiOperation(value = "api to get a appointment by appointmentid", notes = "", response = Appointment.class)
    @GetMapping("/getbyid/{appointmentid}")
    public Appointment getbyid(@PathVariable("appointmentid") Integer appointmentid) throws Exception {
        Appointment appointment = appointmentService.getbyid(appointmentid);
        return appointment;
    }
}
