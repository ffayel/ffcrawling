package com.ff.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ff.dao.data.Job;
import com.ff.dao.data.Site;

public class JobThread implements Runnable {

	private Job _job;

	public JobThread(Job job) {
		this._job = job;
	}

	@Override
	public void run() {
		// TODO change status Job
		// Traitement du job Url par url
		for (Site site : _job.getSites()) {
			try {
				// utilisé l'objet cette classe permet de prendre en compte les redirections
				URL url = new URL(site.getUrl());
				System.out.println("host : " + url.getHost());
				System.out.println("path : " + url.getPath());
				System.out.println("getProtocol : " + url.getProtocol());
				// possibilité d'ajouter la gestion de proxy en utilisant :
				// url.openConnection(Proxy proxy)
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String line = null;
				StringBuilder builder = new StringBuilder();
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				in.close();

				// je pense que la regexp peut etre améliorer mais pour le moment elle couvre une bonne partie des cas possible
				Matcher matcher = Pattern.compile("<img[^>]*src=\"([^\"]*)\"|'([^']*)' [^>]+>").matcher(builder);

				System.out.println("machess : " + matcher.matches());
				System.out.println("nbGroup : " + matcher.groupCount());
				Set<URL> imagesUrl = new HashSet<URL>();
				int cptImg = 0;
				while (matcher.find()) {
					if (matcher.groupCount() == 2) {
						// System.out.println(matcher.group(1));
						String imgUrl = matcher.group(1);
						// on verifie que l'on a bien recupér une donné
						// (probleme dut a la regexp)
						if (imgUrl != null) {
							// constitution de l'url absolue pour les images.
							URL u = new URL(url, imgUrl);
							imagesUrl.add(u);
							cptImg++;
							System.out.println(u);
						}
					}
					// System.out.println(matcher.groupCount());
				}

				for (URL urlImage : imagesUrl) {
					// TODO check BDD et Enregistrement de l'image.
				}
				System.out.println("nb img : " + cptImg + "//" + imagesUrl.size());

			} catch (Exception e) {
				//TODO modifier le status du job a ERROR
			}
		}
		System.out.println("END THREAD");
	}

}
