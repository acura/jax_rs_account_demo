package com.org.patch;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.InvalidJsonPatchException;
import com.flipkart.zjsonpatch.JsonPatch;
import com.flipkart.zjsonpatch.JsonPatchApplicationException;
import com.org.model.AccountWS;
import com.org.rest.AccountException;
import com.org.rest.AccountRestService;
import com.org.service.AccountService;

@Provider
@PATCH
public class AccountPatchInterceptor implements ReaderInterceptor {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(AccountPatchInterceptor.class);
	
	private final UriInfo uriInfo;
	
    @Autowired
    private AccountService accountService;

    /**
     * {@code PatchingInterceptor} injection constructor.
     *
     * @param uriInfo {@code javax.ws.rs.core.UriInfo} proxy instance.
     */
    public AccountPatchInterceptor(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
    	// fetch Account id from request attribute
    	MultivaluedMap<String, String> params = uriInfo.getPathParameters();
    	if(!params.containsKey(AccountRestService.ACCOUNT_PARAM_KEY_ID)) {
    		throw new WebApplicationException("Invalid Account id !");
    	}
    	
    	AccountWS account = null ;
    	try {
    		account = accountService.getAccount(params.getFirst(AccountRestService.ACCOUNT_PARAM_KEY_ID));
    	} catch (AccountException e) {
    		logger.error("Account Not Found !", e);
    		throw new WebApplicationException(e);
		}
    	
    	// fetching patch from request 
    	String patch = new BufferedReader(new InputStreamReader(readerInterceptorContext.getInputStream()))
    	  .lines().collect(Collectors.joining("\n"));
    	JsonNode jsonPatch = MAPPER.readTree(patch);
    	// validating patch 
    	try {
    		JsonPatch.validate(jsonPatch);
    	} catch(InvalidJsonPatchException invalidJsonPatchException) {
    		logger.error("Invalid Patch ", invalidJsonPatchException);
    		throw new WebApplicationException(invalidJsonPatchException);
    	}
    	logger.debug("Retried Patch From request "+ patch);
    	try {
    		JsonNode accountNode = MAPPER.readTree(MAPPER.writeValueAsString(account));
    		JsonNode updatedNode = JsonPatch.apply(jsonPatch, accountNode);
    		logger.debug("Account Node " + accountNode.toString());
    		logger.debug("Updated Patch Node "+ updatedNode.toString());
    		// Stream the result & modify the stream on the readerInterceptor
            ByteArrayOutputStream resultAsByteArray = new ByteArrayOutputStream();
            MAPPER.writeValue(resultAsByteArray, updatedNode);
            readerInterceptorContext.setInputStream(new ByteArrayInputStream(resultAsByteArray.toByteArray()));
            return readerInterceptorContext.proceed();
    	} catch(JsonPatchApplicationException exception) {
    		logger.error("Error Applying Patch ", exception);
    		throw new WebApplicationException(exception);
    	}
    }
}