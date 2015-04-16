package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestJPAServlet extends HttpServlet {
    @PersistenceUnit(unitName = "JPAServletPU")
    private EntityManagerFactory emf_;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("enter TestJPAServet.doGet");

		resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        EntityManager em = null;
        em = emf_.createEntityManager();
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
            queryVisitors.setParameter("assetName", "anma");
            List<Object[]> visitors = queryVisitors.getResultList();
            count = visitors.size();
            out.println("<div id=\"list\">\n"); 
            out.println("<h2>Recent Visitors</h2>\n"); 
            out.println("<table>\n"); 
            out.println("<thead>\n"); 
            out.println("<tr>\n"); 
            out.println("<th>ID</th>\n"); 
            out.println("<th>Name</th>\n"); 
            out.println("<th>Visit Date</th>\n"); 
            out.println("<th>Comment</th>\n"); 
            out.println("</tr>\n"); 
            out.println("</thead>\n"); 
            out.println("<tbody>\n");

            for ( Object[] visitor2 : visitors) {
                out.println("<tr>\n"); 
            	out.println("<td>"+visitor2.getClass().getName()+"</td>");
//            	MsgConfig123 visitor = (MsgConfig123)visitor2;
                out.println("<td>" + visitor2[0] + "</td>\n"); 
                out.println("<td>" + visitor2[1] + "</td>\n"); 
                out.println("<td>" + visitor2[2] + "</td>\n"); 
                out.println("</tr>\n"); 
            }
            out.println("</tbody>\n"); 
            out.println("</table>\n"); 
            out.println("<p>Number of Visitors <span class=\"amount\">" + count + "</span></p>\n");
            out.println("\n");
            out.println("</div>\n"); 
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
