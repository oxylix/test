package com.oxy.freelanceplatform.frontend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oxy.freelanceplatform.models.Pager;
import com.oxy.freelanceplatform.models.Role;
import com.oxy.freelanceplatform.models.User;
import com.oxy.freelanceplatform.services.RoleService;
import com.oxy.freelanceplatform.services.UserService;


@EnableAutoConfiguration
@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final int BUTTONS_TO_SHOW = 5; 
	private static final int INITIAL_PAGE = 0; 
	private static final int INITIAL_PAGE_SIZE = 5; 
	private static final int[] PAGE_SIZES = { 2, 5, 10, 20 }; 

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
    UserService userService;
	
	@Autowired
    RoleService roleService;
	
	@GetMapping
    public String home(Model model){
        return "frontend/admin/dashboard";
    }

	@GetMapping("/roles")
    public String rolesMapping(Model model, @RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page ){
		
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		
		List<Role> roles = roleService.findAll();
		Page <User> users = userService.findAllPageable(new PageRequest(evalPage, evalPageSize));
		Pager pager = new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
		
		
		model.addAttribute("roles", roles);
		model.addAttribute("users", users);
		model.addAttribute("selectedPageSize", evalPageSize); 
		model.addAttribute("pageSizes", PAGE_SIZES); 
		model.addAttribute("pager", pager); 

		
        return "frontend/admin/roles";
    }
	
		
	@PostMapping("/roles")
	public String dorolesMapping(Model model, @RequestParam  ArrayList<User> users){
        
		log.info("Roles : {}" , users);
		
		if(!users.isEmpty()){
			for (User user : users) {
				log.info("Roles postés: {}" , user.getId());
				
				// User user = userService.findOne();
				
				// userService.save(user);
			}
		}
		
		List<Role> rolesUpdated = roleService.findAll();
		Page <User> usersUpdate = userService.findAllPageable(new PageRequest(INITIAL_PAGE, INITIAL_PAGE_SIZE));
		Pager pager = new Pager(usersUpdate.getTotalPages(), usersUpdate.getNumber(), BUTTONS_TO_SHOW);
		
		model.addAttribute("roles", rolesUpdated);
		model.addAttribute("users", usersUpdate);
		// model.addAttribute("selectedPageSize", evalPageSize); 
		model.addAttribute("pageSizes", PAGE_SIZES); 
		model.addAttribute("pager", pager); 
		
		return "frontend/admin/roles";
    }
}
