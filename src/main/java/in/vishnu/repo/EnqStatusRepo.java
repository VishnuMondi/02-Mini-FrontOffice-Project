package in.vishnu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vishnu.entity.EnqStatusEntity;
import in.vishnu.entity.StudentEnqEntity;


public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>{

	

	public List<EnqStatusEntity> findAll();

	public void save(StudentEnqEntity enqEntity);

	/*public void saveAll(List<EnqStatusEntity> asList);

	public void deleteAll();*/

	

}
 