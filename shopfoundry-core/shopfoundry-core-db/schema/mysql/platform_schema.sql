-- Drop schema if already exists
DROP SCHEMA IF EXISTS sf_platform;

-- Create schema
CREATE SCHEMA sf_platform;

-- Use created schema
USE sf_platform;

-- ACL Permissions
CREATE TABLE sf_acl_permission (
id					INT NOT NULL AUTO_INCREMENT,
permissionKey		VARCHAR(100) NOT NULL,
name				VARCHAR(100) NOT NULL,
description 		VARCHAR(255),
PRIMARY KEY(id),
CONSTRAINT sf_acl_permission_permissionKey_uq UNIQUE (permissionKey),
CONSTRAINT sf_acl_permission_name_uq UNIQUE (name)
) ENGINE=InnoDB;

-- ACL Roles
CREATE TABLE sf_acl_role (
id					INT NOT NULL AUTO_INCREMENT,
name				VARCHAR(100) NOT NULL,
description 		VARCHAR(255),
PRIMARY KEY(id),
CONSTRAINT sf_acl_role_name_uq UNIQUE (name)
) ENGINE=InnoDB;

-- ACL Roles to Permission mapping
CREATE TABLE sf_acl_role_permissions (
permission		INT NOT NULL,
role			INT NOT NULL,
PRIMARY KEY(permission, role),
CONSTRAINT sf_acl_role_permissions_permission_fk FOREIGN KEY ( permission ) REFERENCES sf_acl_permission (id),
CONSTRAINT sf_acl_role_permissions_role_fk FOREIGN KEY ( role ) REFERENCES sf_acl_role (id)
) ENGINE=InnoDB;

-- ACL User
CREATE TABLE sf_acl_user (
id					INT NOT NULL AUTO_INCREMENT,
email				VARCHAR(255) NOT NULL,
password			VARCHAR(64) NOT NULL,
salt				VARCHAR(36) NOT NULL,
firstName	 		VARCHAR(100) NOT NULL,
lastName	 		VARCHAR(100) NOT NULL,
createdAt			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updatedAt			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id),
CONSTRAINT sf_acl_user_email_uq UNIQUE (email)
) ENGINE=InnoDB;

-- ACL user to roles mapping
CREATE TABLE sf_acl_user_roles (
acluser			INT NOT NULL,
role			INT NOT NULL,
PRIMARY KEY(acluser, role),
CONSTRAINT sf_acl_user_roles_user_fk FOREIGN KEY ( acluser ) REFERENCES sf_acl_user (id),
CONSTRAINT sf_acl_user_roles_role_fk FOREIGN KEY ( role ) REFERENCES sf_acl_role (id)
) ENGINE=InnoDB;
