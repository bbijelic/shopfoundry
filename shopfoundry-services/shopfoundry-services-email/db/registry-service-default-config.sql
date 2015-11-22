-- Create Email Service entry
INSERT INTO service_group (name, description, version) VALUES ("EmailService", "This service sends email by using SMTP protocol", "0.0.1");

-- Create Email Service configuration entry
-- Enabled immediately
INSERT INTO service_group_configuration (service_group) VALUES (LAST_INSERT_ID());
SET @last_insert_id = LAST_INSERT_ID();

-- Configuration parameters

-- ------------------------
-- CONNECTION RELATED
-- ------------------------

-- Broker hostmame
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.hostname", "localhost", true);

-- Broker port
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.port", "5672", true);

-- Broker virtual host
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.virtualhost", "shopfoundry", true);

-- Broker username
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.username", "service.email", false);

-- Broker password
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.password", "service.email", false);

-- Connection SSL enabled flag
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.ssl.enabled", "false", false);

-- Connection SSL version
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "broker.ssl.version", "TLSv1.2", false);

-- ------------------------
-- BUS RELATED
-- ------------------------

-- Service bus
-- Service bus exchange
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.service.exchange.name", "bus.service", true);

-- Service bus queue name
-- Queue on which all email requests will come
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.service.queue.name", "service.email.queue", true);

-- Service bus routing
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.service.routing", "service.email", true);

---
---

-- Event bus
-- Event bus exchange
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.event.exchange.name", "bus.event", true);

-- Service event routing for all services
-- Each service has one queue which is binded to the event bus by using this routing key
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.event.routing.all", "", true);

-- Service event routing for service group
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.event.routing.service.group", "service.email", true);

-- Service event routing for service group instance
INSERT INTO service_group_configuration_key_pairs 
	(configuration, config_key, config_value, public) 
VALUES (@last_insert_id, "bus.event.routing.service.instance", "service.email.{uuid}", true);

