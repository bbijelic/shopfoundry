-- Drop and create schema
DROP SCHEMA IF EXISTS sf_registry;
CREATE SCHEMA sf_registry;

-- Workaround for DROP USER IF exists
-- This will create user if already does not exist with USAGE privelages
GRANT USAGE ON sf_registry.* TO 'sf_registry_root'@'%';
FLUSH PRIVILEGES;
-- Drop user
DROP USER sf_registry_root;

-- Create user
CREATE USER 'sf_registry_root'@'%' IDENTIFIED BY 'sf_registry_root';
-- Grant all privileges
GRANT ALL PRIVILEGES ON sf_registry. * TO 'sf_registry_root'@'%';

-- Flush privileges
FLUSH PRIVILEGES;

-- Use created database
USE sf_registry;

-- Service Group table
CREATE TABLE service_group (
id					int not null auto_increment,
name				varchar(255) not null COMMENT 'Service group unique identifier',
description			varchar(255) COMMENT 'Service group name',
version				varchar(20) not null COMMENT 'Service group version',
suspended			bool default false COMMENT 'Is service group suspended. If true, confguration can not be obtained',

PRIMARY KEY (id),
CONSTRAINT service_group_version_uq UNIQUE (name, version)
) ENGINE = InnoDB;

-- Service Group configuation table
CREATE TABLE service_group_configuration (
id					int not null auto_increment COMMENT 'Service group configuration unique identifier',
service_group		int not null COMMENT 'Service group to which this configuration entry relates',
active_from			timestamp not null default current_timestamp COMMENT 'Configuration activation time',
configuration_data	text COMMENT 'Configuration data',

PRIMARY KEY (id, service_group),
CONSTRAINT service_group_configuration__service_group_fk FOREIGN KEY (service_group) REFERENCES service_group (id)
) ENGINE = InnoDB;

-- Service Group configuation key pairs
CREATE TABLE service_group_configuration_key_pairs (
id					int not null auto_increment COMMENT 'Configuration pair unique id',
configuration		int not null,
config_key			varchar(255) not null,
config_value		varchar(255) not null,
config_type			enum('string', 'integer', 'decimal', 'object'),
public				bool default false COMMENT 'Is configuration pair public or private',

PRIMARY KEY (id, configuration),
CONSTRAINT service_group_configuration_keypair_uq UNIQUE (configuration, config_key),
CONSTRAINT service_group_configuration_keypair_fk FOREIGN KEY (configuration) REFERENCES service_group_configuration (id)
) ENGINE = InnoDB;
