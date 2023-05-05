package in.vishnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vishnu.entity.StudentEnqEntity;


public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity,Integer>{

	

}
