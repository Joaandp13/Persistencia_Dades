-- ============================================================
--  BASE DE DADES: Pillam Ltd. Co.
--  Aplicació d'Escalada - JDBC / MySQL 8
-- ============================================================
--
--  RESPONSABILITATS DE JAVA (no aplicables a SQL):
--    1. Validar grau segons el tipus de via:
--         esportiva  -> 4 fins a 9c+
--         classica   -> 4 fins a 9c+  (grau per llarg: fins a 8b)
--         gel        -> 4 fins a 8b   (grau per llarg: fins a 8b)
--    2. Comprovar data_fi_no_apte en cada operació i actualitzar
--       estat a 'apte' si current_date >= data_fi_no_apte.
--    3. En inserir una via, verificar que sector.tipus_vies
--       sigui compatible ('gel' o 'classica_esportiva').
--    4. Calcular num_vies d'Escola/Sector mitjançant COUNT(*).
--    5. Si el creador d'una via no existeix a la BDD, donar-lo d'alta.
--    6. Mantenir llargada_total actualitzada:
--         esportiva  -> igual que la llargada de l'únic llarg (num_llarg=1)
--         classica   -> suma de llarg.llargada on tipus_via='classica'
--         gel        -> suma de llarg.llargada on tipus_via='gel'
--    7. En inserir un llarg, verificar que:
--         - si tipus_via='esportiva' no existeix ja un llarg per a aquella via
--         - el id_via correspon a una via del tipus indicat
--         - llargada esportiva: 5-30m | classica/gel: 15-30m
-- ============================================================

CREATE DATABASE IF NOT EXISTS Pillam
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Pillam;

-- ============================================================
--  ESCALADOR
-- ============================================================
CREATE TABLE escalador (
    id_escalador    INT          PRIMARY KEY AUTO_INCREMENT,
    nom             VARCHAR(100) NOT NULL,
    alias           VARCHAR(50),
    edat            INT          CHECK (edat >= 10),
    -- Grau màxim assolit: validació de format i rang a Java
    nivell          VARCHAR(10),
    -- Nom de la via on ha assolit el nivell màxim
    via_max_nivell  VARCHAR(150),
    estil_preferit  ENUM('esportiva','classica','gel'),
    -- Camp PENDENT segons l'enunciat; reservat per a ús futur
    historial       TEXT
);


-- ============================================================
--  ESCOLA
-- ============================================================
--  · nom UNIQUE a tota la BDD (constraint de l'enunciat)
--  · num_vies NO s'emmagatzema; Java el calcula amb COUNT(*)
-- ============================================================
CREATE TABLE escola (
    id_escola   INT          PRIMARY KEY AUTO_INCREMENT,
    nom         VARCHAR(150) UNIQUE NOT NULL,
    poblacio    VARCHAR(100),
    aproximacio TEXT,
    popularitat ENUM('baixa','mitjana','alta')
);


-- ============================================================
--  SECTOR
-- ============================================================
--  · UNIQUE(id_escola, nom): dos sectors d'una mateixa escola
--    no poden tenir el mateix nom.
--  · tipus_vies: Java garanteix que les vies inserides respectin
--    aquesta restricció (gel != classica/esportiva).
-- ============================================================
CREATE TABLE sector (
    id_sector    INT          PRIMARY KEY AUTO_INCREMENT,
    nom          VARCHAR(100) NOT NULL,
    id_escola    INT          NOT NULL,
    latitud      DECIMAL(10, 7),
    longitud     DECIMAL(10, 7),
    aproximacio  TEXT,
    popularitat  ENUM('baixa','mitjana','alta'),
    restriccions TEXT,
    -- 'gel'                -> nomes vies de gel
    -- 'classica_esportiva' -> vies classiques i/o esportives
    tipus_vies   ENUM('gel','classica_esportiva') NOT NULL,

    FOREIGN KEY (id_escola) REFERENCES escola(id_escola) ON DELETE CASCADE,
    UNIQUE (id_escola, nom)
);


-- ============================================================
--  VIA ESPORTIVA
-- ============================================================
--  · llargada_total: 5-30 m — també serà la llargada del seu
--    únic llarg (num_llarg = 1). Java manté la coherència.
--  · grau: fins a 9c+ (validació de format a Java)
--  · ancoratge: ENUM amb els 3 valors permesos per esportiva
-- ============================================================
CREATE TABLE via_esportiva (
    id_via              INT          PRIMARY KEY AUTO_INCREMENT,
    nom                 VARCHAR(150) NOT NULL,
    id_sector           INT          NOT NULL,
    llargada_total      INT          NOT NULL CHECK (llargada_total BETWEEN 5 AND 30),
    grau                VARCHAR(10)  NOT NULL,
    orientacio          ENUM('N','NE','NO','SE','SO','E','O','S'),
    estat               ENUM('apte','construccio','tancada') DEFAULT 'apte',
    data_inici_no_apte  DATE,
    data_fi_no_apte     DATE,
    ancoratge           ENUM('spits','parabolts','quimics') NOT NULL,
    tipus_roca          ENUM('conglomerat','granit','calcaria','arenisca','altres'),
    id_creador          INT          NOT NULL,
    restriccions        TEXT,

    FOREIGN KEY (id_sector)  REFERENCES sector(id_sector)          ON DELETE CASCADE,
    FOREIGN KEY (id_creador) REFERENCES escalador(id_escalador),
    UNIQUE (id_sector, nom)
);


-- ============================================================
--  VIA CLASSICA
-- ============================================================
--  · llargada_total: Java la calcula sumant els llargs i
--    l'actualitza en cada INSERT/UPDATE/DELETE de llarg.
--  · grau: grau global de la via (Java el deriva del llarg
--    més difícil o s'introdueix manualment).
--  · ancoratge: material tradicional + fixe
-- ============================================================
CREATE TABLE via_classica (
    id_via              INT          PRIMARY KEY AUTO_INCREMENT,
    nom                 VARCHAR(150) NOT NULL,
    id_sector           INT          NOT NULL,
    llargada_total      INT,         -- Java actualitza en afegir/treure llargs
    grau                VARCHAR(10)  NOT NULL,
    orientacio          ENUM('N','NE','NO','SE','SO','E','O','S'),
    estat               ENUM('apte','construccio','tancada') DEFAULT 'apte',
    data_inici_no_apte  DATE,
    data_fi_no_apte     DATE,
    ancoratge           ENUM('friends','tascons','bagues','pitons',
                             'tricams','bigbros',
                             'spits','parabolts','quimics') NOT NULL,
    tipus_roca          ENUM('conglomerat','granit','calcaria','arenisca','altres'),
    id_creador          INT          NOT NULL,
    restriccions        TEXT,

    FOREIGN KEY (id_sector)  REFERENCES sector(id_sector)          ON DELETE CASCADE,
    FOREIGN KEY (id_creador) REFERENCES escalador(id_escalador),
    UNIQUE (id_sector, nom)
);


-- ============================================================
--  VIA GEL
-- ============================================================
--  · llargada_total: igual que classica, Java la manté.
--  · grau maxim permes: 8b (validació a Java)
--  · ancoratge: NOMES material tradicional (sense fixe)
-- ============================================================
CREATE TABLE via_gel (
    id_via              INT          PRIMARY KEY AUTO_INCREMENT,
    nom                 VARCHAR(150) NOT NULL,
    id_sector           INT          NOT NULL,
    llargada_total      INT,         -- Java actualitza en afegir/treure llargs
    grau                VARCHAR(10)  NOT NULL,
    orientacio          ENUM('N','NE','NO','SE','SO','E','O','S'),
    estat               ENUM('apte','construccio','tancada') DEFAULT 'apte',
    data_inici_no_apte  DATE,
    data_fi_no_apte     DATE,
    -- Gel NO inclou spits/parabolts/quimics (ancoratge fixe no usat en gel)
    ancoratge           ENUM('friends','tascons','bagues','pitons',
                             'tricams','bigbros') NOT NULL,
    tipus_roca          ENUM('conglomerat','granit','calcaria','arenisca','altres'),
    id_creador          INT          NOT NULL,
    restriccions        TEXT,

    FOREIGN KEY (id_sector)  REFERENCES sector(id_sector)          ON DELETE CASCADE,
    FOREIGN KEY (id_creador) REFERENCES escalador(id_escalador),
    UNIQUE (id_sector, nom)
);


-- ============================================================
--  TRAMS (taula unificada per als 3 tipus de via)
-- ============================================================
--  Per tenir FKs reals, cada tipus de via té la seva pròpia
--  columna nullable. El CHECK garanteix que exactament una
--  de les tres estigui informada.
--
--  Regles de negoci que Java ha de fer complir:
--    · id_via_esportiva NOT NULL
--        -> exactament 1 tram per via (num_tram sempre = 1)
--        -> llargada: 5-30 m
--    · id_via_classica / id_via_gel NOT NULL
--        -> trams múltiples (num_tram: 1, 2, 3...)
--        -> llargada per tram: 15-30 m
--    · En qualsevol INSERT/UPDATE/DELETE de tram, Java
--      recalcula i actualitza llargada_total a la via.
-- ============================================================
CREATE TABLE trams (
    id_tram           INT         PRIMARY KEY AUTO_INCREMENT,
    -- Exactament una de les tres columnes ha d'estar informada
    id_via_esportiva  INT         DEFAULT NULL,
    id_via_classica   INT         DEFAULT NULL,
    id_via_gel        INT         DEFAULT NULL,
    -- Numero de tram: T1=1, T2=2... Esportiva sempre 1.
    num_tram          INT         NOT NULL CHECK (num_tram >= 1),
    -- Rang global 5-30; Java refina: classica/gel mínim 15 m
    llargada          INT         NOT NULL CHECK (llargada BETWEEN 5 AND 30),
    -- Grau del tram; rang maxim segons tipus validat a Java
    grau              VARCHAR(10) NOT NULL,

    FOREIGN KEY (id_via_esportiva) REFERENCES via_esportiva(id_via) ON DELETE CASCADE,
    FOREIGN KEY (id_via_classica)  REFERENCES via_classica(id_via)  ON DELETE CASCADE,
    FOREIGN KEY (id_via_gel)       REFERENCES via_gel(id_via)       ON DELETE CASCADE,

    -- Exactament una via ha d'estar informada
    CHECK (
        (id_via_esportiva IS NOT NULL AND id_via_classica IS NULL     AND id_via_gel IS NULL)
        OR
        (id_via_esportiva IS NULL     AND id_via_classica IS NOT NULL AND id_via_gel IS NULL)
        OR
        (id_via_esportiva IS NULL     AND id_via_classica IS NULL     AND id_via_gel IS NOT NULL)
    ),

    -- Evita duplicar el mateix tram dins d'una via esportiva
    UNIQUE (id_via_esportiva, num_tram),
    -- Evita duplicar el mateix tram dins d'una via classica
    UNIQUE (id_via_classica,  num_tram),
    -- Evita duplicar el mateix tram dins d'una via gel
    UNIQUE (id_via_gel,       num_tram)
);


-- ============================================================
--  INDEXOS
-- ============================================================

-- Cercar vies per estat
CREATE INDEX idx_ve_estat   ON via_esportiva (estat);
CREATE INDEX idx_vc_estat   ON via_classica  (estat);
CREATE INDEX idx_vg_estat   ON via_gel       (estat);

-- Cercar vies per dificultat en rang
CREATE INDEX idx_ve_grau    ON via_esportiva (grau);
CREATE INDEX idx_vc_grau    ON via_classica  (grau);
CREATE INDEX idx_vg_grau    ON via_gel       (grau);

-- Vies que han passat a 'apte' recentment
CREATE INDEX idx_ve_datafin ON via_esportiva (data_fi_no_apte);
CREATE INDEX idx_vc_datafin ON via_classica  (data_fi_no_apte);
CREATE INDEX idx_vg_datafin ON via_gel       (data_fi_no_apte);

-- Escaladors amb el mateix nivell maxim
CREATE INDEX idx_esc_nivell ON escalador     (nivell);

-- Trams per via (consultes frequents des de Java)
CREATE INDEX idx_trams_esportiva ON trams (id_via_esportiva);
CREATE INDEX idx_trams_classica  ON trams (id_via_classica);
CREATE INDEX idx_trams_gel       ON trams (id_via_gel);