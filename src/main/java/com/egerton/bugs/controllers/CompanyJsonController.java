
package com.egerton.bugs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.egerton.bugs.Model.Company;
import com.egerton.bugs.Model.Town;
import com.egerton.bugs.Model.View;
import com.egerton.bugs.service.CompanyService;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
public class CompanyJsonController {
	@Autowired
	 private CompanyService companyService;
	
	//Create company Json object for ui autocomplete  	
  	@RequestMapping(value={"/company"}, method = RequestMethod.GET,produces = "application/json")
  	@JsonView(View.Summary.class)  	
  	public  @ResponseBody  List<Company> getAllUsers(@RequestParam("term") String term){
		return companyService.contains(term);
  	    		
  	}	
  	
	//Create Json object for ui autocomplete based on companyId selected
	@RequestMapping(value={"/company/{companyId}"}, method = RequestMethod.GET,produces = "application/json")
	@JsonView(View.Summary.class)
	public  @ResponseBody  List<Town> getAllUsers(@PathVariable("companyId") String companyId,@RequestParam("term") String term){
		return companyService.containsTown(Long.valueOf(companyId), term);
	    		
	}
	


}

