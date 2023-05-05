package in.vishnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vishnu.entity.CourseEntity;

public interface CourseRepo  extends JpaRepository<CourseEntity, Integer>{
	
	/*public List<CourseEntity> findAll();

	public void saveAll(List<CourseEntity> asList);

	public void deleteAll();*/

}
