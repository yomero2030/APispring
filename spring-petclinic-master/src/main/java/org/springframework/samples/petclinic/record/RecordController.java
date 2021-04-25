/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.record;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katZ_
 */
@Controller
public class RecordController {

    private final RecordRepository records;
    private ModelAndView modelAndView;

    public RecordController(RecordRepository clinicService) {
        this.records = clinicService;
    }

    private ModelAndView ViewListUser() {
        ModelAndView _modelAndView = new ModelAndView("user/records");
        ArrayList<Record> records = this.records.All();
        _modelAndView.addObject("records", records);
        return _modelAndView;
    }

    @GetMapping("/admin/records")
    public ModelAndView List() {
        modelAndView = this.ViewListUser();
        return modelAndView;
    }

}
