````md
# Pillam Ltd. Co. — Aplicació de Gestió d'Escalada

Aplicació de gestió de vies d'escalada desenvolupada amb Java, JDBC, MySQL/PostgreSQL i HikariCP.

Pràctica de Persistència de Dades — Curs 2025-2026

---

## Característiques principals

- Gestió completa d'escaladors, escoles, sectors, vies i trams
- Compatibilitat amb MySQL i PostgreSQL
- Arquitectura DAO amb herència genèrica
- Pool de connexions amb HikariCP
- Validacions a nivell Java i Base de Dades
- CRUD complet i consultes avançades

---

# Requisits previs

## Software necessari

- Java 21
- MySQL 8.x o PostgreSQL 15+

---

## Llibreries necessàries

### MySQL

```txt
mysql-connector-j-9.x.x.jar
```

### PostgreSQL

```txt
postgresql-42.x.x.jar
```

### Dependències generals

```txt
HikariCP-5.x.x.jar
slf4j-api
slf4j-simple
```

---

## Afegir llibreries a IntelliJ

```txt
File → Project Structure → Libraries → + → Java → selecciona els .jar → OK
```

---

# Configuració de la Base de Dades

Edita el fitxer:

```txt
src/db.properties
```

## Exemple de configuració

```properties
# Escollir tipus de BD
db.type=mysql

# =========================
# MYSQL
# =========================

mysql.url=jdbc:mysql://localhost:3306/Pillam
mysql.user=root
mysql.password=pirineus

# =========================
# POSTGRESQL
# =========================

postgres.url=jdbc:postgresql://localhost:5432/Pillam
postgres.user=postgres
postgres.password=contrasenya
```

---

# Crear la Base de Dades

Executa el script SQL:

```bash
mysql -u root -p < pillam_db.sql
```

També es pot executar des de:

- MySQL Workbench
- DBeaver
- pgAdmin
- IntelliJ Database Tools

---

# Estructura del projecte

```txt
src/
├── Main.java
├── db.properties

├── Model/
│   ├── Objectes/
│   │   ├── Escalador.java
│   │   ├── Escola.java
│   │   ├── Sector.java
│   │   ├── ViaEsportiva.java
│   │   ├── ViaClassica.java
│   │   ├── ViaGel.java
│   │   └── Tram.java
│   │
│   └── DAO/
│       ├── DBConnection.java
│       │
│       └── Clases/
│           ├── ActualitzarEstatsDAO.java
│           ├── EscaladorDAO.java
│           ├── EscolaDAO.java
│           ├── SectorDAO.java
│           ├── TramDAO.java
│           │
│           └── Via/
│               ├── ViaDAO.java
│               ├── ViaEsportivaDAO.java
│               ├── ViaClassicaDAO.java
│               └── ViaGelDAO.java

├── Controller/
│   ├── Escalador/
│   ├── Escola/
│   ├── Sector/
│   └── Vies/
│       ├── TipusVies/
│       ├── modificarVies.java
│       ├── eliminarVies.java
│       └── llistarVies.java

└── Vista/
    ├── Utils.java
    ├── menuPrincipal.java
    ├── menuCrear.java
    ├── menuModificar.java
    ├── menuEliminar.java
    ├── menuLlistar.java
    └── menuVies.java
```

---

# Taules de la Base de Dades

| Taula | Descripció |
|---|---|
| `escalador` | Escaladors registrats |
| `escola` | Zones d'escalada |
| `sector` | Subsectors d'una escola |
| `via_esportiva` | Vies esportives |
| `via_classica` | Vies clàssiques |
| `via_gel` | Vies de gel |
| `trams` | Trams de totes les vies |

---

# Decisions de disseny

## 1. Pool de connexions — HikariCP

S'utilitza HikariCP amb un pool de fins a 10 connexions. La configuració es llegeix des de `db.properties`, evitant credencials hardcodejades.

---

## 2. Compatibilitat MySQL i PostgreSQL

`DBConnection` detecta automàticament el valor `db.type` i configura el driver corresponent.

---

## 3. Actualització automàtica d'estats

`ActualitzarEstatsDAO.comprovarIActualitzar()` s'executa a l'inici de l'aplicació per actualitzar vies amb estat `construccio` o `tancada`.

---

## 4. Taules de vies separades

Cada tipus de via disposa de la seva pròpia taula per permetre validacions i ENUMs específics.

---

## 5. Herència als DAOs

Els DAOs de vies hereten de `ViaDAO<T>` per reutilitzar funcionalitats comunes.

---

## 6. Taula `trams` unificada

La taula `trams` utilitza tres FKs nullables:

- `id_via_esportiva`
- `id_via_classica`
- `id_via_gel`

Només una pot tenir valor simultàniament.

---

## 7. Validació de llargada

- Via esportiva: 5-30m
- Via clàssica i gel: 15-30m

---

## 8. `llargada_total` calculada per Java

- Via esportiva: coincideix amb el tram únic
- Via clàssica i gel: suma automàtica dels trams

---

## 9. `num_vies` no s'emmagatzema

El nombre de vies es calcula dinàmicament amb `COUNT(*)`.

---

## 10. `id_creador` nullable

S'utilitza `ON DELETE SET NULL` per evitar inconsistències en eliminar escaladors.

---

## 11. Validació en dos nivells

### Model

Validació individual de camps.

### Controller

Validació de regles de negoci i relacions.

---

## 12. Constructors amb i sense ID

Els models disposen de:

- Constructor amb ID
- Constructor sense ID

Els IDs són generats automàticament per la BDD.

---

## 13. Eliminació en cadena

Ordre d'eliminació:

```txt
trams -> vies -> sectors -> escola
```

Amb doble confirmació abans d'executar-se.

---

## 14. Utils.esperarEnter()

Permet mantenir visibles els resultats abans de tornar al menú.

---

# Menús de l'aplicació

```txt
MENU PRINCIPAL

1. CREAR
   1. Escalador
   2. Escola
   3. Sector
   4. Vies
      1. Via Esportiva
      2. Via Classica
      3. Via de Gel

2. MODIFICAR
   1. Escalador
   2. Escola
   3. Sector
   4. Vies

3. LLISTAR / CONSULTAR
   1-8. CRUD bàsic
   9. Vies disponibles d'una escola
   10. Cercar vies per dificultat
   11. Cercar vies per estat
   12. Escoles amb restriccions
   13. Sectors amb més de X vies
   14. Escaladors amb mateix nivell
   15. Vies passades a APTE recentment
   16. Vies més llargues d'una escola

4. ELIMINAR
   1. Escalador
   2. Escola
   3. Sector
   4. Vies
```

---

# Autors

- Joan Díaz
- Ferran Orobitg

Persistència de Dades — 2025-2026
````
