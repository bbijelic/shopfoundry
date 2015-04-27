package org.shopfoundry.core.service.gateway.inbound;

import org.shopfoundry.core.service.gateway.Gateway;

/**
 * Inbound gateway.
 * 
 * Inbound gateway is considered to be an inbound only communication interface.
 * Services can run multiple inbound communication interfaces simultaniously.
 * 
 * @author Bojan Bijelic
 */
public interface InboundGateway extends Gateway {}
