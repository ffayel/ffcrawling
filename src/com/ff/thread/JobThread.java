package com.ff.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ff.dao.data.Job;
import com.ff.dao.data.Job.STATUS;
import com.ff.dao.data.Site;
import com.ff.dao.data.SiteImage;
import com.ff.dao.util.UtilJob;
import com.ff.dao.util.UtilSiteImage;
import com.ff.tool.MyProperties;

public class JobThread implements Runnable {

	private static final Pattern patternImg = Pattern.compile("<img[^>]*src=\"([^\"]*)\"|'([^']*)' [^>]+>");
	private static final File homeDirImage = new File(MyProperties.getString("path.image"));
	private Job _job;
	private boolean saveBDD = true;

	public JobThread(int jobId) {
		this._job = UtilJob.getById(jobId);
	}
	
	public JobThread(Job job) {
		this._job = job;
	}

	@Override
	public void run() {
		// change status Job
		if(this.saveBDD)
			UtilJob.updateStatus(_job.getId(), STATUS.RUN);
		// Traitement du job Url par url
		for (Site site : _job.getSites()) {
			try {
				// utiliser l'objet cette classe permet de prendre en compte les redirections
				URL url = new URL(site.getUrl());
				// possibilité d'ajouter la gestion de proxy en utilisant :
				// url.openConnection(Proxy proxy)
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String line = null;
				StringBuilder builder = new StringBuilder();
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				in.close();

				// je pense que la regexp peut etre amélioré mais pour le moment elle couvre une bonne partie des cas possible
				Matcher matcher = patternImg.matcher(builder);

				Set<URL> imagesUrl = new HashSet<URL>();
				int cptImg = 0;
				while (matcher.find()) {
					if (matcher.groupCount() == 2) {
						// System.out.println(matcher.group(1));
						String strImgUrl = matcher.group(1);
						// on verifie que l'on a bien recuperé une donnée (probleme dut à la regex)
						if (strImgUrl != null) {
							// constitution de l'url absolue pour les images.
							URL u = new URL(url, strImgUrl);
							imagesUrl.add(u);
							cptImg++;
						}
					}
					// System.out.println(matcher.groupCount());
				}

				for (URL urlImage : imagesUrl) {
					//site.getId()
					if(this.saveBDD){
						//verification de la présence dans la base
						final SiteImage sImg = UtilSiteImage.getImageByUrl(urlImage.toString());
						if(sImg != null){
							//ajout de la reference au site actuel
							UtilSiteImage.addImageToSite(site.getId(), sImg.getId());
						}else{
							//ajout en BDD et et au site (transaction)
							final int siteImageId = UtilSiteImage.create(urlImage.toString(), site.getId());
							if(siteImageId > 0){
								//telechargement dans les fichier système.
								//je rajoute le non de l'image a la fin du fichier pour permettre de determiné le 
								//"MineType" coté web service
								//TODO après quelque test j'ai remarqué que sur certain site le nomage des images pose problème pour les sauvgarder
								final File destinationFile = new File(homeDirImage, "image"+siteImageId+"_"+ new File(urlImage.getFile()).getName());
								if (!destinationFile.getParentFile().exists()) { destinationFile.getParentFile().mkdirs(); }
								try{
									InputStream is = urlImage.openStream();
									OutputStream os = new FileOutputStream(destinationFile);
									byte[] b = new byte[2048];
									int length;
	
									while ((length = is.read(b)) != -1) {
										os.write(b, 0, length);
									}
									is.close();
									os.close();
								}catch(Exception e){
									throw new Exception("impossible de sauvgarder l'image");
								}
							}
						}
					}else{
						System.out.println(urlImage);
					}
					// TODO check BDD et Enregistrement de l'image.
				}
				if(this.saveBDD)
					UtilJob.updateStatus(_job.getId(), STATUS.END);
				System.out.println("nb img : " + cptImg + "//" + imagesUrl.size());

			} catch (Exception e) {
				//TODO modifier le status du job a ERROR
				if(this.saveBDD)
					UtilJob.updateStatus(_job.getId(), STATUS.ERROR);
				System.err.println("ERROR THREAD"+e.getMessage());
			}
		}
		System.out.println("END THREAD");
	}
	
	public boolean isSaveBDD() {
		return saveBDD;
	}

	public void setSaveBDD(boolean saveBDD) {
		this.saveBDD = saveBDD;
	}

}
