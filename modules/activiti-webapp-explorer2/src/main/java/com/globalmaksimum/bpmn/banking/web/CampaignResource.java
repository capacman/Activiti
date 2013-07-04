package com.globalmaksimum.bpmn.banking.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalmaksimum.bpmn.banking.domain.OFOTalimat;
import com.globalmaksimum.bpmn.banking.service.CampaignService;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.json.JSONWithPadding;

@Path("/sim/campaign")
@Produces({ MediaType.APPLICATION_JSON, "application/javascript",
		"application/x-javascript", "text/ecmascript",
		"application/ecmascript", "text/jscript" })
public class CampaignResource {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CampaignResource.class);

	private CampaignService campaignService;

	@POST
	@Path("/start/{campaign}/{customerID}")
	public JSONWithPadding startCampaign(
			@PathParam("campaign") String campaign,
			@PathParam("customerID") Long customerID) {
		LOGGER.info("campaign {} customer {} started", campaign, customerID);
		ProcessInstance startCampaign = campaignService.startCampaign(campaign,
				customerID);
		return new JSONWithPadding(!campaignService.isfinished(startCampaign.getId()));
	}

	public CampaignService getCampaignService() {
		return campaignService;
	}

	public void setCampaignService(CampaignService campaignService) {
		this.campaignService = campaignService;
	}

	@POST
	@Path("/useCredit/{customerid}")
	public JSONWithPadding useCredit(@PathParam("customerid") Long customerid) {
		return new JSONWithPadding(campaignService.triggerGoSignal(customerid,
				"waitCreditUse"));
	}

	@POST
	@Path("/closekmh/{customerid}/{enforce}/{kmh}")
	public JSONWithPadding closeKMH(@PathParam("customerid") Long customerID,
			@PathParam("enforce") Boolean enforce, @PathParam("kmh") String kmh) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("kmh", kmh);
		vars.put("forceCloseKMH", enforce);
		return new JSONWithPadding(campaignService.triggerSignal(customerID,
				"closeKMH", vars));
	}

	@POST
	@Path("/ofo/{customerid}/{ofo}")
	public JSONWithPadding giveOFOTalimat(
			@PathParam("customerid") Long customerID,
			@PathParam("ofo") Integer ofoid) {
		return new JSONWithPadding(campaignService.triggerSignal(customerID,
				"extensionMessage", "OFO", new OFOTalimat(ofoid)));
	}
}
