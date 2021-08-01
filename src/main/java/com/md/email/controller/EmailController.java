package com.md.email.controller;

import com.md.email.controller.mapper.EmailMapper;
import com.md.email.dto.EmailDto;
import com.md.email.entities.EmailModel;
import com.md.email.services.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emails")
@Api(value = "API Rest de Envio de Emails")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    @ApiOperation(value = "Enviar Email")
    public ResponseEntity<EmailModel> enviarEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = EmailMapper.INSTANCE.toEmailEntity(emailDto);
        //BeanUtils.copyProperties(emailDto, emailModel);

        emailService.enviarEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping()
    @ApiOperation(value = "Listar todos os Emails enviados")
    public ResponseEntity<List<EmailModel>> listarEmailsEnviado() {
        List<EmailModel> lista = emailService.listarEmailsEnviado();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (EmailModel dto :
                    lista) {
                Long id = dto.getIdEmail();
                dto.add(linkTo(methodOn(EmailController.class).recuperarEmailEnviado(id)).withSelfRel());
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "obter email enviado por ID")
    public ResponseEntity<EmailModel> recuperarEmailEnviado(@PathVariable(value = "id") Long id) {
        Optional<EmailModel> email = emailService.obterEmailEnviado(id);

        if (!email.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            email.get().add(linkTo(methodOn(EmailController.class).listarEmailsEnviado()).withRel("Lista de Emails Enviados"));
            return new ResponseEntity<>(email.get(), HttpStatus.OK);
        }
    }
}
