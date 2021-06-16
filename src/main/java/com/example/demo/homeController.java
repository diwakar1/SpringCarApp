package com.example.demo;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;

@Controller
public class homeController {
	@Autowired
	CarRepository carRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CloudinaryConfig cloudinaryConfig;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("cars", carRepository.findAll());
		return "list";
	}

	@GetMapping("/addcar")
	public String addCars(Model model) {
		model.addAttribute("car", new Car());
		model.addAttribute("categories", categoryRepository.findAll());
		return "carform";

	}

	@GetMapping("/addcategory")
	public String addCategories(Model model) {
		model.addAttribute("category", new Category());
		return "categoryform";

	}

	@PostMapping("/addcar")
	public String processCar(@Valid Car car, BindingResult bindingResult, Model model,
			@RequestAttribute("file") MultipartFile file) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "carform";
		}

		
		  try {
		  
		  Map maploader =
		  cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resources","auto")
		  ); 
		  car.setImage(maploader.get("url").toString());
		  
		  
		  }catch(IOException ex) {
			  ex.printStackTrace();
		  
		  }
		 

		carRepository.save(car);
		return "redirect:/";
	}
	

	@PostMapping("/addcategory")
	public String processCategory(@Valid Category category, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "categoryform";
		}
		categoryRepository.save(category);
		return "list";
	}
	
	
	@RequestMapping("/detail/{id}")
	
	public String showDetail(@PathVariable("id")long id,Model model) {
		model.addAttribute("car", carRepository.findById(id).get());
		return "show";
		
		
	}
	

	@RequestMapping("/update/{id}")
	
	public String updateCar(@PathVariable("id")long id,Model model) {
		model.addAttribute("car", carRepository.findById(id).get());
		model.addAttribute("category",categoryRepository.findAll());
		return "carform";
		
		
	}
	

	@RequestMapping("/delete/{id}")
	
	public String deleteCar(@PathVariable("id")long id) {
		carRepository.deleteById(id);
		
		return "redirect:/";
		
		
	}
}



