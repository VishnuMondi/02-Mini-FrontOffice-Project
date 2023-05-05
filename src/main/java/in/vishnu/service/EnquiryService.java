package in.vishnu.service;

import java.util.List;

import in.vishnu.binding.DashboardResponse;
import in.vishnu.binding.EnquiryForm;
import in.vishnu.binding.EnquirySearchCriteria;
import in.vishnu.entity.StudentEnqEntity;

public interface EnquiryService {

	public List<String> getCourses();

	public List<String> getEnqStatuses();

	public DashboardResponse getDashboardData(Integer userId);

	public boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnqEntity> getEnquires();

	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria,Integer userId);

	public StudentEnqEntity getEnq(Integer id);

	public String updateEnq(Integer enqId, EnquiryForm formObj);

}







