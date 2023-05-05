package in.vishnu.runner;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.vishnu.entity.CourseEntity;
import in.vishnu.entity.EnqStatusEntity;
import in.vishnu.repo.CourseRepo;
import in.vishnu.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	
   @Autowired
	private CourseRepo courseRepo;
   
   
   @Autowired
   private EnqStatusRepo statusRepo;
   
   @Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java-Realtime-Project");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("AWS");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("DevOps");
		
		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("SpringBoot");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		statusRepo.deleteAll();

		EnqStatusEntity s1 = new EnqStatusEntity();
		s1.setEnqStatus("New");
		
		EnqStatusEntity s2 = new EnqStatusEntity();
		s2.setEnqStatus("Enrolled");
		
		EnqStatusEntity s3 = new EnqStatusEntity();
		s3.setEnqStatus("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1, s2, s3));
	}
}