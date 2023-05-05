package in.vishnu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vishnu.binding.DashboardResponse;
import in.vishnu.binding.EnquiryForm;
import in.vishnu.binding.EnquirySearchCriteria;
import in.vishnu.entity.StudentEnqEntity;
import in.vishnu.repo.StudentEnqRepo;
import in.vishnu.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	public EnquiryService enqService;

	@Autowired
	public HttpSession session;

	@Autowired
	public StudentEnqRepo studentRepo;

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		if(session.getAttribute("enqId")!= null)
		{
			enqService.updateEnq((Integer)session.getAttribute("enqId"), formObj);
			session.removeAttribute("enqId");
			model.addAttribute("successMsg","Enquiry Updated");
		}
		else {
			boolean status = enqService.saveEnquiry(formObj);
			if (status) {
				model.addAttribute("successMsg", "Enquiry Added..");
			} else {
				model.addAttribute("errMsg", "Problem Occured..");
			}
		}
		return "add-enquiry";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}


	@GetMapping("/viewenquires")
	public String getAllproducts(Model model) {
		List<StudentEnqEntity> list = studentRepo.findAll();
		model.addAttribute("enquires", list);
		return "View-enquires";
	}



	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		//model.addAttribute("formObj", new EnquiryForm());

		initForm(model);

		return "add-enquiry";
	}



	private void initForm(Model model) {

		List<String> courses = enqService.getCourses();


		List<String> enqStatus = enqService.getEnqStatuses();


		EnquiryForm formObj = new EnquiryForm();


		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatus);
		model.addAttribute("formObj", formObj);

	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		List<StudentEnqEntity> enquires = enqService.getEnquires();
		model.addAttribute("enquires", enquires);
		return "View-enquires";

	}

	@GetMapping("/filter-enquries")
	public String getFilteredEnqs(@RequestParam("cname") String cname, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquires", filteredEnqs);

		return "filter-enquiry-page";
	}

	@GetMapping("/enqu")
	public String enquiry(Model model)
	{
		initForm(model);
		EnquiryForm enqForm=new EnquiryForm();
		if(session.getAttribute("enq")!=null)
		{
			StudentEnqEntity enq = (StudentEnqEntity)session.getAttribute("enq");
			BeanUtils.copyProperties(enq, enqForm);
			session.removeAttribute("enq");
		}
		model.addAttribute("formObj", enqForm);
		return "add-enquiry";
	}


	@GetMapping("/edit/{id}")
	public String editEnquiry(@PathVariable("id") Integer id) {
		StudentEnqEntity enq= enqService.getEnq(id);
		session.setAttribute("enq", enq);
		session.setAttribute("enqId", id);
		return "redirect:/enqu";
	}
}

