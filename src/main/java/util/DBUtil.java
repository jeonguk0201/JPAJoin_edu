//모든 DAO들이 공통적으로 사용되는 클래스
/* interface는 절대 객체 생성 불가
 * 	- 상수와 미완성 메소드로 구성
 * 	- 미완성 보유인 상태로 객체 생성 불가
 * 	- 주석(스펙, 명령어)
 * 하위클래스 개발 단 미완성 메소드를 재정의 필수
 *  
 * public class SessionImpl implements EntityManager{}
 */
package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DBUtil {
	private static EntityManagerFactory emf;
	// properties.xml 로딩되면서 dbinfo 키와 매핑된 설정 정보로 table 생성
	static {
		emf = Persistence.createEntityManagerFactory("dbinfo");
	}

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();				
	}
	
	public static void close() {
		if(emf != null) {
			emf.close();
			emf = null;
		}
	}
	

}
