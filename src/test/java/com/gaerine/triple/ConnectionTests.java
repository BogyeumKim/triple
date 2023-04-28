package com.gaerine.triple;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
public class ConnectionTests {

    @Autowired DataSource dataSource;

    @Test
    public void testConnect(){
        try{
            Connection con = dataSource.getConnection();
            log.info(String.valueOf(con.getMetaData()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
