package step04.miniproject;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.Test;
import util.DBUtil;

public class Step04RunTest {

	@Test
	public void step01Test() {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = DBUtil.getEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Dept d1 = new Dept(2L, "test1", "test2");
			em.persist(d1);
			Emp newEmployee = new Emp(1L, "이름", "직업", 22L, new Date(System.currentTimeMillis()), 3000L, 200L, d1);
			em.persist(newEmployee);

			tx.commit();
			printEmp(em);
			updateDeptLoc(em);
			deleteDeptAndEmp(em);

		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	private void printEmp(EntityManager em) {
		List<Emp> employees = em.createQuery("SELECT e FROM Emp e JOIN e.deptNo", Emp.class).getResultList();
		int index = 1;

		for (Emp emp : employees) {
			System.out.println("============== 직원 정보 [" + index + "] ==============");
			System.out.println("사원 번호: " + emp.getEmpNo());
			System.out.println("이름: " + emp.getEName());
			System.out.println("직업: " + emp.getJob());
			System.out.println("관리자: " + emp.getManager());
			System.out.println("입사일: " + emp.getHireDate());
			System.out.println("급여: " + emp.getSalary());
			System.out.println("커미션: " + emp.getComm());
			System.out.println("-------------- 부서 정보 --------------");
			if (emp.getDeptNo() != null) {
				System.out.println("부서 번호: " + emp.getDeptNo().getDEPTNO());
				System.out.println("부서 이름: " + emp.getDeptNo().getDNAME());
				System.out.println("부서 위치: " + emp.getDeptNo().getLOC());
			} else {
				System.out.println("연관된 부서 정보가 없습니다.");
			}
			System.out.println("======================================\n");
			index++;
		}
	}

	private void updateDeptLoc(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Dept> depts = em.createQuery("SELECT d FROM Dept d WHERE d.DEPTNO = 2", Dept.class).getResultList();

		System.out.println("====== 부서 정보 업데이트 시작 ======");
		for (Dept dept : depts) {
			if (dept != null) {
				String oldLoc = dept.getLOC();
				dept.setLOC("PARIS");
				System.out.println("부서 번호: " + dept.getDEPTNO());
				System.out.println("부서 이름: " + dept.getDNAME());
				System.out.println("위치가 " + oldLoc + "에서 " + dept.getLOC() + "(으)로 업데이트되었습니다.");
				System.out.println("-----------------------------------");
			}
		}
		System.out.println("====== 부서 정보 업데이트 완료 ======\n");
		tx.commit();
	}

	private void deleteDeptAndEmp(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Dept> depts = em.createQuery("SELECT d FROM Dept d WHERE d.DEPTNO = 2", Dept.class).getResultList();

		System.out.println("====== 부서 정보 삭제 시작 ======");
		for (Dept dept : depts) {
			if (dept != null) {
				// 부서에 속한 직원들의 관계를 먼저 제거
				List<Emp> employeesInDept = em.createQuery("SELECT e FROM Emp e WHERE e.deptNo = :dept", Emp.class)
						.setParameter("dept", dept)
						.getResultList();

				for (Emp emp : employeesInDept) {
					em.remove(emp);
				}

				System.out.println("삭제할 부서 번호: " + dept.getDEPTNO());
				System.out.println("부서 이름: " + dept.getDNAME());
				System.out.println("위치: " + dept.getLOC());

				em.remove(dept);
				System.out.println("부서 번호 " + dept.getDEPTNO() + "가 성공적으로 삭제되었습니다.");
				System.out.println("-----------------------------------");
			}
		}
		System.out.println("====== 부서 정보 삭제 완료 ======\n");
		tx.commit();
	}
}