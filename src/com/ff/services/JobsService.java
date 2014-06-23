package com.ff.services;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ff.tool.MyLang;

@Path("/jobs")
public class JobsService {
	@POST
	@Path("/create")
	@Produces(MediaType.TEXT_PLAIN)
	public String create() {
		return "Hello POST Jersey";
	}

	@GET
	@Path("/{jobId}/status")
	@Produces(MediaType.TEXT_PLAIN)
	public Response status(@PathParam("jobId") int jobId) {
		//	new Thread(new JobThread()).start();
		return Response.ok(MyLang.getInstance().getTranslationString("job.status.label", jobId)).build();
	}

	/**
	 * 
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
		return Response.ok("job result demander : " + jobId).build();
	}

	@GET
	@Path("/{jobId}/content/{img}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response content(@PathParam("jobId") int jobId, @PathParam("img") String imgUrl) {
		File f = new File(imgUrl);
		if (!f.exists()) {
			throw new WebApplicationException(404);
		}
		String mt = new MimetypesFileTypeMap().getContentType(f);
		return Response.ok(f, mt).build();
		// return Response.ok("job content demander : "+jobId + " / imgURL : "+imgUrl).build();
	}
}
