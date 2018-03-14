package test.spring.security.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import test.spring.security.domain.User;
import test.spring.security.domain.UserForm;
import test.spring.security.service.UserService;
@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * 查询所用用户
	 * @return
	 */
	@GetMapping
	public ModelAndView list(Model model) {

		List<?> list = new ArrayList<>();	// 当前所在页面数据列表
//		list.add(new User(1L, "noukey", 30));
//		list.add(new User(2L,"老卫", 29));
		model.addAttribute("title", "用户管理");
		model.addAttribute("userList", userService.listUsers());
		
		return new ModelAndView("users/list", "userModel", model);
	}
	
	@GetMapping("listUsers")
	public ModelAndView listUsers(Model model,UserForm user) {
		model.addAttribute("userList", userService.listUsers(user));
		return new ModelAndView("users/list", "userModel", model);
	}
	
	@RequestMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_USER_ADD')")
	public String addUser(Model model,User user) {
		System.out.println(user);
		userService.addUser(user);
		return "redirect:/users";
	}
 

}
