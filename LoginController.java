package com.oxy.freelanceplatform.frontend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.oxy.freelanceplatform.models.Role;
import com.oxy.freelanceplatform.models.User;
import com.oxy.freelanceplatform.services.RoleService;
import com.oxy.freelanceplatform.services.UserService;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;


@Controller
public class LoginController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    
    @Autowired
    @Qualifier("userValidator")
    Validator userValidator;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login/login";
    }

    @GetMapping("/register")
    public String register(){
        return "login/register";
    }

    @PostMapping("/register")
    public ModelAndView doRegister(@Valid User user, BindingResult bindingResult, Model model){

        userValidator.validate(user, bindingResult);

        if( bindingResult.hasErrors() ){

            StringBuilder sb = new StringBuilder();

            bindingResult.getAllErrors().forEach(e -> {


                sb.append(StringUtils.capitalize(e.getDefaultMessage()));
                sb.append("<br>");
            });

            model.addAttribute("error", sb);

        } else {
            userService.save(user);
            return new ModelAndView("redirect:/login");
        }
        model.addAttribute("user", user);
        return new ModelAndView("login/register", model.asMap());
    }
    
    @GetMapping("/update/user/{id}")
    public String updateUser(Model model, @PathVariable("id") long id){
    	
    	 boolean hasRoleAdmin = false;
    	 User user = super.getCurrentUser();
    	 
    	 Set<Role> rolesUsr = user.getRoles();
    	 for(Role rol : rolesUsr ){
    		// System.out.println("ROLE= "+ rol.getName());
	    	 if (rol.getName().equals("ADMIN")) {
	    	     hasRoleAdmin = true;
	    	     break;
	    	 }
    	 }
    	 
    	if (hasRoleAdmin) {
    		User usertoedit = userService.findOne(id);
    		model.addAttribute("user", usertoedit);
    	}else{
    		model.addAttribute("user", user);
    	}
    	
    	List<Role> roles = roleService.findAll();
    	model.addAttribute("roles", roles);
    	
    	return "login/update_user";
    }
    
    @PostMapping("/update/user")
    public String doUpdateUser(Model model, @Valid User u){
    	
    	List<Role> roles = roleService.findAll();
    	model.addAttribute("roles", roles);
    	
    	if( u instanceof User != true) {
    		model.addAttribute("errors", "Une erreur est survenu, merci de contacter votre Adminisrateur");
    		
    		return "login/update_user";
            //return ;
        }
    	 // Check if password is empty or too short
    	if(u.getPassword().length() < 6){
        	model.addAttribute("errors", "Password must be longer than 5 characters");
        	return "login/update_user";
        }
        User user = (User) u;
        userService.save(user);
        
    	User updatedUser = userService.findOne(user.getId());
    	   	
    	model.addAttribute("user", updatedUser);
    	
        return "login/update_user";
    }
}
