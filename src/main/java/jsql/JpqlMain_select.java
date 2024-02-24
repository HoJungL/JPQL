package jsql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain_select {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            // 타입표현(admin)
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);


            em.flush();
            em.clear();

//            String query = "select m from Member m left join m.team t on t.name = 'teamA'";
//            String query2 = "select m from Member m left join Team t on m.username = t.name";
/*            List<Member> result = em.createQuery(query3, Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();*/


            String query= "select m.username, 'HELLO', true from Member m " +
                    "where m.type = :userType";

            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }
            tx.commit();

 /*           // 페이징 API는  select문을 String jpql = ~라고 등록하기.

            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();



            System.out.println("result.size() = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1.getUsername());
            }*/
/*
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();
            List<MemberDTO> result = em.createQuery("select new jsql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());
*/
/*

            Member findMember=  result.get(0);
            findMember.setAge(20); // .영속성 프로젝션에서 관리를 다함!!
*/

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
