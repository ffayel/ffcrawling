package com.ff.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ff.dao.data.Job;
import com.ff.dao.data.Site;
import com.ff.dao.data.SiteImage;
import com.ff.dao.exception.FFCrawlSqlException;
import com.ff.dao.util.UtilJob;
import com.ff.dao.util.UtilSiteImage;
import com.ff.thread.JobThread;
import com.ff.tool.MyLang;
import com.ff.tool.MyProperties;

@Path("/jobs")
public class JobsService {
	/**
	 * 
	 * @param params les paramettres attendu sont les url des sites à examiner au format suivant : http://www.cnn.comhttp://www.4chan.org/
	 * Seul les urls avec le protocol HTTP ou HTTPS sont pris en charge
	 * @return
	 */
	@POST
	@Path("/create")
	@Produces(MediaType.TEXT_PLAIN)
	//@Consumes("application/x-www-form-urlencoded")
	public Response create(String params) {
		final List<String> urlSites = new ArrayList<String>();
		//recupération des paramettres
		//je n'ai pas trouver de regex pour spliter des url qui me sont retourné come suis : http://www.cnn.comhttp://www.4chan.org/
		//J'ai donc fait rapide (mais moins correct a mon sens)
		// debut de piste pour la regex : (http[^http]+)
		if(params.length() > 0 && params.startsWith("http")){
			String[] p = params.split("http");
			for (String s : p) {
				if(!"".equals(s) && null != s){
					urlSites.add("http"+s);
					System.out.println(s);
				}
			}
		}
		//verification que des urls on bien été transmissent
		if(urlSites.size() <=0){
			return Response.status(Status.BAD_REQUEST).build();//retour erreur si pas d'urls
		}
		//creation du Job
		int jobId = UtilJob.create(urlSites);
		if(jobId > 0){
			//lancement du thread
			new Thread(new JobThread(jobId)).start();
		}
		return Response.ok(MyLang.getInstance().getTranslationString("jobs.create.return", jobId)).build();
	}

	/**
	 * Renvoi le statut du job.
 	 * si le jobId est introuvable en BDD un code ERREUR 400 est retourné à l'utilisateur
	 * @param jobId identifiant du job
	 * @return l'etat d'avancement du job.
	 * <ul>
	 * 	<li>En attente de lancement</li>
	 * 	<li>En cours</li>
	 * 	<li>Terminer</li>
	 * 	<li>En erreur</li>
	 * </ul>
	 */
	@GET
	@Path("/{jobId}/status")
	@Produces(MediaType.TEXT_PLAIN)
	public Response status(@PathParam("jobId") int jobId) {
		if(jobId < 0)// pas de valeur
			return Response.status(Status.BAD_REQUEST).build();
		Job.STATUS status;
		try{
			//recupération du status du job en base et affichage
			status = UtilJob.getStatus(jobId);
		}catch(FFCrawlSqlException e){
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(MyLang.getInstance().getTranslationString("jobs.status."+status.name()+".label", jobId)).build();
	}

	/**
	 * Permet d'afficher le resultat du job
	 * @param jobId identifiant du job
	 * @return deux cas possible en fonction de l'état du job :
	 * <ul>
	 * 	<li>Non terminer</li>
	 * 	<li>Terminer : restitution de la liste des urls des images extraitent des pages web</li>
	 * </ul>
	 */
	@GET
	@Path("/{jobId}/result")
	@Produces(MediaType.TEXT_PLAIN)
	public Response result(@PathParam("jobId") int jobId) {
		Response response;
		if(jobId <= 0){// pas de valeur
			response = Response.status(Status.BAD_REQUEST).build();
		}else{
			Job job = UtilJob.getById(jobId);
			if(job == null){
				response = Response.status(Status.BAD_REQUEST).build();
			}else{
				StringBuilder resultBuild = new StringBuilder();
				final MyLang lang = MyLang.getInstance();
				switch (job.getStatus()) {
				case RUN:
					resultBuild.append(lang.getTranslationString("jobs.result.run.label", job.getId()));
					break;
				default:
					resultBuild.append(lang.getTranslationString("jobs.result.title.label", job.getId()));
					resultBuild.append(lang.getTranslationString("jobs.result.status.label",lang.getTranslationString("text.status."+job.getStatus().name()+".label")));
					for (Site site : job.getSites()) {
						resultBuild.append(lang.getTranslationString("jobs.result.site.label", site.getId(), site.getUrl(), site.countImage()));
						for (SiteImage image : site.getImages()) {
							resultBuild.append(lang.getTranslationString("jobs.result.image.label", image.getId(), image.getUrl()));
						}
					}
					resultBuild.append(lang.getTranslationString("jobs.result.end.label", job.getId()));
					break;
				}
				//formatter le resultat
				//MyLang.getInstance().get
				response = Response.ok(resultBuild.toString()).build();
			}
		}
		return response;
	}

	/**
	 * Permet de recupérer les image qui ont été téléchagées sur les site par les job
	 * @param jobId identifiant du job
	 * @param imgId identifiant de l'image fourni par le webService "result"
	 * @return le fichier image
	 */
	@GET
	@Path("/{jobId}/content/{imgId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response content(@PathParam("jobId") int jobId, @PathParam("imgId") int imgId) {
		final File homeDirImage = new File(MyProperties.getString("path.image"));
		
		if(jobId > 0 && imgId > 0){
			Job job = UtilJob.getById(jobId);
			if(job != null){
				SiteImage siteImage = UtilSiteImage.getImageByIdForJob(imgId, job.getId());
				if(siteImage != null){
					File f = new File(homeDirImage, "image"+siteImage.getId()+"_"+ new File(siteImage.getUrl()).getName());
					if (!f.exists()) {
						throw new WebApplicationException(404);
					}
					String mt = new MimetypesFileTypeMap().getContentType(f);
					return Response.ok(f, mt).build();
		
				}
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
