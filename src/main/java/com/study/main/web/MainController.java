package com.study.main.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping({"/", "/index"})
	public String home() {
		return "This is MainContoller";
	}
}
