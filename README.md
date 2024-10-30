<h1 align="center" style="font-weight: bold;">Event Breeze 🌦️</h1>

<p align="center">
<a href="#tech">Technologies</a> |
<a href="#started">Getting Started</a> |
<a href="#author">Authorship</a>
</p>

<p align="center">
  Event Breeze is a final project for the T-Academy course. Made with Spring Boot, it is an application 
  where users can register or purchase events, with support for real-time weather forecasts, integrations 
  with Google Cloud API's and LLM's.
</p>

<h3 align="center" style="font-style: italic;">Successful events start with accurate forecasts</h3>

<h2 id="technologies">💻 Technologies</h2>

- Java 22
- Spring Framework
- PostgreSQL

<h2 id="started">🚀 Getting started</h2>

<h3>Cloning</h3>

Run the following command to clone the repository:

```bash
git clone https://github.com/BarucBM/TCC_T-Academy_BackEnd.git
```

<h3>Configuration</h2>

The `application.properties` configuration file in the `src/main/resources` folder must be configured with the
information from your database credentials before you run the application.

## 🔐 Authentication and Permissions

The system uses a role-based access control mechanism to protect the API endpoints. Below is a detailed explanation of
the permissions configured for each route:

### Open Endpoints (no prior authentication needed)
- **POST `/auth/login`**: All users can access this endpoint to log in and get an access token.
- **POST `/auth/google-login`**: All users can access this endpoint to log in and get an access token, but a Google account is required.
- **POST `/auth/refresh-token`**: All users can access this endpoint to generate a new access token.
- **POST `/auth/register/customer`**: Allows users to register with a customer role.
- **POST `/auth/register/company`**: Allows users to register with a company role.

### General Rules

- **Any Other Requests**: For any endpoint not explicitly listed above, the user must be authenticated. This means a
  valid authentication token is required to access any other resources.

<h2 id="author">✍🏻 Authorship</h2>

- Baruc Moreira (https://github.com/BarucBM)
- Isaque Barisch (https://github.com/isaquebarisch)
- Júlia Montibeler (https://github.com/julia-montibeler)
- Letícia Borchardt (https://github.com/leticiaborchardt)