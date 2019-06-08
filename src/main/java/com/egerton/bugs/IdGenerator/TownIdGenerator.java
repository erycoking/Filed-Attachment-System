package com.egerton.bugs.IdGenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TownIdGenerator implements IdentifierGenerator{

	@Override
	public Serializable generate(SessionImplementor session, Object obj) 
			throws HibernateException {
		 String prefix = "T";
		 Connection connection = session.connection();

	        try {
	            Statement statement=connection.createStatement();

	            ResultSet rs=statement.executeQuery("select townid from bugs_final.town");

	            if(rs.last())
	            {
	                String id=rs.getString(1);
	                int tid = Integer.valueOf(id.substring(1));
	                int new_tid  = tid+1;
	                String generatedId = prefix + new Integer(new_tid).toString();
	                return generatedId;
	            }else{
	            	return prefix+"1001";
				}
	        } catch (SQLException e) {
	            
	            e.printStackTrace();
	        }
		
		
		
		return null;
	}

}
