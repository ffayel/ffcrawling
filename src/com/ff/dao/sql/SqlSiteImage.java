package com.ff.dao.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ff.dao.data.SiteImage;
import com.ff.dao.exception.AuthException;
import com.ff.dao.exception.FFCrawlSqlException;
import com.ff.dao.tool.MySgbd;
import com.ff.dao.tool.MySgbd.SETTER_TYPE;
import com.ff.tool.MyTime;

public final class SqlSiteImage {
	private final static long auth = 616864632;
	private final static String authMsg = "com.ff.dao.sql.SqlSiteImage must be only call by com.ff.dao.util.UtilSiteImage";
	
	public static SiteImage getByUrl(final long control, final String url) {
		if (auth != control)
			throw new AuthException(authMsg);
		SiteImage result = null;
		ResultSet rs = null;
		final MySgbd monTool = new MySgbd();
		try {
			final String query = "SELECT image.id, image.url, image.dateload "
					+ " FROM `image` "
					+ " WHERE image.url = ? ";
			final Object[][] param = { { SETTER_TYPE.STRING, url } };
			rs = monTool.doQueryResultSet(query, param);
			if (rs.next()) {
				result = new SiteImage(rs.getInt(1), rs.getString(2), MyTime.getCalendar(rs.getTimestamp(3)));
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

	public static boolean addToSite(long control, int siteId, int imageId) {
		if (auth != control)
			throw new FFCrawlSqlException(authMsg);
		final String query = "INSERT INTO `lnk_img_site` (`id_img`, `id_site`) VALUES (?, ?)";
		final Object[][] param = { { SETTER_TYPE.INT, siteId }, { SETTER_TYPE.INT, imageId } };
		return new MySgbd().doQueryInsert(query, param) == 1;
	}
	
	public static int create(final long controle, final String url, final int siteId) {
		if (auth != controle)
			throw new AuthException(authMsg);

		int newId = -1;
		final String query1 = "INSERT INTO `image` (`url`) VALUES (?)";
		final String query2 = "INSERT INTO `lnk_img_site` (`id_img`, `id_site`) VALUES (?, ?)";
		final Object[][] param1 = {{ SETTER_TYPE.STRING, url }};
		MySgbd sgbd = new MySgbd();
		try {
			sgbd.getConnection().setAutoCommit(false);

			newId = sgbd.doQueryInsertGeneratedKey(query1, param1);
			if (newId > 0) {
				final Object[][] param2 = { { SETTER_TYPE.INT, newId }, {SETTER_TYPE.INT, siteId}};
				if (1 != sgbd.doQueryInsert(query2, param2)) {
					throw new FFCrawlSqlException("Insert lnk site<->image error : " + newId + " / " + siteId);
				}
				sgbd.getConnection().commit();
			}
		} catch (SQLException e) {
			newId = -1;
			try {
				sgbd.getConnection().rollback();
			} catch (SQLException e1) {}
			throw new FFCrawlSqlException(e);
		} finally {
			sgbd.closePool();
		}
		return newId;
	}

	public static SiteImage getById(long control, int imgId) {
		if (auth != control)
			throw new AuthException(authMsg);
		SiteImage result = null;
		ResultSet rs = null;
		final MySgbd monTool = new MySgbd();
		try {
			final String query = "SELECT image.id, image.url, image.dateload "
					+ " FROM `image` "
					+ " WHERE image.id = ? ";
			final Object[][] param = { { SETTER_TYPE.INT, imgId } };
			rs = monTool.doQueryResultSet(query, param);
			if (rs.next()) {
				result = new SiteImage(rs.getInt(1), rs.getString(2), MyTime.getCalendar(rs.getTimestamp(3)));
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

	public static SiteImage getByIdForJob(final long control, final int imgId, final int jobId) {
		if (auth != control)
			throw new AuthException(authMsg);
		SiteImage result = null;
		ResultSet rs = null;
		final MySgbd monTool = new MySgbd();
		try {
			final String query = "SELECT image.id, image.url, image.dateload "
					+ " FROM `image` "
					+ " INNER JOIN lnk_img_site ON image.id = lnk_img_site.id_img "
					+ " INNER JOIN lnk_site_job ON lnk_img_site.id_site = lnk_site_job.id_site "
					+ " WHERE image.id = ? AND lnk_site_job.id_job = ? ";
			final Object[][] param = { { SETTER_TYPE.INT, imgId },{ SETTER_TYPE.INT, jobId } };
			rs = monTool.doQueryResultSet(query, param);
			if (rs.next()) {
				result = new SiteImage(rs.getInt(1), rs.getString(2), MyTime.getCalendar(rs.getTimestamp(3)));
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
	

}
