package in.vishnu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishnu.binding.DashboardResponse;
import in.vishnu.binding.EnquiryForm;
import in.vishnu.binding.EnquirySearchCriteria;
import in.vishnu.entity.CourseEntity;
import in.vishnu.entity.EnqStatusEntity;
import in.vishnu.entity.StudentEnqEntity;
import in.vishnu.entity.UserDtlsEntity;
import in.vishnu.repo.CourseRepo;
import in.vishnu.repo.EnqStatusRepo;
import in.vishnu.repo.StudentEnqRepo;
import in.vishnu.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	DashboardResponse response = new DashboardResponse();
	
	@Autowired
	public CourseRepo courseRepo;
	
	@Autowired
	public EnqStatusRepo enqStatusRepo;
	
	@Autowired
	public StudentEnqRepo studentRepo;
	
	@Autowired
	public UserDtlsRepo  userDtlsRepo;
	
	@Autowired
	public HttpSession session;
	
	@Override
	public List<String> getCourses() {
		

		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		
		return names;
	
	}

	@Override
	public List<String> getEnqStatuses() {
		
	    List<EnqStatusEntity> findAll = enqStatusRepo.findAll();

		List<String> status = new ArrayList<>();
		
		for(EnqStatusEntity entity : findAll) {
			status.add(entity.getEnqStatus());
		}
		
			return status;
		
		
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);

		if(findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			
			List<StudentEnqEntity> enquries = userEntity.getEnquries();
			
            Integer totalEnquriesCnt = enquries.size();
			
            Integer enrolledCnt = enquries.stream()			
            .filter(e -> e.getEnqStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size();
            
            Integer lostCnt = enquries.stream()
            .filter(e -> e.getEnqStatus().equals("Lost"))
            .collect(Collectors.toList()).size();
            
            response.setTotalEnquriesCnt(totalEnquriesCnt);
            response.setEnrolledCnt(enrolledCnt);
            response.setLostCnt(lostCnt);
			}
		
		return response;
		
	
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(form,entity);
		Integer userId = (Integer)session.getAttribute("userId");
		
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		
		entity.setUser(userEntity);
		enqStatusRepo.save(entity);
		
		
		
		
		return true;
	}



	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquires = userDtlsEntity.getEnquries();
			
			if(null!=criteria.getCourseName()
					& !"".equals(criteria.getCourseName())) {
				enquires = enquires.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}

			if(null!=criteria.getEnqStatus()
					& !"".equals(criteria.getEnqStatus())) {
				enquires = enquires.stream()
				.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getClassMode()
					& !"".equals(criteria.getClassMode())) {
				enquires = enquires.stream()
				.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			
			
			return enquires;		
		}
		return null;
		
}
	public StudentEnqEntity getEnq(Integer enqId)
	{
		Optional<StudentEnqEntity> enq = studentRepo.findById(enqId);
	     return enq.get();
	}
	

	@Override
	public String updateEnq(Integer enqId, EnquiryForm formObj) {
		
		Optional<StudentEnqEntity> enq = studentRepo.findById(enqId);
		if(enq.isPresent())
		{
			StudentEnqEntity enqEntity = enq.get();
			enqEntity.setStudentName(formObj.getStudentName());
			enqEntity.setStudentPhno(formObj.getStudentPhno());
			enqEntity.setClassMode(formObj.getClassMode());
		    enqEntity.setCourseName(formObj.getCourseName());
			enqEntity.setEnqStatus(formObj.getEnqStatus());
			
			studentRepo.save(enqEntity);
			return " Edit Updated..";
		}
		
		return "Failed";
	}
	
	public List<StudentEnqEntity> getEnquires(){
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquires = userDtlsEntity.getEnquries();
			return enquires;		
		}
		return null;
	}
}