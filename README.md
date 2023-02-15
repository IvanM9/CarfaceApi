# Carface: Reconocimiento facial para acceso de vehículos al campus “La María” de la UTEQ

CarFace es un sistema innovador dado que
combina la parte física de la cámara, para emplear el reconocimiento facial del usuario y
compararlo con los datos almacenados en el sistema, proporcionando así, información en
tiempo real a los guardias encargados del ingreso y salida de los vehículos.
[target](target)

## Despliegue

- Es necesario Java JDK en su versión 17.
- Base de datos PostgreSQL

### Variables de entorno

      - SPRING.DATASOURCE.URL
      - SPRING.DATASOURCE.USERNAME
      - SPRING.DATASOURCE.PASSWORD
      - SPRING.JPA.PROPERTIES.HIBERNATE.DIALECT
      - SPRING.JPA.HIBERNATE.DDL-AUTO
      - JWT.SECRET
      - SPRING.THYMELEAF.PREFIX
      - SPRING.THYMELEAF.SUFFIX
      - spring.servlet.multipart.max-file-size
      - spring.servlet.multipart.max-request-size
      - admin.correo
      - admin.clave
      - aws.access_key_id
      - aws.secret_access_key
      - aws.s3.region
      - aws.s3.bucket
      - aws.s3.bucket_rekognition
      - aws.s3.bucket_2

### Docker

``` bash
docker-compose up -d
```
