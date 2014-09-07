package com.rudenkoInc.eshop.dao.externalTxDao.impl;


import com.rudenkoInc.eshop.dao.exceptions.DaoException;
import com.rudenkoInc.eshop.dao.exceptions.DaoSystemException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

    public static void closeQuietly(Connection conn)  {
        try{
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e){
            //NOP
        }

    }

    public static void closeQuietly(ResultSet rs, Statement stmt)  {
        try{
            if(rs != null && stmt != null){
                stmt.close();
                rs.close();
            }
        } catch (SQLException e){
            //NOP
        }
    }

}
