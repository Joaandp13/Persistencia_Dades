USE Pillam;

-- =========================================
-- LIMPIEZA (opcional si ya ejecutaste antes)
-- =========================================
DROP PROCEDURE IF EXISTS insertar_escaladores;
DROP PROCEDURE IF EXISTS insertar_escolas;
DROP PROCEDURE IF EXISTS insertar_sectors;
DROP PROCEDURE IF EXISTS insertar_vies_esportives;
DROP PROCEDURE IF EXISTS insertar_vies_classiques;
DROP PROCEDURE IF EXISTS insertar_vies_gel;
DROP PROCEDURE IF EXISTS insertar_llargs_classica;
DROP PROCEDURE IF EXISTS insertar_llargs_gel;

-- =========================================
-- ESCALADORES
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_escaladores()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
        INSERT INTO escalador (nom, alias, edat, nivell, via_max_nivell, estil_preferit)
        VALUES (
            CONCAT('Escalador_', i),
            CONCAT('Alias_', i),
            FLOOR(18 + RAND()*40),
            ELT(FLOOR(1 + RAND()*10),
                '5','6a','6b','6c','7a','7b','7c','8a','8b','9a'
            ),
            CONCAT('Via_', i),
            ELT(FLOOR(1 + RAND()*3),
                'esportiva','classica','gel'
            )
        );
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_escaladores();

-- =========================================
-- ESCUELAS
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_escolas()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
        INSERT INTO escola (nom, poblacio, aproximacio, popularitat)
        VALUES (
            CONCAT('Escola_', i),
            CONCAT('Poblacio_', i),
            'Acces facil',
            ELT(FLOOR(1 + RAND()*3),
                'baixa','mitjana','alta'
            )
        );
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_escolas();

-- =========================================
-- SECTORES
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_sectors()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
        INSERT INTO sector (
            nom, id_escola, latitud, longitud,
            aproximacio, popularitat, restriccions, tipus_vies
        )
        VALUES (
            CONCAT('Sector_', i),
            FLOOR(1 + RAND()*100),
            41 + RAND(),
            2 + RAND(),
            'Cami curt',
            ELT(FLOOR(1 + RAND()*3),
                'baixa','mitjana','alta'
            ),
            'Cap',
            IF(RAND() > 0.3, 'classica_esportiva', 'gel')
        );
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_sectors();

-- =========================================
-- VIAS ESPORTIVAS
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_vies_esportives()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE sector_id INT;

    WHILE i <= 100 DO

        SELECT id_sector INTO sector_id
        FROM sector
        WHERE tipus_vies = 'classica_esportiva'
        ORDER BY RAND()
        LIMIT 1;

        INSERT INTO via_esportiva (
            nom, id_sector, llargada_total, grau,
            orientacio, estat, ancoratge,
            tipus_roca, id_creador
        )
        VALUES (
            CONCAT('Via_E_', i),
            sector_id,
            FLOOR(5 + RAND()*25),
            ELT(FLOOR(1 + RAND()*10),
                '5','6a','6b','6c','7a','7b','7c','8a','8b','9a'
            ),
            ELT(FLOOR(1 + RAND()*8),
                'N','NE','NO','SE','SO','E','O','S'
            ),
            'apte',
            ELT(FLOOR(1 + RAND()*3),
                'spits','parabolts','quimics'
            ),
            ELT(FLOOR(1 + RAND()*5),
                'conglomerat','granit','calcaria','arenisca','altres'
            ),
            FLOOR(1 + RAND()*100)
        );

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_vies_esportives();

-- =========================================
-- VIAS CLASICAS
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_vies_classiques()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE sector_id INT;

    WHILE i <= 100 DO

        SELECT id_sector INTO sector_id
        FROM sector
        WHERE tipus_vies = 'classica_esportiva'
        ORDER BY RAND()
        LIMIT 1;

        INSERT INTO via_classica (
            nom, id_sector, grau, orientacio,
            estat, ancoratge, tipus_roca, id_creador
        )
        VALUES (
            CONCAT('Via_C_', i),
            sector_id,
            ELT(FLOOR(1 + RAND()*9),
                '5','6a','6b','6c','7a','7b','7c','8a','8b'
            ),
            ELT(FLOOR(1 + RAND()*8),
                'N','NE','NO','SE','SO','E','O','S'
            ),
            'apte',
            ELT(FLOOR(1 + RAND()*9),
                'friends','tascons','bagues','pitons',
                'tricams','bigbros','spits','parabolts','quimics'
            ),
            ELT(FLOOR(1 + RAND()*5),
                'conglomerat','granit','calcaria','arenisca','altres'
            ),
            FLOOR(1 + RAND()*100)
        );

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_vies_classiques();

-- =========================================
-- VIAS GEL
-- =========================================
DELIMITER $$

CREATE PROCEDURE insertar_vies_gel()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE sector_id INT;

    WHILE i <= 100 DO

        SELECT id_sector INTO sector_id
        FROM sector
        WHERE tipus_vies = 'gel'
        ORDER BY RAND()
        LIMIT 1;

        INSERT INTO via_gel (
            nom, id_sector, grau, orientacio,
            estat, ancoratge, tipus_roca, id_creador
        )
        VALUES (
            CONCAT('Via_G_', i),
            sector_id,
            ELT(FLOOR(1 + RAND()*8),
                '4','5','6a','6b','6c','7a','7b','8a'
            ),
            ELT(FLOOR(1 + RAND()*8),
                'N','NE','NO','SE','SO','E','O','S'
            ),
            'apte',
            ELT(FLOOR(1 + RAND()*6),
                'friends','tascons','bagues','pitons','tricams','bigbros'
            ),
            ELT(FLOOR(1 + RAND()*5),
                'conglomerat','granit','calcaria','arenisca','altres'
            ),
            FLOOR(1 + RAND()*100)
        );

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL insertar_vies_gel();

-- =========================================
-- LLARGS
-- =========================================

-- Deportiva (1 llarg)
INSERT INTO llarg (id_via_esportiva, num_llarg, llargada, grau)
SELECT id_via, 1, llargada_total, grau
FROM via_esportiva;

-- Classica
DELIMITER $$

CREATE PROCEDURE insertar_llargs_classica()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE vid INT;
    DECLARE cur CURSOR FOR SELECT id_via FROM via_classica;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO vid;
        IF done THEN LEAVE read_loop; END IF;

        SET @n = FLOOR(2 + RAND()*3);
        SET @i = 1;

        WHILE @i <= @n DO
            INSERT INTO llarg (id_via_classica, num_llarg, llargada, grau)
            VALUES (vid, @i, FLOOR(15 + RAND()*15), '6a');
            SET @i = @i + 1;
        END WHILE;

    END LOOP;

    CLOSE cur;
END$$

DELIMITER ;

CALL insertar_llargs_classica();

-- Gel
DELIMITER $$

CREATE PROCEDURE insertar_llargs_gel()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE vid INT;
    DECLARE cur CURSOR FOR SELECT id_via FROM via_gel;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO vid;
        IF done THEN LEAVE read_loop; END IF;

        SET @n = FLOOR(2 + RAND()*3);
        SET @i = 1;

        WHILE @i <= @n DO
            INSERT INTO llarg (id_via_gel, num_llarg, llargada, grau)
            VALUES (vid, @i, FLOOR(15 + RAND()*15), '5');
            SET @i = @i + 1;
        END WHILE;

    END LOOP;

    CLOSE cur;
END$$

DELIMITER ;

CALL insertar_llargs_gel();


SELECT * FROM sector;