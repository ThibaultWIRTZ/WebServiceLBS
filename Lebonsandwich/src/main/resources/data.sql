DROP TABLE IF EXISTS `sandwich`;
CREATE TABLE `sandwich` (
  `id` IDENTITY NOT NULL,
  `nom` varchar(128) NOT NULL,
  `description` varchar(128) NOT NULL,
  `typepain` varchar(128) NOT NULL,
  `tarif` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` IDENTITY NOT NULL,
  `nom` varchar(128) NOT NULL,
  `description` varchar(128) NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sandwich_categories`;
CREATE TABLE `sandwich_categories` (  
  `sandwich_id` int(11) NOT NULL,
  `categories_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO sandwich VALUES (1,	'panini raclette', 'Sandwich grille a la raclette','blanc','5.35');
INSERT INTO sandwich VALUES (2,	'panini au poulet', 'Sandwich grille au poulet','blanc','5.35');
INSERT INTO sandwich VALUES (3,	'le bagnat', 'Sandwich au thon','blanc','5.35');
INSERT INTO sandwich VALUES (4,	'le bucheron', 'Sandwich aux trois viandes','blanc','5.35');
INSERT INTO sandwich VALUES (5,'jambon-beurre', 'Sandwich au jambon','blanc','5.35');
INSERT INTO sandwich VALUES (6,	'fajitas poulet', 'Sandwich au poulet dans une fajitas','blanc','5.35');
INSERT INTO sandwich VALUES (7,	'le forestier', 'Sandwich aux champignons et aux lardons','blanc','5.35');

INSERT INTO category VALUES (1,'végétarien','sandwich sans viande');
INSERT INTO category VALUES (2,'carnivore','sandwich avec plusieurs sorte de viande');
INSERT INTO category VALUES (3,'spécial','sandwich du mois');

INSERT INTO sandwich_categories VALUES (1,3);
INSERT INTO sandwich_categories VALUES (2,2);
INSERT INTO sandwich_categories VALUES (3,4);