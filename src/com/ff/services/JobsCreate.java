package com.ff.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/create")
public class JobsCreate {
	@GET @Path("ID")
	@Produces(MediaType.TEXT_PLAIN)
	public String create() {
		return "Hello POST Jersey";
	}
}