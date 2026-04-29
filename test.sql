- ============================================================
--  DADES D'EXEMPLE (~120 registres coherents)
--  Ordre d'inserció respecta les FK
-- ============================================================
 
-- ── ESCALADORS (10) ──────────────────────────────────────────
INSERT INTO escalador (nom, alias, edat, nivell, via_max_nivell, estil_preferit) VALUES
('Marc Ferrer',       'Ferro',    32, '8b',  'Biographie',        'esportiva'),
('Laura Puig',        'Lapua',    26, '7c+', 'Ali Hulk',          'esportiva'),
('Jordi Bosch',       'Boschet',  41, '7a',  'Cavall Bernat',     'classica'),
('Marta Sala',        'Marsal',   29, '6c+', 'El Falco',          'esportiva'),
('Pere Roca',         'Rocasec',  35, '8a+', 'Superman',          'esportiva'),
('Anna Vidal',        'Vidaleta', 23, '7b+', 'La Bestia',         'classica'),
('Miquel Tort',       'Tortuga',  48, '6b',  'Espero del Migdia', 'classica'),
('Cristina Mas',      'Cris',     31, '7c',  'Tres Torres',       'gel'),
('Arnau Pla',         'Planer',   19, '6a+', 'Paret Groga',       'esportiva'),
('Sofia Gomez',       'Sofi',     27, '8b+', 'Golpe de Estado',   'gel');
 
-- ── ESCOLES (5) ──────────────────────────────────────────────
INSERT INTO escola (nom, poblacio, aproximacio, popularitat) VALUES
('Montserrat',        'Monistrol de Montserrat', 'Acces per cable car o carretera fins al monestir. 10-30 min caminant fins als sectors.', 'alta'),
('Siurana',           'Cornudella de Montsant',  'Carretera TV-7011 fins al poble de Siurana. Sectors a 5-20 min a peu.',                  'alta'),
('El Cogul',          'Les Garrigues',            'Pista de terra des de la L-702. Aparcament al peu de la paret.',                         'baixa'),
('Mallos de Riglos',  'Ayerbe',                   'Poble de Riglos accessible per A-132. Sectors ben senyalitzats des del nucli urba.',      'mitjana'),
('Vall de Boi',       'Barruera',                 'Carretera N-230 fins a Barruera. Cascades a 30-90 min amb raquetes o esquis.',            'baixa');
 
-- ── SECTORS (8) ──────────────────────────────────────────────
-- id_escola: 1=Montserrat 2=Siurana 3=El Cogul 4=Riglos 5=Vall de Boi
INSERT INTO sector (nom, id_escola, latitud, longitud, aproximacio, popularitat, restriccions, tipus_vies) VALUES
('Degotalls',        1, 41.5936820,  1.8328150, '15 min des del monestir seguint el sender senyalitzat.',       'alta',    NULL,                                                                   'classica_esportiva'),
('Sant Benet',       1, 41.5948100,  1.8301230, '20 min des de la carretera per cami de ferradura.',            'mitjana', 'Tancat de febrer a juny per nidificacio de falcons peregrins.',        'classica_esportiva'),
('El Pati',          2, 41.2631450,  0.9274800, '5 min des del parking del poble de Siurana.',                  'alta',    NULL,                                                                   'classica_esportiva'),
('Els Murons',       2, 41.2645000,  0.9259000, '10 min des del poble per cami de terra entre oliveres.',       'alta',    NULL,                                                                   'classica_esportiva'),
('La Pedrera',       3, 41.4800000,  0.7120000, 'Acces directe des de la pista forestal. Aparcament a 5 min.', 'baixa',   NULL,                                                                   'classica_esportiva'),
('El Fire',          4, 42.3601000, -0.7720000, '20 min a peu des de Riglos seguint la senyalitzacio groga.',   'mitjana', NULL,                                                                   'classica_esportiva'),
('Cascada del Doll', 5, 42.5120000,  0.8450000, '45 min amb raquetes des del refugi de Colomers (1850 m).',     'baixa',   'Acces condicionat a bones condicions de gel. Consultar previament.',  'gel'),
('Circ de Colomers', 5, 42.5210000,  0.8521000, '90 min des de Barruera. Material alpi obligatori.',            'baixa',   'Zona de proteccio especial PEIN. Respectar estrictament l entorn.',   'gel');
 
-- ── VIES ESPORTIVES (25) ─────────────────────────────────────
-- id_sector: 1=Degotalls 2=Sant Benet 3=El Pati 4=Els Murons 5=La Pedrera 6=El Fire
INSERT INTO via_esportiva (nom, id_sector, llargada_total, grau, orientacio, estat, data_inici_no_apte, data_fi_no_apte, ancoratge, tipus_roca, id_creador, restriccions) VALUES
('El Gripau Daurat',  1, 25, '6c',  'SE', 'apte',        NULL,         NULL,         'parabolts', 'calcaria',    1, NULL),
('Vent del Nord',     1, 18, '7a',  'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    2, NULL),
('Calaix de Sastre',  1, 22, '6b+', 'SE', 'apte',        NULL,         NULL,         'spits',     'calcaria',    3, NULL),
('La Grua',           1, 28, '7b',  'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    5, NULL),
('Pas del Gegant',    1, 15, '6a',  'SE', 'apte',        NULL,         NULL,         'spits',     'calcaria',    4, NULL),
('Cara de Bruixa',    2, 20, '7c',  'E',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    5, NULL),
('Ull de Vidre',      2, 17, '6c+', 'E',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    1, NULL),
('Falco Pelegrí',     2, 12, '5',   'NE', 'tancada',     '2026-02-01', '2026-07-01', 'spits',     'calcaria',    7, 'Tancada per nidificacio. Reobertura prevista l 1 de juliol.'),
('Les Estalactites',  3, 24, '7a+', 'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    2, NULL),
('Memoria de Roca',   3, 20, '8a',  'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    5, NULL),
('Dulce de Leche',    3, 18, '7b+', 'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    2, NULL),
('La Travessa',       3, 30, '6b',  'SO', 'apte',        NULL,         NULL,         'spits',     'calcaria',    4, NULL),
('Xip-i-Xap',         3, 16, '6c+', 'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    6, NULL),
('Amic del Vent',     4, 22, '7c+', 'O',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',   10, NULL),
('Sortida de Lluna',  4, 19, '7a',  'SO', 'apte',        NULL,         NULL,         'parabolts', 'calcaria',    2, NULL),
('El Drac',           4, 25, '8b',  'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    5, NULL),
('Freda de Matinada', 4, 14, '6b+', 'O',  'apte',        NULL,         NULL,         'spits',     'calcaria',    6, NULL),
('Pedra Filosofal',   4, 21, '7b',  'SO', 'construccio', '2025-09-01', '2026-10-01', 'parabolts', 'calcaria',    5, 'En rehabilitacio. Obertura prevista tardor 2026.'),
('Tarda de Tardor',   5, 18, '6a+', 'SO', 'apte',        NULL,         NULL,         'spits',     'calcaria',    9, NULL),
('El Llamp',          5, 24, '7a+', 'S',  'apte',        NULL,         NULL,         'parabolts', 'calcaria',    1, NULL),
('Pedrera Direct',    5, 12, '5',   'S',  'apte',        NULL,         NULL,         'spits',     'calcaria',    7, NULL),
('Torre del Vent',    6, 28, '7b',  'N',  'apte',        NULL,         NULL,         'parabolts', 'conglomerat', 8, NULL),
('Riglos Express',    6, 20, '6c',  'NE', 'apte',        NULL,         NULL,         'parabolts', 'conglomerat', 3, NULL),
('Pas de Gegant',     6, 25, '7a',  'N',  'apte',        NULL,         NULL,         'parabolts', 'conglomerat', 1, NULL),
('El Mono Enrabiat',  6, 22, '7c',  'NE', 'apte',        NULL,         NULL,         'quimics',   'conglomerat', 5, NULL);
 
-- ── VIES CLASSIQUES (10) ─────────────────────────────────────
INSERT INTO via_classica (nom, id_sector, llargada_total, grau, orientacio, estat, ancoratge, tipus_roca, id_creador, restriccions) VALUES
('Espero Magestuos',  1, 53, '6b',  'S',  'apte', 'friends', 'calcaria',    3, NULL),
('Diedral Classic',   1, 45, '7a',  'SE', 'apte', 'tascons', 'calcaria',    7, NULL),
('La Gran Paret',     2, 70, '6c',  'E',  'apte', 'bagues',  'calcaria',    3, NULL),
('Diedre del Cel',    3, 55, '7b',  'S',  'apte', 'friends', 'calcaria',    6, NULL),
('Via dels Vells',    3, 45, '5',   'SO', 'apte', 'pitons',  'calcaria',    7, NULL),
('Cami dels Angels',  4, 40, '6a+', 'O',  'apte', 'friends', 'calcaria',    3, NULL),
('Falco i la Pedra',  4, 63, '6c+', 'SO', 'apte', 'tascons', 'calcaria',    6, NULL),
('Ruta dels Pioners', 5, 35, '6a',  'S',  'apte', 'pitons',  'calcaria',    7, NULL),
('Nord-Oest Directe', 6, 50, '7a',  'NO', 'apte', 'friends', 'conglomerat', 3, NULL),
('El Gran Mural',     6, 80, '7b+', 'N',  'apte', 'bagues',  'conglomerat', 8, NULL);
 
-- ── VIES GEL (5) ─────────────────────────────────────────────
-- id_sector: 7=Cascada del Doll 8=Circ de Colomers
INSERT INTO via_gel (nom, id_sector, llargada_total, grau, orientacio, estat, ancoratge, tipus_roca, id_creador, restriccions) VALUES
('Glacada Perfecta',  7, 53, '6a', 'N', 'apte', 'friends', 'calcaria', 8,  NULL),
('Cascada Blava',     7, 45, '7a', 'N', 'apte', 'pitons',  'calcaria', 10, 'Condicions variables. Verificar estat del gel abans de pujar.'),
('Cor de Gel',        7, 50, '7b', 'N', 'apte', 'tascons', 'calcaria', 8,  NULL),
('Agulla de Hielo',   8, 40, '6b', 'N', 'apte', 'friends', 'calcaria', 10, NULL),
('Pilar Blanc',       8, 55, '8b', 'N', 'apte', 'pitons',  'calcaria', 10, 'Via de maxima dificultat del sector. Nomes alpinistes experts.');
 
-- ── TRAMS - VIES ESPORTIVES (1 tram per via = 25 trams) ──────
INSERT INTO trams (id_via_esportiva, num_tram, llargada, grau) VALUES
(1,  1, 25, '6c'),
(2,  1, 18, '7a'),
(3,  1, 22, '6b+'),
(4,  1, 28, '7b'),
(5,  1, 15, '6a'),
(6,  1, 20, '7c'),
(7,  1, 17, '6c+'),
(8,  1, 12, '5'),
(9,  1, 24, '7a+'),
(10, 1, 20, '8a'),
(11, 1, 18, '7b+'),
(12, 1, 30, '6b'),
(13, 1, 16, '6c+'),
(14, 1, 22, '7c+'),
(15, 1, 19, '7a'),
(16, 1, 25, '8b'),
(17, 1, 14, '6b+'),
(18, 1, 21, '7b'),
(19, 1, 18, '6a+'),
(20, 1, 24, '7a+'),
(21, 1, 12, '5'),
(22, 1, 28, '7b'),
(23, 1, 20, '6c'),
(24, 1, 25, '7a'),
(25, 1, 22, '7c');
 
-- ── TRAMS - VIES CLASSIQUES (2-3 trams per via = 23 trams) ───
INSERT INTO trams (id_via_classica, num_tram, llargada, grau) VALUES
-- Via 1: Espero Magestuos (6b) total 53m
(1,  1, 25, '6a'),
(1,  2, 28, '6b'),
-- Via 2: Diedral Classic (7a) total 45m
(2,  1, 20, '6c'),
(2,  2, 25, '7a'),
-- Via 3: La Gran Paret (6c) total 70m
(3,  1, 22, '6a+'),
(3,  2, 28, '6c'),
(3,  3, 20, '6b'),
-- Via 4: Diedre del Cel (7b) total 55m
(4,  1, 25, '7a+'),
(4,  2, 30, '7b'),
-- Via 5: Via dels Vells (5) total 45m
(5,  1, 20, '4'),
(5,  2, 25, '5'),
-- Via 6: Cami dels Angels (6a+) total 40m
(6,  1, 18, '5'),
(6,  2, 22, '6a+'),
-- Via 7: Falco i la Pedra (6c+) total 63m
(7,  1, 20, '6b'),
(7,  2, 25, '6c+'),
(7,  3, 18, '6c'),
-- Via 8: Ruta dels Pioners (6a) total 35m
(8,  1, 15, '5'),
(8,  2, 20, '6a'),
-- Via 9: Nord-Oest Directe (7a) total 50m
(9,  1, 22, '6c'),
(9,  2, 28, '7a'),
-- Via 10: El Gran Mural (7b+) total 80m
(10, 1, 25, '7a'),
(10, 2, 30, '7b'),
(10, 3, 25, '7b+');
 
-- ── TRAMS - VIES GEL (2 trams per via = 10 trams) ────────────
INSERT INTO trams (id_via_gel, num_tram, llargada, grau) VALUES
-- Via gel 1: Glacada Perfecta (6a) total 53m
(1, 1, 25, '5'),
(1, 2, 28, '6a'),
-- Via gel 2: Cascada Blava (7a) total 45m
(2, 1, 20, '6c'),
(2, 2, 25, '7a'),
-- Via gel 3: Cor de Gel (7b) total 50m
(3, 1, 22, '7a'),
(3, 2, 28, '7b'),
-- Via gel 4: Agulla de Hielo (6b) total 40m
(4, 1, 18, '6a'),
(4, 2, 22, '6b'),
-- Via gel 5: Pilar Blanc (8b) total 55m
(5, 1, 25, '7c+'),
(5, 2, 30, '8b');
 
-- ============================================================
--  RESUM DE REGISTRES INSERITS
--  escalador            : 10
--  escola               :  5
--  sector               :  8
--  via_esportiva        : 25
--  via_classica         : 10
--  via_gel              :  5
--  trams (esportiva)    : 25
--  trams (classica)     : 23
--  trams (gel)          : 10
--  ─────────────────────────
--  TOTAL                : 121
-- ============================================================