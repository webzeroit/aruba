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
public class UserEmailController {


	@Autowired
	public EmailService emailService;


	@GetMapping("/v1/checkSecurity")
	public String securedResource(Authentication auth) {
		return "This is a SECURED resource. Authentication: " + auth.getName() + "; Authorities: " + auth.getAuthorities();
	}

	@ApiOperation(value = "Obtain user email via JWT")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/v1/userEmail")
	public ResponseEntity<ServiceResponseWrapper> getUserEmail() {
		log.info("Obtain user Email ");
		return new ResponseEntity<>(ServiceResponseWrapper.buildSuccessWrapper(emailService.getUserEmail()), HttpStatus.OK);
	}


	@ApiOperation(value = "Search user email ")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/v1/searchEmail")
	public ResponseEntity<ServiceResponseWrapper> searchEmail(@RequestBody Map<String,String> searchFilter) {
		log.info("Search user Email ");
		return new ResponseEntity<>(ServiceResponseWrapper.buildSuccessWrapper(emailService.searchEmail(searchFilter)), HttpStatus.OK);
	}



}
