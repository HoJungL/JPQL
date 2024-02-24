package jsql;

import javax.persistence.*;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);


            // 실제로는 이렇게합니다.
            Member result = em.createQuery("select m from Member m where m.username= :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("result = " + result.getUsername());

            /* 복잡하게 하면 이렇게합니다.
            // 반환 타입이 명확하지 않을때 (스트링 + 인트)
//            Query query1 = em.createQuery("select m.username, m.age from Member m");

            // 반환 타입이 명확할때 (스트링만 있거나, 인트만 있거나)
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username= :username", Member.class);
            // parameter 변경
            query.setParameter("username", "member1");
            // 하나만 가져올때!
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult : " + singleResult);
*/

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
