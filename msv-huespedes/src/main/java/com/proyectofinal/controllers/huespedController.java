package com.proyectofinal.controllers;

import com.proyectofinal.common.controller.CommonController;
import com.proyectofinal.common.dto.HuespedRequest;
import com.proyectofinal.services.HuespedService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/huesped")
public class huespedController extends CommonController<HuespedRequest, HuespedRequest, HuespedService> {
    public huespedController(HuespedService service) {
        super(service);
    }
}
