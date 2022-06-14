/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.mysql.createTable;

import com.alibaba.druid.sql.MysqlTest;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat.Column;
import org.junit.Assert;
import org.junit.Test;

public class MySqlCreateTableTest72 extends MysqlTest {
    @Test
    public void test_one() throws Exception {
        String sql = "CREATE TABLE \"MessageInstance\" ("
                + "  \"id\" int(11) NOT NULL AUTO_INCREMENT,"
                + "  \"messageId\" int(11) NOT NULL,"
                + "  PRIMARY KEY (\'id\'),"
                + "  KEY \"ix_messageId\" (\"messageId\")"
                + ")";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement stmt = parser.parseCreateTable();

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        stmt.accept(visitor);

        Column column = visitor.getColumn("MessageInstance", "id");
        Assert.assertNotNull(column);
        Assert.assertEquals("int", column.getDataType());

        {
            String output = SQLUtils.toMySqlString(stmt);
            Assert.assertEquals("CREATE TABLE \"MessageInstance\" ("
                    + "\n\t\"id\" int(11) NOT NULL AUTO_INCREMENT,"
                    + "\n\t\"messageId\" int(11) NOT NULL,"
                    + "\n\tPRIMARY KEY (\'id\'),"
                    + "\n\tKEY \"ix_messageId\" (\"messageId\")"
                    + "\n)", output);
        }

        {
            String output = SQLUtils.toMySqlString(stmt, SQLUtils.DEFAULT_LCASE_FORMAT_OPTION);
            Assert.assertEquals("create table \"MessageInstance\" ("
                    + "\n\t\"id\" int(11) not null auto_increment,"
                    + "\n\t\"messageId\" int(11) not null,"
                    + "\n\tprimary key (\'id\'),"
                    + "\n\tkey \"ix_messageId\" (\"messageId\")"
                    + "\n)", output);
        }
    }
}
