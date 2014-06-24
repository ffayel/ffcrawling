package com.ff.dao.util;

import com.ff.dao.data.SiteImage;
import com.ff.dao.sql.SqlSiteImage;

public final class UtilSiteImage {
	private final static long auth = 616864632;

	public static SiteImage getImageByUrl(final String url) {
		SiteImage siteImage = null;
		if(url != null && !"".equals(url)){
			siteImage = SqlSiteImage.getByUrl(auth, url);
		}
		return siteImage;
	}

	public static boolean addImageToSite(int siteId, int imageId) {
		if(siteId > 0 && imageId > 0){
			return SqlSiteImage.addToSite(auth, siteId, imageId);
		}
		return false;
	}
	
	public static int create(final String url, final int siteId){
		if(!"".equals(url) && siteId > 0){
			return SqlSiteImage.create(auth, url, siteId);
		}
		return -1;
	}

	public static SiteImage getImageById(final int imgId) {
		SiteImage siteImage = null;
		if(imgId >0){
			siteImage = SqlSiteImage.getById(auth, imgId);
		}
		return siteImage;
	}

	/**
	 * Récupere une image en verifiant quelle appartient bien au job 
	 * @param imgId identifiant de l'image
	 * @param jobId identifiant du job
	 * @return
	 */
	public static SiteImage getImageByIdForJob(final int imgId, final int jobId) {
		SiteImage siteImage = null;
		if(imgId >0){
			siteImage = SqlSiteImage.getByIdForJob(auth, imgId, jobId);
		}
		return siteImage;
	}
}
