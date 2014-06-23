package com.ff.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/create")
public class JobsCreate {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String create() {
		return "Hello POST Jersey";
	}
	
	public Response test(){
		return Response.status(Status.ACCEPTED).build();
	}
}
