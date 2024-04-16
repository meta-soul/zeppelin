package org.dmetasoul.lakesoul;

import com.google.gson.JsonParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.notebook.repo.NotebookRepoWithVersionControl;
import org.postgresql.util.PGobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static String getRealNameByName(String name) throws IOException {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        // Query the user table and return a list of User objects
        String query = "SELECT extra FROM t_user where name = ?";
        Object[] params = {name};
        LOGGER.info("Start Query Lakesoul Real UserName {} ....", name);
        String extra = null;
        try {
            extra = queryRunner.query(query, rs -> {
                if (rs.next()) {
                    // 获取 PGobject 对象
                    PGobject pgObject = (PGobject) rs.getObject("extra");
                    // 将 PGobject 转换为字符串形式
                    return pgObject.getValue();
                }
                return null;
            },params);
        } catch (SQLException e) {
            throw new IOException(e);
        }

        if (extra != null) {
            String realName = new JsonParser().parse(extra).getAsJsonObject().get("real_name").getAsString();
            LOGGER.info("Query Lakesoul {} Real UserName Success,realName is {}", name, realName);
            return realName;
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

    public static boolean isUserInWorkSpaceByName(String username, String workspace) {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        String query = "SELECT * FROM t_user u JOIN t_user_workspace_role uwr ON u.id = uwr.user_id JOIN t_workspace w ON uwr.workspace_id = w.id WHERE u.name = ? AND w.name = ?";
        Object[] params = {username, workspace};
        LOGGER.info("Start Query User {} is user in WorkSpace {}", username, workspace);
        try {
            Object[] result = queryRunner.query(query, new ArrayHandler(), params);
            return result.length > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Query user role in workspace failed", e);
        }
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
            int result = queryRunner.query(query, rs -> rs.next() ? rs.getInt(1) : -1, params);
            LOGGER.info("User {}'s role in workspace {} is {}", name, workspace, result);
            return result == 0 || result == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Query user role in workspace failed", e);
        }
    }

    public static boolean saveNoteInfo(String noteRealPath, String notePath, String noteId, String noteVersion,
                                       String noteMessage, int time) {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        String query = "INSERT INTO t_note_info (note_real_path, note_path, note_id, note_version, note_message, note_update_time) VALUES " +
                "(?, ?, ?, ? ,?, ?)";

        Object[] params = {noteRealPath, notePath, noteId, noteVersion, noteMessage, time};
        try {
            String result = queryRunner.insert(query, rs -> rs.next() ? rs.getString(1) : null, params);
            LOGGER.info("Insert into t_note_info table info note_path:{}, note_id:{}, note_version:{}, note_message:{} result id {}",
                    notePath, noteId, noteVersion, noteMessage, result);
            return result.equalsIgnoreCase(noteRealPath);
        } catch (SQLException e) {
            throw new RuntimeException("Query user role in workspace failed", e);
        }
    }

    public static List<NotebookRepoWithVersionControl.Revision> listHistoryNoteInfo(String notePath, String noteId) {
        QueryRunner queryRunner = new QueryRunner(DBUtils.getDs());
        List<NotebookRepoWithVersionControl.Revision> revisions = new ArrayList<>();
        String query = "SELECT note_version, note_message, note_update_time from t_note_info WHERE note_path = ? AND note_id = ? order by note_update_time desc";

        Object[] params = {notePath, noteId};

        try {
            queryRunner.query(query, rs -> {
                while (rs.next()) {
                    // 获取 PGobject 对象
                    String noteVersion = rs.getString("note_version");
                    String noteMessage = rs.getString("note_message");
                    int noteUpdateTime = rs.getInt("note_update_time");
                    NotebookRepoWithVersionControl.Revision revision = new NotebookRepoWithVersionControl.Revision(noteVersion, noteMessage, noteUpdateTime);
                    revisions.add(revision);
                }
                return revisions;
            }, params);
            LOGGER.info("Select from t_note_info note_path:{}, note_id:{}, result size is {}", notePath, noteId, revisions.size());
            return revisions;
        } catch (SQLException e) {
            throw new RuntimeException("Query user role in workspace failed", e);
        }
    }
}

