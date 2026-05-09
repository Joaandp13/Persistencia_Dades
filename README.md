# \# Pillam Ltd. Co. — Aplicació d'Escalada

# 

# Aplicació de gestió de vies d'escalada desenvolupada en \*\*Java + MySQL/PostgreSQL\*\* amb connectivitat JDBC i pool de connexions HikariCP.  

# Pràctica de Persistència de Dades — curs 2025-2026.

# 

# \---

# 

# \## Requisits previs

# 

# \- \*\*Java 21\*\*

# \- \*\*MySQL 8.x\*\* o \*\*PostgreSQL 15+\*\*

# \- Llibreries necessàries (afegir al classpath del projecte):

# &#x20; - `mysql-connector-j-9.x.x.jar` (si uses MySQL)

# &#x20; - `postgresql-42.x.x.jar` (si uses PostgreSQL)

# &#x20; - `HikariCP-5.x.x.jar` (pool de connexions)

# &#x20; - `slf4j-api` + `slf4j-simple` (dependència de HikariCP)

# 

# \### Afegir llibreries a IntelliJ

# `File → Project Structure → Libraries → + → Java → selecciona els .jar → OK`

# 

# \---

# 

# \## Configuració de la base de dades

# 

# Edita el fitxer `src/db.properties`:

# 

# ```properties

# \# Canvia aquest valor per escollir la BD: mysql o postgres

# db.type=mysql

# 

# \# MySQL

# mysql.url=jdbc:mysql://localhost:3306/Pillam

# mysql.user=root

# mysql.password=la\_teva\_contrasenya ( en el nostre cas pirineus)(

# 

# \# PostgreSQL

# postgres.url=jdbc:postgresql://localhost:5432/Pillam

# postgres.user=postgres

# postgres.password=la\_teva\_contrasenya

# ```

# 

# \### Crear la base de dades

# Executa el fitxer `pillam\_db.sql` a MySQL Workbench o per terminal:

# ```bash

# mysql -u root -p < pillam\_db.sql

# ```

# 

# \---

# 

# \## Estructura del projecte

# 

# ```

# src/

# ├── Main.java

# ├── db.properties                  <- credencials i tipus de BD

# ├── Model/

# │   ├── Objectes/                  <- Classes de domini (POJOs)

# │   │   ├── Escalador.java

# │   │   ├── Escola.java

# │   │   ├── Sector.java

# │   │   ├── ViaEsportiva.java

# │   │   ├── ViaClassica.java

# │   │   ├── ViaGel.java

# │   │   └── Tram.java

# │   └── DAO/

# │       ├── DBConnection.java      <- Pool HikariCP (MySQL/PostgreSQL)

# │       └── Clases/

# │           ├── ActualitzarEstatsDAO.java  <- s'executa a l'inici

# │           ├── EscaladorDAO.java

# │           ├── EscolaDAO.java

# │           ├── SectorDAO.java

# │           ├── TramDAO.java

# │           └── Via/

# │               ├── ViaDAO.java            <- Classe abstracta generica

# │               ├── ViaEsportivaDAO.java

# │               ├── ViaClassicaDAO.java

# │               └── ViaGelDAO.java

# ├── Controller/

# │   ├── Escalador/    -> crear, modificar, eliminar, llistar

# │   ├── Escola/       -> crear, modificar, eliminar, llistar

# │   ├── Sector/       -> crear, modificar, eliminar, llistar

# │   └── Vies/

# │       ├── TipusVies/    -> crearEsportiva, crearClassica, crearGel

# │       ├── modificarVies.java

# │       ├── eliminarVies.java

# │       └── llistarVies.java

# └── Vista/

# &#x20;   ├── Utils.java             <- esperarEnter() i utilitats

# &#x20;   ├── menuPrincipal.java

# &#x20;   ├── menuCrear.java

# &#x20;   ├── menuModificar.java

# &#x20;   ├── menuEliminar.java

# &#x20;   ├── menuLlistar.java

# &#x20;   └── menuVies.java

# ```

# 

# \---

# 

# \## Taules de la base de dades

# 

# | Taula | Descripcio |

# |---|---|

# | `escalador` | Escaladors registrats al sistema |

# | `escola` | Zones d'escalada (ex: Montserrat, Siurana) |

# | `sector` | Subsectors dins d'una escola |

# | `via\_esportiva` | Vies esportives (5-30m, 1 sol tram) |

# | `via\_classica` | Vies classiques (multiples trams, 15-30m cadascun) |

# | `via\_gel` | Vies de gel (multiples trams, material especific) |

# | `trams` | Trams de totes les vies (taula unificada amb 3 FKs nullables) |

# 

# \---

# 

# \## Decisions de disseny

# 

# \### 1. Pool de connexions — HikariCP

# En lloc d'un singleton amb una sola connexio, s'utilitza \*\*HikariCP\*\* amb un pool de fins a 10 connexions. La configuracio es llegeix des de `db.properties`, sense credencials al codi font. Aixo permet canviar de BD o d'entorn sense tocar el codi.

# 

# \### 2. Suport MySQL i PostgreSQL

# `DBConnection` detecta el valor `db.type` del fitxer de propietats i configura el driver i la URL corresponents. Per canviar de BD n'hi ha prou amb editar una linia de `db.properties`.

# 

# \### 3. Actualitzacio automatica d'estats a l'inici

# `ActualitzarEstatsDAO.comprovarIActualitzar()` s'executa cada cop que s'inicia l'aplicacio. Comprova totes les vies amb estat `construccio` o `tancada` i les passa a `apte` automaticament si la `data\_fi\_no\_apte` ja ha passat. Mostra per pantalla les vies actualitzades.

# 

# \### 4. Taules de vies separades

# Tres taules separades (`via\_esportiva`, `via\_classica`, `via\_gel`) en lloc d'una taula unica amb camps nullables. Permet ENUMs d'ancoratge especifics per cada tipus (ex: gel no pot tenir spits/parabolts/quimics) i validacions a nivell de BDD mes precises.

# 

# \### 5. Herencia als DAOs de vies — ViaDAO<T>

# Els tres DAOs de vies hereten d'una classe abstracta generica `ViaDAO<T>` que conte tots els metodes comuns (CRUD + consultes especifiques). Cada fill implementa nomes el `mapRow`, els SQL d'insercio/modificacio i els parametres. Evita repetir \~150 linies de codi per cada DAO.

# 

# \### 6. Taula `trams` unificada amb tres FKs nullables

# La taula `trams` te tres columnes FK nullables: `id\_via\_esportiva`, `id\_via\_classica` i `id\_via\_gel`. Exactament una estara informada per registre. El model `Tram.java` valida aixo amb `validarUnicTipusVia()` als setters, i la BDD ho garanteix amb un `CHECK`.

# 

# \### 7. Validacio de llargada de trams per tipus

# El `setLlargada` del `Tram` aplica rangs diferents segons el tipus de via:

# \- Via esportiva (`idViaEsportiva != null`): \*\*5-30m\*\*

# \- Via classica / gel: \*\*15-30m\*\*

# 

# \### 8. `llargada\_total` calculada per Java

# \- \*\*Via esportiva:\*\* `llargada\_total` coincideix amb el seu unic tram.

# \- \*\*Via classica/gel:\*\* Java recalcula la suma cridant `actualitzarLlargadaTotal()` cada cop que s'afegeix o elimina un tram. La BDD emmagatzema el valor calculat.

# 

# \### 9. `num\_vies` no s'emmagatzema

# El nombre de vies d'una escola o sector es calcula dinamicament amb `COUNT(\*)` des de Java (`EscolaDAO.comptarVies()`), mai s'emmagatzema a la BDD.

# 

# \### 10. `id\_creador` nullable amb ON DELETE SET NULL

# Les tres taules de vies tenen `id\_creador INT DEFAULT NULL` amb `ON DELETE SET NULL`. Eliminar un escalador no trenca les seves vies, simplement queden amb `id\_creador = NULL`.

# 

# \### 11. Validacio en dos nivells

# \- \*\*Setters del Model:\*\* validen cada camp individualment (format, rang, valors permesos).

# \- \*\*Controller:\*\* valida regles creuades (ex: sector compatible amb el tipus de via, creador existent a la BDD).

# 

# \### 12. Constructor sense id per a insercions

# Tots els models tenen dos constructors: \*\*amb id\*\* (per quan el DAO llegeix de la BDD) i \*\*sense id\*\* (per quan el Controller crea un objecte nou). L'id el genera MySQL/PostgreSQL amb `AUTO\_INCREMENT` i el DAO el recupera amb `getGeneratedKeys()`.

# 

# \### 13. Eliminacio en cadena

# Per eliminar una escola o sector, Java elimina les entitats filles de dins cap a fora: \*\*trams -> vies -> sectors -> escola\*\*. Es mostra un resum del que s'eliminara i es demana doble confirmacio.

# 

# \### 14. Utils.esperarEnter()

# Totes les consultes del menu 3 criden `Utils.esperarEnter()` al final perque l'usuari pugui llegir els resultats sense que el menu els tapi immediatament.

# 

# \---

# 

# \## Menus de l'aplicacio

# 

# ```

# MENU PRINCIPAL

# &#x20;1. CREAR

# &#x20;   1. Escalador

# &#x20;   2. Escola

# &#x20;   3. Sector

# &#x20;   4. Vies

# &#x20;      1. Via Esportiva  (crea via + 1 tram automaticament)

# &#x20;      2. Via Classica   (crea via + demana trams un per un)

# &#x20;      3. Via de Gel     (igual que classica)

# 

# &#x20;2. MODIFICAR

# &#x20;   1. Escalador

# &#x20;   2. Escola

# &#x20;   3. Sector

# &#x20;   4. Vies (esportiva / classica / gel)

# 

# &#x20;3. LLISTAR / CONSULTAR

# &#x20;   1-8.  CRUD basic (un/tots per cada entitat)

# &#x20;   9.    Vies disponibles (apte) d'una escola

# &#x20;   10.   Cercar vies per rang de dificultat

# &#x20;   11.   Cercar vies per estat

# &#x20;   12.   Escoles amb restriccions actives

# &#x20;   13.   Sectors amb mes de X vies disponibles

# &#x20;   14.   Escaladors amb el mateix nivell maxim

# &#x20;   15.   Vies que han passat a APTE recentment

# &#x20;   16.   Vies mes llargues d'una escola

# 

# &#x20;4. ELIMINAR

# &#x20;   1. Escalador  (doble confirmacio)

# &#x20;   2. Escola     (eliminacio en cadena + resum previ)

# &#x20;   3. Sector     (eliminacio en cadena + resum previ)

# &#x20;   4. Vies       (elimina trams primer, despres la via)

# ```

# 

# \---

# 

# \## Autors

# 

# Joan Díaz, Ferran Orobitg — Persistencia de Dades 2025-2026

