package com.example.demo.service;

import com.example.demo.dao.AppointmentDAO;
import com.example.demo.pojo.*;
import com.example.demo.viewmodel.AppointmentInfo;
import com.example.demo.viewmodel.AvailableSlot;
import com.example.demo.viewmodel.DentistSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired AppointmentDAO appointmentDAO;
    @Autowired DentalService dentalService;
    @Autowired DentistService dentistService;
    @Autowired AccountService accountService;
    @Autowired TreatmentService treatmentService;

    public void add(Appointment appointment){
        appointmentDAO.save(appointment);
    }

    public Appointment getByAppointmentID(Integer appointmentid) {
        return appointmentDAO.findByAppointmentIDAndIsDeletedFalse(appointmentid);
    }

    public List<Appointment> getByPatientID(Integer patientid) {
        return appointmentDAO.findByPatientIDAndIsDeletedFalse(patientid,Sort.by(Sort.Direction.DESC, "appointmentDate"));
    }

    public  List<AppointmentInfo> getByDentalID(Integer dentalid) {
        List<AppointmentInfo> AppointInfoList = new ArrayList<>();
        List<Appointment> appointmentList = appointmentDAO.findByDentalIDAndIsDeletedFalse(dentalid,Sort.by(Sort.Direction.DESC, "appointmentDate"));
        for(Appointment aitem: appointmentList){

            AppointmentInfo Info = new AppointmentInfo();
            Info.setAppointment(aitem);

            SimpleDateFormat localDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            Info.setAppointmentDateFormat(localDateFormat.format(aitem.getAppointmentDate()));

            SimpleDateFormat localTimeFormat = new SimpleDateFormat("HH:mm");
            Info.setStartTimeFormat(localTimeFormat.format(aitem.getAppointmentStartTime()));
            Info.setEndTimeFormat(localTimeFormat.format(aitem.getAppointmentEndTime()));

            User user = accountService.getByUserID(aitem.getPatientID());
            Info.setCustomer(user);

            Dentist dentist = dentistService.getDentistInfobyDentistID(aitem.getDentistID());
            dentist.setScheduleList(null);
            Info.setDentist(dentist);

            Treatment treatment = treatmentService.getByTreatmentID(aitem.getTreatmentID());
            Info.setTreatment(treatment);

            AppointInfoList.add(Info);
        }

        return AppointInfoList;
    }
    public List<Appointment> getByDentalIDAndDentistIDAndDate(Integer dentalid, Integer dentistid, Date date) {
        return appointmentDAO.findByDentalIDAndAppointmentDateAndIsDeletedFalse(dentalid,date,Sort.by(Sort.Direction.DESC, "appointmentStartTime"));
    }
    public List<Appointment> getByDentalIDAndDentistIDAndPeriod(Integer dentalid,Integer dentistid,Date AppointDate, Date StartTime, Date EndTime){
        return appointmentDAO.findByDentalIDAndDentistIDAndPeriodAndIsDeletedFalse(dentalid,dentistid,AppointDate,StartTime,EndTime);

    }

    public Boolean cancelAppointment(Integer appointmnetid){
        Date updatedDate = new Date();

        Appointment appointmentModel = getByAppointmentID(appointmnetid);

        if(appointmentModel == null){
            return false;
        }

        if(appointmentModel.getStatus() == 2 || appointmentModel.getStatus() == 3){
            return false;
        }

        appointmentModel.setStatus(2);
        appointmentModel.setUpdatedBy(appointmentModel.getCreatedBy());
        appointmentModel.setUpdatedDate(updatedDate);
        appointmentDAO.save(appointmentModel);

        return true;
    }


    public Boolean edit(Appointment appointment){
        Date updatedDate = new Date();

        Appointment appointmentModel = getByAppointmentID(appointment.getAppointmentID());

        if(appointmentModel == null){
            return false;
        }

        appointmentModel.setAppointmentStartTime(appointment.getAppointmentStartTime());
        appointmentModel.setAppointmentEndTime(appointment.getAppointmentEndTime());
        appointmentModel.setUpdatedDate(updatedDate);
        appointmentModel.setUpdatedBy(appointment.getCreatedBy());
        appointmentDAO.save(appointmentModel);
        return true;
    }



    public List<DentistSlot> getAvailableSlotByDentalIDAndTreamentID(Integer dentalid, Integer treatmentid, Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer dateofweek = cal.get(cal.DAY_OF_WEEK);

      //  Integer dateofweek = date.getDay();
        Dental dentalModel = dentalService.getByDentalID(dentalid);
        Set<Dentist> dentistList = dentalModel.getDentistList();
        Set<DentalTreatment> dentalTreatment = dentalModel.getDentalTreatmentList();

        float TreatmentDuration = 0.5f;
        for(DentalTreatment element: dentalTreatment){
            if(element.getTreatmentID() == treatmentid){
                TreatmentDuration = element.getPerSeccionDuration();
                break;
            }
        }

        List<DentistSlot> dentistSlots = new ArrayList<DentistSlot>();
        for(Dentist dentist: dentistList){

            DentistSlot dentistSlot = new DentistSlot();
            dentistSlot.setDentist(dentist);

            Set<Schedule> scheduleList = dentist.getScheduleList();
            Schedule scheduleModel = new Schedule();
            for(Schedule schedule: scheduleList){
                if(schedule.getDayOfWeek() == dateofweek){
                    scheduleModel = schedule;
                    break;
                }
            }
            long timediff = scheduleModel.getEndTime().getTime() - scheduleModel.getStartTime().getTime();
            long difference_In_Hours
                    = (timediff
                    / (1000 * 60 * 60))
                    % 24;

            float countSection = difference_In_Hours / TreatmentDuration;

            Date StartTime = scheduleModel.getStartTime();
            List<AvailableSlot> slots = new ArrayList<AvailableSlot>();
            for(int i=0; i< (int)countSection; i++){

                long t= StartTime.getTime();
                Date afterAdding= new Date((long) (t + (TreatmentDuration * 60 * 60000)));

                AvailableSlot slot = new AvailableSlot();
                slot.setStartTime(scheduleModel.getStartTime());
                slot.setEndTime(afterAdding);
                slot.setAvailable(true);
                slots.add(slot);
                StartTime = afterAdding;
            }

            dentistSlot.setAvailableSlots(slots);
            dentistSlots.add(dentistSlot);
        }

        for(DentistSlot dentistSlot: dentistSlots){

            for(AvailableSlot ava: dentistSlot.getAvailableSlots()){
                List<Appointment> list = getByDentalIDAndDentistIDAndPeriod(dentalid,dentistSlot.getDentist().getDentistID(),date, ava.getStartTime(),ava.getEndTime());
                if(list.size()>0){
                    ava.setAvailable(false);
                }
            }

        }

        return dentistSlots;

    }

}
