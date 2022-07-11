package it.aruba.gamma.controller;

import io.swagger.annotations.ApiOperation;
import it.aruba.gamma.model.ServiceResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@Validated
@RequestMapping("/secured")
public class ArchiveFileController {



	@ApiOperation(value = "Start process for store document signed")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/v1/storeSecuredDoc")
	public ResponseEntity<ServiceResponseWrapper> storeSecuredDoc(@RequestBody Map<String,String> param) {
		log.info("Start process for Store secured document");
		return new ResponseEntity<>(ServiceResponseWrapper.buildSuccessWrapper(param), HttpStatus.OK);
	}



}
