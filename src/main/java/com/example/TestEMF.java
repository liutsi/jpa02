package com.example;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TestEMF {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAServletPU", null);
		EntityManager em = emf.createEntityManager();

        int count = 0;
        try {
        	//JPL的SQL的语法是JPQL和SQL是不一致的
        	//表名是区分大小写的，会根据表名查找Entity注解的类来填充，所以这俩名字必须大小写保持一致
        	//表必须用别名，不用别名就报UnwantedTokenException异常
        	//JPA的sql语句不支持select *必须列举查询的列名
        	//这里的列名和Entity里的属性值也必须大小写一致对上
        	//getSingleResult需要对应只有一条记录，如果查到多条会报javax.persistence.NonUniqueResultException: More than one result was returned from Query.getSingleResult()
        	//但是似乎Entity.Id主键在表里不存在，并不影响什么
        	//即使是Object[]形式的查询，也一样需要Entity对上的，所以ModelClass必不可少
            Query queryVisitors = em.createQuery(
                "SELECT v.lpurl, v.siteName,v.assetId,v.assetName,v.instance from msgconfig v where v.assetName=:assetName" //select * is not supported, must be detailed column names
              );
            queryVisitors.setParameter("assetName", "0210001");
            List<Object[]> visitors = queryVisitors.getResultList();
            count = visitors.size();


            for ( Object[] visitor2 : visitors) {
                System.out.println("<tr>\n"); 
            	System.out.println("<td>"+visitor2.getClass().getName()+"</td>");
//            	MsgConfig123 visitor = (MsgConfig123)visitor2;
                System.out.println("<td>" + visitor2[0] + "</td>\n"); 
                System.out.println("<td>" + visitor2[1] + "</td>\n"); 
                System.out.println("<td>" + visitor2[2] + "</td>\n"); 
                System.out.println("<td>" + visitor2[3] + "</td>\n"); 
                System.out.println("</tr>\n"); 
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } 
//        finally { // em cannot be used after closed
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
        

        try {
        	//JPL的SQL的语法是JPQL和SQL是不一致的
        	//表名是区分大小写的，会根据表名查找Entity注解的类来填充，所以这俩名字必须大小写保持一致
        	//表必须用别名，不用别名就报UnwantedTokenException异常
        	//JPA的sql语句不支持select *必须列举查询的列名
        	//这里的列名和Entity里的属性值也必须大小写一致对上
        	//getSingleResult需要对应只有一条记录，如果查到多条会报javax.persistence.NonUniqueResultException: More than one result was returned from Query.getSingleResult()
        	//但是似乎Entity.Id主键在表里不存在，并不影响什么
        	//即使是Object[]形式的查询，也一样需要Entity对上的，所以ModelClass必不可少
            Query queryVisitors = em.createQuery(
                "SELECT v from msgconfig v where v.assetName=:assetName" //select * is not supported, must be detailed column names
              );
            queryVisitors.setParameter("assetName", "0210001");
            List<MsgConfig123> visitors = queryVisitors.getResultList();
            count = visitors.size();
            for ( MsgConfig123 visitor2 : visitors) {
                System.out.println("<tr>\n"); 
            	System.out.println("<td>"+visitor2.getClass().getName()+"</td>");
//            	MsgConfig123 visitor = (MsgConfig123)visitor2;
                System.out.println("<td>" + visitor2.getAssetName() + "</td>\n"); 
                System.out.println("<td>" + visitor2.getAssetId() + "</td>\n"); 
                System.out.println("<td>" + visitor2.getInstance() + "</td>\n"); 
                System.out.println("<td>" + visitor2.getLpurl() + "</td>\n"); 
                System.out.println("</tr>\n"); 
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
	}

}
