package in.vishnu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
*/

@Entity
@Table(name = "AIT_cOURSE_NAME")
@Data
public class CourseEntity {
	
	@Id
	@GeneratedValue
	private Integer CourseId;
	
	private String CourseName;

}
