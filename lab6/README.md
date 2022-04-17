

## SonarQube image:

```
 docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

## Euromilhoes credentials:

name: euromilhoes
token: a719c5e329287238407b510e2a3c6eea9e255977


## Verify:

```
mvn verify sonar:sonar -Dsonar.host.url=http://localhost:$SONAR_PORT -Dsonar.projectKey=$PROJECT_KEY -Dsonar.login=$PROJECT_TOKEN
```
