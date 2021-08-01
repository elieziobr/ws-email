package com.md.email.controller;

import com.md.email.controller.mapper.EmailMapper;
import com.md.email.dto.EmailDto;
import com.md.email.entities.EmailModel;
import com.md.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<EmailModel> enviarEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = EmailMapper.INSTANCE.toEmailEntity(emailDto);
        //BeanUtils.copyProperties(emailDto, emailModel);

        emailService.enviarEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}
