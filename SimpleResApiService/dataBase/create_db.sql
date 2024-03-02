SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE tms_demo;

CREATE DATABASE tms_demo;

\connect tms_demo

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

CREATE SCHEMA TMS;
COMMENT ON SCHEMA TMS IS 'Test Management System schema';

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';

DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA pg_catalog;

SET search_path = TMS, pg_catalog;

CREATE FUNCTION lang() RETURNS text
    LANGUAGE plpgsql STABLE
    AS $$
BEGIN
  RETURN current_setting('TMS.lang');
EXCEPTION
  WHEN undefined_object THEN
    RETURN NULL;
END;
$$;

CREATE FUNCTION now() RETURNS timestamp with time zone
    LANGUAGE sql IMMUTABLE
    AS $$SELECT '2024-01-01 19:00:00'::TIMESTAMP AT TIME ZONE 'Europe/Samara';$$;
COMMENT ON FUNCTION now() IS 'Point in time according to which the data are generated';

SET default_tablespace = '';
SET default_with_oids = false;

DROP TABLE IF EXISTS "users";

CREATE TABLE users (
    userId uuid DEFAULT uuid_generate_v4() NOT NULL,
    login varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    registrationDate DATE DEFAULT now() NOT NULL,
    CONSTRAINT users_userId UNIQUE(userId),
    CONSTRAINT users_login_key UNIQUE(login),
    CONSTRAINT users_login_check CHECK(length(login) >= 6 AND length(login) <= 32),
    CONSTRAINT users_password_check CHECK(length(password) >= 6 AND length(password) <= 32),
    PRIMARY KEY (userId)
);

COMMENT ON TABLE users IS 'Registered users in TMS';
COMMENT ON COLUMN users.userId IS 'Unique user UUID and Primary key';
COMMENT ON COLUMN users.login IS 'User login, must be greater or equals then 6';
COMMENT ON COLUMN users.password IS 'User password, must be grater or equals then 6';

DROP TABLE IF EXISTS "projects";

CREATE TABLE projects (
    projectId uuid DEFAULT uuid_generate_v4() NOT NULL,
    projectName varchar(256) NOT NULL,
    summary varchar(256),
    description text,
    createdDate DATE DEFAULT now() NOT NULL,
    createdBy uuid NOT NULL,
    CONSTRAINT tms_project_id UNIQUE(projectId),
    PRIMARY KEY (projectId),
    FOREIGN KEY (createdBy) REFERENCES users(userId)
);

COMMENT ON TABLE projects IS 'Projects';
COMMENT ON COLUMN projects.projectId IS 'Unique project UUID (primary key)';
COMMENT ON COLUMN projects.projectName IS 'Project name must be not null';
COMMENT ON COLUMN projects.summary IS 'Project summary it is a little description';
COMMENT ON COLUMN projects.description IS 'Description';
COMMENT ON COLUMN projects.createdDate IS 'Date created it project';
COMMENT ON COLUMN projects.createdBy IS 'User uuid (foreign key) - created by user';

DROP TABLE IF EXISTS "testPlans";

CREATE TABLE testPlans (
    testPlanId uuid DEFAULT uuid_generate_v4() NOT NULL,
    testPlanName varchar(256) NOT NULL,
    summary varchar(256) NOT NULL,
    description varchar(256) NOT NULL,
    createdDate DATE DEFAULT now() NOT NULL,
    createdBy uuid NOT NULL,
    projectId uuid NOT NULL,
    CONSTRAINT tms_test_plan_id UNIQUE(testPlanId),
    CONSTRAINT tms_test_plan_name UNIQUE(testPlanName),
    PRIMARY KEY (testPlanId),
    FOREIGN KEY (createdBy) REFERENCES users(userId),
    FOREIGN KEY (projectId) REFERENCES projects(projectId)
);

COMMENT ON TABLE testPlans IS 'Test plans table';
COMMENT ON COLUMN testPlans.testPlanId IS 'Unique test plan UUID (primary key)';
COMMENT ON COLUMN testPlans.testPlanName IS 'Unique test plan name';
COMMENT ON COLUMN testPlans.summary IS 'Test plan summary';
COMMENT ON COLUMN testPlans.description IS 'Test plan description';
COMMENT ON COLUMN testPlans.createdDate IS 'Test plan created date';
COMMENT ON COLUMN testPlans.createdBy IS 'User when create this plan (foreign key)';
COMMENT ON COLUMN testPlans.projectId IS 'Project where created this plan (foreign key)';

DROP TABLE IF EXISTS "testCases";

CREATE TABLE testCases(
    caseId uuid DEFAULT uuid_generate_v4() NOT NULL,
    caseName varchar(256) NOT NULL,
    summary varchar(256) NOT NULL,
    createdDate DATE DEFAULT now() NOT NULL,
    createdBy uuid NOT NULL,
    projectId uuid NOT NULL,
    testPlanId uuid NOT NULL,
    CONSTRAINT tms_test_case_id UNIQUE(caseId),
    PRIMARY KEY (caseId),
    FOREIGN KEY (createdBy) REFERENCES users(userId),
    FOREIGN KEY (projectId) REFERENCES projects(projectId),
    FOREIGN KEY (testPlanId) REFERENCES testPlans(testPlanId)
);

COMMENT ON TABLE testCases IS 'Test case table';
COMMENT ON COLUMN testCases.caseId IS 'Unique case ID';
COMMENT ON COLUMN testCases.caseName IS 'Test Case Name';
COMMENT ON COLUMN testCases.summary IS 'Little description';
COMMENT ON COLUMN testCases.createdDate IS 'Created date';
COMMENT ON COLUMN testCases.createdBy IS 'User ID (Foreign key)';
COMMENT ON COLUMN testCases.projectId IS 'Project ID (Foreign key)';
COMMENT ON COLUMN testCases.testPlansId IS 'Test Plan Id (Foreign key)';

DROP TABLE IF EXISTS "testSteps";

CREATE TABLE testSteps(
    stepId uuid DEFAULT uuid_generate_v4() NOT NULL,
    testCaseId uuid NOT NULL,
    step varchar(256),
    testData varchar(256),
    expectedResult varchar(256),
    CONSTRAINT tms_step_id UNIQUE(stepId),
    PRIMARY KEY (stepId),
    FOREIGN KEY (testCaseId) REFERENCES testCases(caseId)
);

COMMENT ON TABLE testSteps IS 'Test Step table';
COMMENT ON COLUMN testSteps.stepId IS 'Step ID Unique (Primary key)';
COMMENT ON COLUMN testSteps.testCaseId IS 'Test Case ID (Foreign key)';
COMMENT ON COLUMN testSteps.step IS 'Step';
COMMENT ON COLUMN testSteps.testData IS 'Test Data';
COMMENT ON COLUMN testSteps.expectedResult IS 'Expected Result';




