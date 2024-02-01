package org.dmetasoul.lakesoul;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.zeppelin.conf.ZeppelinConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName DBUtils
 * @Description Lakesoul Dashboard Backend DB Utils
 * @createTime 2023/7/31 16:35
 */
public class DBUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    private static final HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    private ZeppelinConfiguration zconf;

     private DBUtils() {
        zconf = ZeppelinConfiguration.create();
        config.setJdbcUrl(zconf.getLakesoulDashBoardPGUrl());
        config.setUsername(zconf.getLakesoulDashBoardPGUserName());
        config.setPassword(zconf.getLakesoulDashBoardPGPassword());
        config.setMaximumPoolSize(5);
        ds = new HikariDataSource(config);

    }

    public static DBUtils getInstance(){
        return DBUtilHolder.instance;
    }

    public static HikariDataSource getDs(){
         return DBUtilHolder.instance.ds;
    }

    /**
     * 内部类实现单例懒加载
     */
    private static class DBUtilHolder {
        private static final DBUtils instance = new DBUtils();

    }


    public static String getPasswordByName(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        // Query the user table and return a list of User objects
        String query = "SELECT pg_password FROM t_user where name = ?";
        Object[] params = {name};
        LOGGER.info("Start Query User {} Lakesoul Password ....", name);
        String password = queryRunner.query(query, new ScalarHandler<>(),params);

        if (password != null) {
            LOGGER.info("Query User {} Lakesoul Password Success.", name);
            return password;
        }
        return null;

    }

    public static boolean isUserInWorkSpace(String userId, String workspace) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        String query = "SELECT uid,workspace_id FROM t_user_workspace_role LEFT JOIN t_user ON t_user.id = t_user_workspace_role.user_id WHERE workspace_id IN (SELECT id FROM t_workspace WHERE name= ?) and t_user.uid = ?";
        Object[] params = {workspace, userId};
        LOGGER.info("Start Query User {} is user in WorkSpace {}", userId, workspace);
        Object[] result = queryRunner.query(query, new ArrayHandler(), params);

        return result.length > 0;

    }

    public static boolean isAdminInWorkSpace(String name, String workspace) {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        String query = "SELECT role_type " +
                "FROM t_user_workspace_role tuwr " +
                "INNER JOIN t_role tr ON tuwr.role_id = tr.id " +
                "INNER JOIN t_user tu ON tuwr.user_id = tu.id " +
                "INNER JOIN t_workspace tw ON tuwr.workspace_id = tw.id " +
                "WHERE tu.name = ? " +
                "AND tw.name = ?";
        Object[] params = {name, workspace};
        try {
            int result = queryRunner.query(query, rs -> rs.next() ? rs.getInt(0) : -1, params);
            LOGGER.info("User {}'s role in workspace {} is {}", name, workspace, result);
            return result == 0 || result == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Query user role in workspace failed", e);
        }
    }
}

