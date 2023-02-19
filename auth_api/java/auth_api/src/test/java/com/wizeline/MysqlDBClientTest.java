package com.wizeline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MysqlDBClientTest {
    @Test
    void validationCredential() {
        MysqlDBClient mysqlDBClient = MysqlDBClient.getInstance();
        Assertions.assertEquals("admin", mysqlDBClient.validationCredential("admin", "secret"));
        Assertions.assertEquals("editor", mysqlDBClient.validationCredential("noadmin", "noPow3r"));
        Assertions.assertEquals("viewer", mysqlDBClient.validationCredential("bob", "thisIsNotAPasswordBob"));
    }
}
