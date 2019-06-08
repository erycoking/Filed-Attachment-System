package com.egerton.bugs.IdGenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyIdGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SessionImplementor session, Object obj) 
			throws HibernateException {
		 String prefix = "C";
		 Connection connection = session.connection();

	        try {
	            Statement statement=connection.createStatement();

	            ResultSet rs=statement.executeQuery("select companyid from bugs_final.company");

	            if(rs.last())
	            {
	                String cid=rs.getString("companyid");
	                int id = Integer.valueOf(cid.substring(1));
					System.out.println(id);
					int new_id = id+1;
					String generatedId = prefix + new Integer(new_id).toString();
					System.out.println(generatedId);
					return generatedId;
	            }else {
	            	return prefix+"1001";
				}
	        } catch (SQLException e) {
	            
	            e.printStackTrace();
	        }
		
		
		
		return null;
	}

}
