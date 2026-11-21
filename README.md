# Base de datos MySQL con Docker Compose

El servicio `mysql` utiliza MySQL 8.4, crea la base de datos `gasolinera` y conserva los datos en el volumen Docker `mysql_data`.

## Puesta en marcha

1. Copiar `.env.example` como `.env` y cambiar las dos contraseñas.
2. Ejecutar:

```powershell
docker compose up -d
```

3. Comprobar el estado:

```powershell
docker compose ps
docker compose logs -f mysql
```

Los scripts `.sql` de `sql/` se ejecutan automáticamente, en orden alfabético, solo al inicializar el volumen por primera vez. Para conectarse desde el equipo anfitrión se puede usar `localhost`, el puerto indicado por `MYSQL_PORT`, el usuario `MYSQL_USER` y la base `MYSQL_DATABASE`.

Para detener el servicio conservando los datos:

```powershell
docker compose down
```

No ejecutar `docker compose down -v` salvo que se quiera eliminar también el volumen y todos los datos de MySQL.
