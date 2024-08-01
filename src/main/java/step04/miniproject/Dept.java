package step04.miniproject;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

@Getter
@Setter
@ToString

@SequenceGenerator(name = "DEPTNO", sequenceName = "DEPTNO",
				   initialValue = 1, allocationSize = 50)
@Entity
public class Dept {

	@Id
	@Column(name = "DEPTNO")
	private Long DEPTNO;
	
	@NonNull
	@Column(name="DNAME", length = 20)
	private String DNAME;
	
	@NonNull
	@Column(name="LOC", length = 20)
	private String LOC;

}