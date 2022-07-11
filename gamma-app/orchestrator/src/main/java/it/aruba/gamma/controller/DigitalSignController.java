package it.aruba.gamma.controller;

import io.swagger.annotations.ApiOperation;
import it.aruba.gamma.model.ServiceResponseWrapper;
import it.aruba.gamma.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@Validated
@RequestMapping("/secured")
public class DigitalSignController {



	@ApiOperation(value = "Start process for Sign document")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/v1/signDoc")
	public ResponseEntity<ServiceResponseWrapper> signDoc(@RequestBody Map<String,String> param) {
		log.info("Start process for Sign document");
		return new ResponseEntity<>(ServiceResponseWrapper.buildSuccessWrapper(param), HttpStatus.OK);
	}



}
