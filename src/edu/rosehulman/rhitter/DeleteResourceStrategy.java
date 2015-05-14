package edu.rosehulman.rhitter;

import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;
import strategy.ResourceStrategyBase;

public class DeleteResourceStrategy extends ResourceStrategyBase {
	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {
		// TODO Auto-generated method stub
		return super.prepareEvaluation(request, fromRoute);
	}

}