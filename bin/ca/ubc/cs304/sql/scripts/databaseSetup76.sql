/**
  Might want to drop any existing tables. so for example have a line like this for every table
  drop table Agency cascade constraints;
 */
drop table agency cascade constraints;
drop table disease cascade constraints;
drop table hospital cascade constraints;
drop table doctor cascade constraints;
drop table government cascade constraints;
drop table location_continent cascade constraints;
drop table location_name_pop cascade constraints;
drop table city cascade constraints;
drop table region cascade constraints;
drop table country cascade constraints;
drop table transportation cascade constraints;
drop table operates_research_lab cascade constraints;
drop table researches_biohl cascade constraints;
drop table researches_pname cascade constraints;
drop table worksfor_doctor cascade constraints;
drop table caresfor_patient cascade constraints;
drop table treats cascade constraints;
drop table certifies cascade constraints;
drop table infected_with cascade constraints;
drop table governs cascade constraints;
drop table route cascade constraints;

 /**
   HAVE TO MAKE SURE that tables are created in
   the correct order. We can't have weak entities (research labs),
   created before strong entities (Agency). Also need tables referenced
   by foreign keys to be made before the tables doing the
   referencing.
  */

CREATE TABLE agency (
    agency_Name varchar2(100) PRIMARY KEY,
    agency_num_of_employees integer
);
CREATE TABLE disease (
    disease_Scientific_Name varchar2(100) PRIMARY KEY,
    disease_Type varchar2(100),
    disease_R0 float(3)
);
CREATE TABLE hospital(
    hospital_Address varchar2(100) PRIMARY KEY,
    hospital_Capacity integer,
    hospital_Name varchar2(100),
    hospital_Type varchar2(100)
);
CREATE TABLE doctor(
    doctor_ID integer PRIMARY KEY,
    doctor_Name varchar2(100)
);
CREATE TABLE government(
    government_Gov_ID integer PRIMARY KEY,
    government_Public_Health_Budget integer,
);
CREATE TABLE location_continent(
    location_continent_LAT float(5),
    location_continent_LON float(5),
    location_continent_Continent varchar2(100),
    PRIMARY KEY(location_continent_LAT, location_continent_LON)
);
/*Confused here if Name should also be part of the primary
  key based off of schema and ERD. CHECK WITH TA!!!
 */
CREATE TABLE location_name_pop(
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    location_name_pop_Population integer,
    PRIMARY KEY(location_name_pop_Name,location_name_pop_LAT, location_name_pop_LON)
);
CREATE TABLE city(
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    city_Mayor varchar2(100),
    PRIMARY KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON) REFERENCES location_name_pop
);
CREATE TABLE region(
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    region_Climate varchar2(100),
    PRIMARY KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON) REFERENCES location_name_pop
);
CREATE TABLE country(
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    country_Leader varchar2(100),
    PRIMARY KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON) REFERENCES location_name_pop
);
CREATE TABLE transportation(
    transportation_Transportation_ID varchar2(100) PRIMARY KEY,
    transportation_Type varchar2(100),
    transportation_Passenger_Count integer
);

/**
  Now create tables for all of the relationships, doing this should
  preserve the order needed for foreign key and weak entity constraints

  HOW TO PUT IN ON DELETE CONSTRAINTS??????!!!
 */

 CREATE TABLE operates_research_lab(
     operates_research_lab_RName varchar2(100),
     operates_research_lab_Address varchar2(100),
     agency_Name varchar2(100),
     PRIMARY KEY(operates_research_lab_RName, agency_Name),
     FOREIGN KEY(agency_Name) REFERENCES agency
 );
CREATE TABLE researches_biohl(
    agency_Name varchar2(100),
    researches_biohl_Bio_Hazard_Level integer,
    PRIMARY KEY(agency_Name),
    FOREIGN KEY(agency_Name) REFERENCES agency
);
CREATE TABLE researches_pname(
    disease_Scientific_Name varchar2(100),
    agency_Name varchar2(100),
    researches_pname_Project_Name varchar2(100),
    PRIMARY KEY (disease_Scientific_Name, agency_Name),
    FOREIGN KEY (disease_Scientific_Name) REFERENCES disease,
    FOREIGN KEY (agency_Name) REFERENCES agency
);
CREATE TABLE worksfor_doctor(
    worksfor_doctor_ID integer,
    hospital_address varchar2(100) not null,
    worksfor_doctor_Since date,
    PRIMARY KEY (worksfor_doctor_ID, hospital_address),
    FOREIGN KEY (hospital_address) REFERENCES hospital
);
/**
  for this relationship I changed the disease_name field to disease_Scientific_Name so its a foreign key
  of the disease table.
 */
CREATE TABLE caresfor_patient(
    caresfor_patient_PID integer PRIMARY KEY,
    caresfor_patient_Name varchar2(100),
    caresfor_patient_Address varchar2(100),
    disease_Scientific_Name varchar2(100),
    caresfor_patient_Since date,
    doctor_ID integer not null,
    FOREIGN KEY (doctor_ID) REFERENCES doctor,
    FOREIGN KEY (disease_Scientific_Name) REFERENCES disease
);
CREATE TABLE treats(
    hospital_Address varchar2(100),
    disease_Scientific_Name varchar2(100),
    PRIMARY KEY (hospital_Address, disease_Scientific_Name),
    FOREIGN KEY (hospital_Address) REFERENCES hospital,
    FOREIGN KEY (disease_Scientific_Name) REFERENCES disease
);
CREATE TABLE certifies(
    doctor_ID integer,
    government_Gov_ID integer,
    certifies_Date date,
    PRIMARY KEY (doctor_ID, government_Gov_ID),
    FOREIGN KEY (doctor_ID) REFERENCES doctor,
    FOREIGN KEY (government_Gov_ID) REFERENCES government
);
CREATE TABLE infected_with(
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    disease_Scientific_Name varchar2(100),
    infected_with_Cases integer,
    infected_with_Deaths integer,
    PRIMARY KEY (location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON, disease_Scientific_Name),
    FOREIGN KEY (location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON) REFERENCES location_name_pop,
    FOREIGN KEY (disease_Scientific_Name) REFERENCES disease
);
CREATE TABLE governs(
    government_Gov_ID integer,
    location_name_pop_Name varchar2(100),
    location_name_pop_LAT float(5),
    location_name_pop_LON float(5),
    PRIMARY KEY (government_Gov_ID, location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY (government_Gov_ID) REFERENCES government,
    FOREIGN KEY (location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON) REFERENCES location_name_pop
);
CREATE TABLE route(
    location_name_pop_Name_Start varchar2(100),
    location_name_pop_LAT_Start float(5),
    location_name_pop_LON_Start float(5),
    location_name_pop_Name_End varchar2(100),
    location_name_pop_LAT_End float(5),
    location_name_pop_LON_End float(5),
    transportation_Transportation_ID varchar2(100),
    route_Date date,
    PRIMARY KEY (location_name_pop_Name_Start, location_name_pop_LAT_Start, location_name_pop_LON_Start, location_name_pop_Name_End, location_name_pop_LAT_End, location_name_pop_LON_End, transportation_Transportation_ID),
    FOREIGN KEY (location_name_pop_Name_Start, location_name_pop_LAT_Start, location_name_pop_LON_Start) REFERENCES location_name_pop(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY (location_name_pop_Name_End, location_name_pop_LAT_End, location_name_pop_LON_End) REFERENCES location_name_pop(location_name_pop_Name, location_name_pop_LAT, location_name_pop_LON),
    FOREIGN KEY (transportation_Transportation_ID) REFERENCES transportation
);

INSERT INTO agency VALUES ('World Health Organization', 7000);
INSERT INTO agency VALUES ('Centers for Disease Control and Prevention', 10899);
INSERT INTO agency VALUES ('Department of Health and Human Services', 3000);
INSERT INTO agency VALUES ('Ministry of Health (British Columbia)', 2000);
INSERT INTO agency VALUES ('Vancouver Coastal Health', 500);

INSERT INTO disease VALUES ('Swine Flu', 'Virus', 1.5);
INSERT INTO disease VALUES ('COVID-19', 'Virus', 3.2);
INSERT INTO disease VALUES ('Norovirus', 'Virus', 3.7);
INSERT INTO disease VALUES ('Tuberculosis', 'Bacteria', 10);
INSERT INTO disease VALUES ('Malaria', 'Parasite', NULL);

INSERT INTO hospital VALUES ('5783 Notreal Rd, Nowhereville', 300, 'Memorial Hospital', 'Childrens');
INSERT INTO hospital VALUES ('4559 Mahogany Ave, Philadelphia', 50, 'Smith Hospital', 'All Ages');
INSERT INTO hospital VALUES ('2993 Redrock Rd, Mars', 20, 'Musk Hospital', 'Childrens');
INSERT INTO hospital VALUES ('4889 Oak Rd, Dokey', 150, 'St Marks', 'All Ages');
INSERT INTO hospital VALUES ('2948 Pupper Pl, Bonetown', 200, 'Wags Hospital', 'Childrens');

INSERT INTO doctor VALUES (0, 'Matthew Baker');
INSERT INTO doctor VALUES (1, 'Betty Porter');
INSERT INTO doctor VALUES (2, 'Emily Livingston');
INSERT INTO doctor VALUES (3, 'Robert Brown');
INSERT INTO doctor VALUES (4, 'Andrew Sandusky');

INSERT INTO government VALUES (0, 1100000000);
INSERT INTO government VALUES (1, 2340000000);
INSERT INTO government VALUES (2, 896000000);
INSERT INTO government VALUES (3, 22000000000);
INSERT INTO government VALUES (4, 4839000000);

INSERT INTO location_continent VALUES (8.79, 34.50, 'Africa');
INSERT INTO location_continent VALUES (54.53, 105.26, 'North America');
INSERT INTO location_continent VALUES (8.78, 55.49, 'South America');
INSERT INTO location_continent VALUES (34.05, 100.62, 'Asia');
INSERT INTO location_continent VALUES (54.53, 15.26, 'Europe');

INSERT INTO location_name_pop VALUES ('Hawaii', 19.90, 155.58, 100000);
INSERT INTO location_name_pop VALUES ('Puerto Rico', 18.22, 66.59, 2000000);
INSERT INTO location_name_pop VALUES ('Guam', 13.44, 144.79, 10000);
INSERT INTO location_name_pop VALUES ('American Samoa', 14.27, 170.13, 20000);
INSERT INTO location_name_pop VALUES ('Northern Mariana Islands', 15.09, 145.67, 3000);

INSERT INTO city VALUES ('Vancouver', 49.28, 123.12, 'Kennedy Stewart');
INSERT INTO city VALUES ('Toronto', 43.65, 79.38, 'Rob Ford');
INSERT INTO city VALUES ('Shanghai', 31.23, 121.47, 'Yong Ying');
INSERT INTO city VALUES ('New York', 40.71, 74.00, 'Bill de Blasio');
INSERT INTO city VALUES ('Berlin', 52.52, 13.4, 'Michael Muller');

INSERT INTO region VALUES ('Pacific NorthWest', 40.0, 120.0, 'Temperate Rainforest');
INSERT INTO region VALUES ('Central America', 10, 80, 'Jungle');
INSERT INTO region VALUES ('Andes', 30, 90, 'Alpine');
INSERT INTO region VALUES ('Sahara', 15, 20, 'Desert');
INSERT INTO region VALUES ('Pacific Islands', 180, 0, 'Tropical Rainforest');

INSERT INTO country VALUES ('Canada', 56.13, 106.35, 'Justin Trudeau');
INSERT INTO country VALUES ('China', 35.86, 104.20, 'Xi Jingping');
INSERT INTO country VALUES ('USA', 37.09, 95.71, 'Donald Trump');
INSERT INTO country VALUES ('Germany', 51.17, 10.45, 'Angela Merkel');
INSERT INTO country VALUES ('Mexico', 19.43, 99.13, 'Andres Obrador');

INSERT INTO transportation VALUES (0, 'Air', 180);
INSERT INTO transportation VALUES (1, 'Sea', 5600);
INSERT INTO transportation VALUES (2, 'Sea', 570);
INSERT INTO transportation VALUES (3, 'Sea', 56);
INSERT INTO transportation VALUES (4, 'Train', 2100);

INSERT INTO operates_research_lab VALUES ('Mike Boskowitz Memorial Laboratory', 'World Health Organization','404 Notreal Rd, Nowhereville');
INSERT INTO operates_research_lab VALUES ('National Research Laboratory', 'Centers for Disease Control and Prevention','3467 Mahogany ave, Philadelphia');
INSERT INTO operates_research_lab VALUES ('Musk National Laboratory', 'World Health Organization', '2345 Redrock Rd, Mars');
INSERT INTO operates_research_lab VALUES ('Hugh Mungus Biohazard Lab','Vancouver Coastal Health','8585 Oaky Rd, Dokey');
INSERT INTO operates_research_lab VALUES ('Yellow Lab','Department of Health and Human Services','1010 Pupper Pl, Bonetown');

INSERT INTO researches_biohl VALUES ('World Health Organization', 0);
INSERT INTO researches_biohl VALUES ('Centers for Disease Control and Prevention', 1);
INSERT INTO researches_biohl VALUES ('Department of Health and Human Services', 2);
INSERT INTO researches_biohl VALUES ('Ministry of Health', 3);
INSERT INTO researches_biohl VALUES ('Vancouver Coastal Health', 4);

INSERT INTO researches_pname VALUES ('Swine Flu', 'World Health Organization', 'Swine Flu 1');
INSERT INTO researches_pname VALUES ('COVID-19', 'Centers for Diases Control and Prevention', 'COVID-19 3');
INSERT INTO researches_pname VALUES ('Norovirus', 'Department of Health and Human Services', 'Norovirus 5');
INSERT INTO researches_pname VALUES ('Tuberculosis', 'Ministry of Health (British Columbia)', 'Tuberculosis 1');
INSERT INTO researches_pname VALUES ('Malaria', 'Vancouver Coastal Health', 'Malaria 10');

INSERT INTO worksfor_doctor VALUES (0,'5783 Notreal Rd, Nowhereville', {d '2004-12-02'});
INSERT INTO worksfor_doctor VALUES (1, '4559 Mahogany Ave, Philadelphia', {d '2012-01-03'});
INSERT INTO worksfor_doctor VALUES (2, '2993 Redrock rd, Mars', {d '1987-04-10'});
INSERT INTO worksfor_doctor VALUES (3, '4889 Oaky Rd, Dokey', {d '1999-06-07'});
INSERT INTO worksfor_doctor VALUES (4, '2948 Pupper Pl, Bonetown', {d '2014-05-23'});

INSERT INTO caresfor_patient VALUES (0, 'Marcus Lowe', '967 Notreal Rd, Mowhereville', 'Swine Flu', {d '2020-02-26'}, 1);
INSERT INTO caresfor_patient VALUES (1, 'Tom McCaw', '8959 Mahogany Ave, Philadelphia', 'COVID-19', {d '2020-02-18'}, 1);
INSERT INTO caresfor_patient VALUES (2, 'Anthony Hall', '3879 Redrock Rd, Mars', 'COVID-19', {d '2020-01-29'}, 0);
INSERT INTO caresfor_patient VALUES (3, 'Susan Smith', '4738 Oaky Rd, Dokey', 'Tuberculosis', {d '2020-02-11'}, 2);
INSERT INTO caresfor_patient VALUES (4,' Amy Sneedley', '1938 Pupper Pl, Bonetown', 'Malaria', {d '2020-02-21'}, 3);

INSERT INTO treats VALUES ('5783 Notreal Rd, NoWhereville','COVID-19');
INSERT INTO treats VALUES ('5783 Notreal Rd, Nowhereville','Tuberculosis');
INSERT INTO treats VALUES ('4889 Oaky Rd, Dokey','Norovirus');
INSERT INTO treats VALUES ('2948 Pupper Pl, Bonetown','Malaria');
INSERT INTO treats VALUES ('2948 Pupper Pl, Bonetown','COVID-19');

INSERT INTO certifies VALUES (0,2, {d '2003-11-21'});
INSERT INTO certifies VALUES (1,2, {d '2010-01-18'});
INSERT INTO certifies VALUES (2,2, {d '1982-02-05'});
INSERT INTO certifies VALUES (3,1, {d '1999-06-03'});
INSERT INTO certifies VALUES (4,0, {d '2014-04-27'});

INSERT INTO infected_with VALUES ('Vancouver', 49.28, 123.12, 'COVID-19', 0, 7);
INSERT INTO infected_with VALUES ('USA', 37.09, 95.71, 'Swine Flu', 240000, 45000000);
INSERT INTO infected_with VALUES ('China', 35.86, 106.35, 'Norovirus', 20, 1000);
INSERT INTO infected_with VALUES ('Germany', 51.17, 10.45, 'Swine Flu', 5000, 200000);
INSERT INTO infected_with VALUES ('Mexico', 19.43, 99.13, 'Malaria', 100, 10000);

INSERT INTO governs VALUES (0, 56.13, 106.35, 'Canada');
INSERT INTO governs VALUES (1, 35.86, 104.20, 'China');
INSERT INTO governs VALUES (2, 37.09, 95.71, 'USA');
INSERT INTO governs VALUES (3, 51.17, 10.45, 'Germany');
INSERT INTO governs VALUES (4, 19.43, 99.13, 'Mexico');

INSERT INTO route VALUES (49.28, 123.12, 'Vancouver', 43.65, 79.38, 'Toronto', 0, {d '2020-02-28'});
INSERT INTO route VALUES (31.23, 121.47, 'Shanghai', 49.28, 123.12, 'Vancouver', 1, {d '2020-02-26'});
INSERT INTO route VALUES (43.65, 79.38, 'Toronto', 40.71, 74.00, 'New York', 4, {d '2020-02-28'});
INSERT INTO route VALUES (49.28, 123.12, 'Vancouver', 31.23, 121.47, 'Shanghai', 3, {d '2020-02-27'});
INSERT INTO route VALUES (52.52, 13.4, 'Berlin', 40.71, 74.00, 'New York', 2, {d '2020-02-28'});



