package org.zerock.controller;


import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
/*	
	//@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dataFormat  = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dataFormat, false));
	}*/
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo:"+todo);
		return "ex03";
	}
	
	@RequestMapping("")
	public void basic() {
		log.info("basic..............");
	}
	
	@RequestMapping(value="/basic",method= {RequestMethod.GET,RequestMethod.POST} )
	public void basicGET(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("basic get...............");
		log.info("name="+name);
		log.info("age="+age);
	}
	
	@GetMapping("/basicOnlyGet") // get 방식으로만 받을수있음~
	public void basicGET2() {
		log.info("basic get only get..........");
	}
	
	@GetMapping("/ex01") // get 방식으로만 받을수있음~ toString
	public String ex01(SampleDTO dto) {
		log.info(""+dto);
		return "ex01";
	}
	
	@GetMapping("/ex02") // get 방식으로만 받을수있음~
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name="+name);
		log.info("age="+age);
		return "ex02";
	}
	
	@GetMapping("/ex03List") // get 방식으로만 받을수있음~
	public String ex03(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids="+ids);
		return "ex03List";
	}
	
	@GetMapping("/ex03Array") // get 방식으로만 받을수있음~
	public String ex03(@RequestParam("ids") String[] ids) {
		log.info("array ids="+Arrays.toString(ids));
		return "ex03Array";
	}
	
	@GetMapping("/ex03Bean") // get 방식으로만 받을수있음~
	public String ex03Bean(SampleDTOList list) {
		log.info("list dtos:"+list);
		return "ex03Bean";
	}
	
	@GetMapping("/ex04") // get 방식으로만 받을수있음~
	public String ex04(SampleDTO dto,@ModelAttribute("page") int page) {
		log.info("dto:"+dto);
		log.info("page:"+page);
		return "/sample/ex04";
	}
	
	@GetMapping("/ex05")
	public void ex05() {
		log.info("ex/05...........");
	}
	
	@GetMapping("/ex06")
	@ResponseBody 
	public SampleDTO ex06() {
		log.info("/ex06..............");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07..............");
		
		String msg="{\"name\":\"홍길동\"}";  //msg="name":"홍길동"
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type","application/json;charset=UTF-8");
		return new ResponseEntity<String>(msg,header,HttpStatus.OK);
	}
}
