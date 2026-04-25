# Hexagonal Demo

API REST construida con Spring Boot, Gradle y PostgreSQL.

Este proyecto esta pensado para que cada integrante configure su propia base de datos local sin tener que editar el codigo fuente.

# Autores 

- Geovanny Paez - 1093294660
- Camilo Jaimes - 
- Edinson Palacio - 1030040009
- Miller Hernández - 1094045081
  
## Antes de empezar

Necesitan tener instalado:

- Java 17
- PostgreSQL
- Git

## Idea general

La aplicacion toma la configuracion desde variables como:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `SERVER_PORT`

Pero para que sea mas facil para el equipo, tambien puede leer esas variables desde un archivo local llamado `.env.properties`.

Ese archivo:

- Se crea en la raiz del proyecto
- Guarda la configuracion personal de cada compañero
- No se sube a Git

## Paso a paso para correr el proyecto

### 1. Clonar el repositorio

```bash
git clone <url-del-repo>
cd demo
```

### 2. Crear la base de datos en PostgreSQL

Cada persona puede usar el nombre de base de datos que quiera. No tiene que ser una sola fija para todos.

Ejemplo:

```sql
CREATE DATABASE hexagonal;
```

No hace falta crear tablas manualmente. Spring Boot las crea o actualiza al iniciar porque Hibernate usa `update`.

### 3. Crear el archivo de configuracion local

En la raiz del proyecto van a encontrar un archivo de ejemplo llamado `.env.properties.example`.

Lo que deben hacer es:

1. Copiar ese archivo
2. Renombrarlo a `.env.properties`
3. Poner ahi los datos de su propia base de datos

El archivo debe quedar asi:

```properties
SERVER_PORT=8080
DB_URL=jdbc:postgresql://localhost:5432/hexagonal
DB_USERNAME=postgres
DB_PASSWORD=tu_password
DDL_AUTO=update
SHOW_SQL=true
```

Importante:

- `DB_URL` debe apuntar a la base de datos que cada uno creo en su computador
- `DB_USERNAME` y `DB_PASSWORD` deben ser los datos reales de su PostgreSQL
- Lo que aparece en el ejemplo no es una configuracion obligatoria, solo una guia

### 4. Explicacion corta de cada variable

- `SERVER_PORT`: puerto donde arranca la API
- `DB_URL`: direccion de conexion a PostgreSQL
- `DB_USERNAME`: usuario de PostgreSQL
- `DB_PASSWORD`: clave de PostgreSQL
- `DDL_AUTO`: comportamiento de Hibernate con las tablas
- `SHOW_SQL`: muestra consultas SQL en consola

Ejemplo de `DB_URL`:

```text
jdbc:postgresql://localhost:5432/nombre_de_tu_base
```

### 5. Ejecutar el proyecto

En Windows:

```powershell
.\gradlew.bat bootRun
```

En Linux o macOS:

```bash
./gradlew bootRun
```

Si todo sale bien, la API queda disponible en:

```text
http://localhost:8080
```

## Si no quieren usar archivo

Tambien pueden configurar las variables manualmente en la terminal.

Ejemplo en PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/hexagonal"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="tu_password"
$env:SERVER_PORT="8080"
.\gradlew.bat bootRun
```

Pero para el equipo se recomienda usar `.env.properties`, porque es mas simple y evita cambiar el codigo.

## Endpoints principales

### Categorias

- `POST /categorias`
- `GET /categorias`
- `GET /categorias/{id}`
- `PUT /categorias/{id}`
- `DELETE /categorias/{id}`

Ejemplo:

```json
{
  "nombre": "Bebidas",
  "descripcion": "Productos liquidos"
}
```

### Clientes

- `POST /clientes`
- `GET /clientes`
- `GET /clientes/{id}`
- `PUT /clientes/{id}`
- `DELETE /clientes/{id}`

Ejemplo:

```json
{
  "id": "123456789",
  "nombre": "Juan Perez"
}
```

### Productos

- `POST /productos`
- `GET /productos`
- `GET /productos/{id}`
- `PUT /productos/{id}`
- `DELETE /productos/{id}`
- `GET /productos/categoria/{categoriaId}`

Ejemplo:

```json
{
  "nombre": "Cafe",
  "descripcion": "Cafe molido 500g",
  "precio": 12500,
  "stock": 20,
  "categoriaId": 1
}
```

### Pedidos

- `POST /pedidos`
- `GET /pedidos`
- `GET /pedidos/{id}`
- `PUT /pedidos/{id}`
- `DELETE /pedidos/{id}`
- `GET /pedidos/cliente/{clienteId}`

Ejemplo:

```json
{
  "fechaPedido": "2026-04-20",
  "estado": "PENDIENTE",
  "clienteId": "123456789",
  "total": 25000
}
```

### Detalles de pedido

- `POST /detalles-pedido`
- `GET /detalles-pedido`
- `GET /detalles-pedido/{id}`
- `PUT /detalles-pedido/{id}`
- `DELETE /detalles-pedido/{id}`
- `GET /detalles-pedido/pedido/{pedidoId}`

Ejemplo:

```json
{
  "pedidoId": 1,
  "productoId": 1,
  "cantidad": 2,
  "precioUnitario": 12500,
  "subtotal": 25000
}
```

## Ejecutar pruebas

En Windows:

```powershell
.\gradlew.bat test
```

En Linux o macOS:

```bash
./gradlew test
```

## Problemas comunes

- Si PostgreSQL no esta corriendo, la app no inicia
- Si el usuario o la clave estan mal, Spring Boot falla al conectarse
- Si la base de datos no existe, tambien falla el arranque
- Si cambian de configuracion, deben actualizar su `.env.properties`
