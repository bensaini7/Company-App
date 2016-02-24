# Company-App

This application provides APIs to access and modify a Company record and its related attributes. Besides Spring's MVC for managing API requests and Hibernate to manage data access functionalities. It uses AOP for logging which reduces the lines of code and delivers cleaner more readable code and logs. 

It also has a HTML and AngularJS based front-end client which allows one to see a list of companies and edit and update any specific company. Application has been deployed on using Heroku's free account and the **front-end client can be accessed** via this **[link](https://sleepy-chamber-33264.herokuapp.com/companyapp/)**.

For security, one can consider Spring Security implementation in Java or OAuth-token based dedicated independent application. Spring Security is highly effective if one were to expose functionality only through a web client. OAuth-taken based independent security app which could also leverage Redis for role-based token management is more effective in decoupling security from functionality and thus a better fit when APIs are exposed as services.


#### Following are the items explained below in this document.

- [x] **Angular JS Front-end client**
- [x] **APIs in Postman and cURLS**
- [x] **Database Scripts PostgreSQL**
- [x] **Technology stack**





> Angular JS front-end client
>-

  URL : (https://sleepy-chamber-33264.herokuapp.com/companyapp/)
  
  ![Image of front-end client](https://github.com/bensaini7/Company-App/blob/master/html_client.png)
  
> APIs in Postman and cURLs
>-

Test via postman by accessing saved test cases also shwn in the image below : [![Run in Postman](https://run.pstmn.io/button.png)](https://www.getpostman.com/run-collection/09b718e8ef829d3c2d82)

![image of postman](https://github.com/bensaini7/Company-App/blob/master/postman_screenshot.png)
    

#### cURLs
 **Create a new company:**
```javascript
  curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 7b104f60-eb7f-ec6d-30cd-9585ff656d56" -d 
  '{
    "name" : "Company AA",
    "email": "abc@def.com",
    "phone":"613-987-7654",
    "address":{
        "address":"8 Terrace Circle",
        "city":"Great Neck",
        "country":"USA"
    }
}' "https://sleepy-chamber-33264.herokuapp.com/companyapp/createCompany/" 
```

**Get a list of all companies:**
```javascript
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 6098ed18-5c7e-01c3-1ba0-fcdcc7bd1ac2" "https://sleepy-chamber-33264.herokuapp.com/companyapp/getCompanyList/?pageSize=100&pageNum=1"
```

**Get details about a company:**
```javascript
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: c789cebb-786c-5f47-3eda-20b085b8e877" -d 
'{
    "companyId": 56,
    "name": "Company A"
  }' "https://sleepy-chamber-33264.herokuapp.com/companyapp/getCompanyDetails/"
```

**Update a company/ Add Beneficial Owners:**
```javascript
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 256a5b3a-ee09-a892-d36b-c523ef9870be" -d 
'{
    "name" : "Company A",
    "address":{
        "address":"999 Vicious Circle",
        "city":"Seattle",
        "country":"USA"
    },
    "beneficialOwners":[
         {
      "ownerId": 96,
      "firstName": "96",
      "lastName": "96"
        }
    ]
}' "https://sleepy-chamber-33264.herokuapp.com/companyapp/updateCompany/"
```
> Database Scripts [PostgreSQL]
>-

```SQL
--Sequences to auto populate IDs incase of Manual Inserts
CREATE SEQUENCE  "COMPANY_INFO_SEQ"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 56 CACHE 20  NO CYCLE ;
CREATE SEQUENCE  "ADDRESS_SEQ"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 56 CACHE 20  NO CYCLE ;
CREATE SEQUENCE  "B_OWNERS_SEQ"  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 56 CACHE 20  NO CYCLE ;

-- Create Address table
CREATE TABLE address 
( add_id integer DEFAULT nextval('"ADDRESS_SEQ"'), 
  address VARCHAR(50) NOT NULL, 
  city VARCHAR(50),
  country VARCHAR(50),
  CONSTRAINT address_pk PRIMARY KEY (add_id)
  );
  
--Create Company Info Table  @Many-to-One relation between Address & Company info
CREATE TABLE company_info
( company_id integer DEFAULT nextval('"COMPANY_INFO_SEQ"'), 
  name VARCHAR(50) NOT NULL UNIQUE, 
  add_id integer NOT NULL,
  email VARCHAR(50),
  phone VARCHAR(50),
  CONSTRAINT COMPANY_INFO_PK PRIMARY KEY (company_id),
  FOREIGN KEY (add_id) REFERENCES address (add_id) 
);

--Create Benficial Owners Table
CREATE TABLE beneficial_owner 
( owner_id integer DEFAULT nextval('"B_OWNERS_SEQ"'), 
  first_name VARCHAR(50) NOT NULL, 
  last_name VARCHAR(50),
  CONSTRAINT owner_pk PRIMARY KEY (owner_id)
);

--Create Mapping table @Many-to-Many Company info & Owner
CREATE TABLE company_info_owner 
(	company_id integer, 
	owner_id integer,
	PRIMARY KEY (company_id, owner_id),
	FOREIGN KEY (company_id) REFERENCES COMPANY_INFO (company_id),
	FOREIGN KEY (owner_id) REFERENCES beneficial_owner (owner_id) 
);
COMMIT;
```
> Technology Stack
>-

 |Techno Stack
 ---|---------
 1 | Spring MVC & AOP
 2 | Hibernate
 3 | Angular JS
 4 | PostgreSQL
