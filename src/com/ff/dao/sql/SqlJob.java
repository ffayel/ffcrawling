package com.ff.dao.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ff.dao.data.Job;
import com.ff.dao.data.Job.STATUS;
import com.ff.dao.data.Site;
import com.ff.dao.data.SiteImage;
import com.ff.dao.exception.AuthException;
import com.ff.dao.exception.FFCrawlSqlException;
import com.ff.dao.tool.MySgbd;
import com.ff.dao.tool.MySgbd.SETTER_TYPE;
import com.ff.tool.MyTime;

public final class SqlJob {
	private final static long auth = 61686052;
	private final static String authMsg = "com.ff.dao.sql.SqlJob must be only call by com.ff.dao.util.UtilJob";

	public static STATUS getStatus(final long controle, final int jobId) {
		if (auth != controle)
			throw new AuthException(authMsg);
		STATUS result = null;
		ResultSet rs = null;
		final MySgbd monTool = new MySgbd();
		try {
			final String query = "SELECT job.`status` FROM `job` WHERE job.id = ? ";
			final Object[][] param = { { SETTER_TYPE.INT, jobId } };
			rs = monTool.doQueryResultSet(query, param);
			if (rs.next()) {
				for (STATUS s : STATUS.values()) {
					if (s.ordinal() == rs.getInt(1)) {
						result = s;
						break;
					}
				}
			}
			if (result == null) {
				throw new NullPointerException("no data in BDD");
			}
		} catch (SQLException e) {
			throw new FFCrawlSqlException(e);
		} catch (NullPointerException e) {
			throw new FFCrawlSqlException(e);
		} finally {
			monTool.closePool();
		}
		return result;
	}

	public static Job get(final long controle, final int jobId) {
		if (auth != controle)
			throw new AuthException(authMsg);
		Job result = null;
		ResultSet rs = null;
		final MySgbd monTool = new MySgbd();
		try {
			final String query = "SELECT job.id, job.`status`, job.datecreate, " + " site.id AS siteid, site.url as siteurl, "
					+ " image.id as imgid, image.url as imgurl, image.dateload as imgdateload  " + " FROM job "
					+ " LEFT JOIN lnk_site_job ON lnk_site_job.id_job = job.id " + " LEFT JOIN site ON lnk_site_job.id_site = site.id "
					+ " LEFT JOIN lnk_img_site ON lnk_img_site.id_site = site.id " + " LEFT JOIN image ON image.id = lnk_img_site.id_img "
					+ " WHERE job.id = ? ";
			final Object[][] param = { { SETTER_TYPE.INT, jobId } };
			rs = monTool.doQueryResultSet(query, param);
			if (rs.next()) {
				result = new Job(rs.getInt(1), rs.getInt(2), MyTime.getCalendar(rs.getTimestamp(3)));
				Site newSite = null;
				do {
					int siteId = rs.getInt(4);
					if (!rs.wasNull()) {
						if (newSite == null || (newSite != null && siteId != newSite.getId())) {
							newSite = new Site(siteId, rs.getString(5));
							result.addSite(newSite);
						}

						int imgId = rs.getInt(6);
						if (!rs.wasNull()) {
							SiteImage sImg = new SiteImage(imgId, rs.getString(7), MyTime.getCalendar(rs.getTimestamp(8)));
							newSite.addImage(sImg);
						}
					}
				} while (rs.next());
			}
		} catch (SQLException e) {
			throw new FFCrawlSqlException(e);
		} catch (NullPointerException e) {
			throw new FFCrawlSqlException(e);
		} finally {
			monTool.closePool();
		}
		return result;
	}

	public static int create(final long controle, final List<String> urls) {
		if (auth != controle)
			throw new AuthException(authMsg);

		int newJobId = -1;
		final String query1 = "INSERT INTO `job` (`status`) VALUES (?) ";
		final String query2 = "INSERT INTO `site` (`url`) VALUES (?)";
		final String query3 = "INSERT INTO `lnk_site_job` (`id_job`, `id_site`) VALUES (?, ?)";
		final Object[][] param1 = {{ SETTER_TYPE.INT, STATUS.BEGIN.ordinal() }};
		MySgbd sgbd = new MySgbd();
		try {
			sgbd.getConnection().setAutoCommit(false);

			newJobId = sgbd.doQueryInsertGeneratedKey(query1, param1);
			if (newJobId > 0) {
				for (String url : urls) {
					final Object[][] param2 = { { SETTER_TYPE.STRING, url }};
					int newSiteId = sgbd.doQueryInsertGeneratedKey(query2, param2);
					if (newSiteId > 0) {
						final Object[][] param3 = { { SETTER_TYPE.INT, newJobId }, { SETTER_TYPE.INT, newSiteId } };
						if (1 != sgbd.doQueryInsert(query3, param3)) {
							throw new FFCrawlSqlException("Insert lnk site<->job error : " + newJobId + " / " + newSiteId);
						}
					}else{
						throw new FFCrawlSqlException("Insert site error : " + url);
						// error
					}
				}
				sgbd.getConnection().commit();
			}
		} catch (SQLException e) {
			newJobId = -1;
			try {
				sgbd.getConnection().rollback();
			} catch (SQLException e1) {
			}
			throw new FFCrawlSqlException(e);
		} finally {
			sgbd.closePool();
		}
		return newJobId;
	}
	
	public static int updateStatus(final long controle, final int jobId, final STATUS status) {
		if (auth != controle)
			throw new FFCrawlSqlException(authMsg);
		final String query = "UPDATE `job` " + " SET status = ? " + " WHERE id = ? ";
		final Object[][] param = { { SETTER_TYPE.INT, status.ordinal() }, { SETTER_TYPE.INT, jobId } };
		return new MySgbd().doQueryUpdate(query, param);
	}
}
