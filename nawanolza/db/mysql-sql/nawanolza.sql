drop database IF EXISTS nawanolza;
create database nawanolza;
use nawanolza;

create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(80) NOT NULL,
  `image` varchar(255) NOT NULL DEFAULT 'ffff',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `characters` (
  `character_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `rare` tinyint NOT NULL,
  PRIMARY KEY (`character_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collection` (
  `collection_id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `character_id` bigint NOT NULL,
  `current_level` tinyint NOT NULL,
  PRIMARY KEY (`collection_id`),
  CONSTRAINT `collection_fk_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `collection_fk_characters` FOREIGN KEY (`character_id`) REFERENCES `characters` (`character_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `histories` (
  `history_id` bigint NOT NULL AUTO_INCREMENT,
  `collection_id` bigint NOT NULL,
  `level` tinyint NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`history_id`),
  CONSTRAINT `histories_fk_collection` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`collection_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `type_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `character_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `character_type` (
  `character_type_id` bigint NOT NULL AUTO_INCREMENT,
  `character_id` bigint NOT NULL,
  `type_id` bigint NOT NULL,
  PRIMARY KEY (`character_type_id`),
  CONSTRAINT `histories_fk_characters` FOREIGN KEY (`character_id`) REFERENCES `characters` (`character_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `histories_fk_types` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `quiz_id` bigint NOT NULL AUTO_INCREMENT,
  `context` varchar(500) NOT NULL,
  `answer` tinyint NOT NULL,
  PRIMARY KEY (`quiz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `game_id` bigint NOT NULL AUTO_INCREMENT,
  `time` int NOT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `nawanolza`.`game`
(`time`)
VALUES
(10);

INSERT INTO `nawanolza`.`game`
(`time`)
VALUES
(5);

INSERT INTO `nawanolza`.`game`
(`time`)
VALUES
(15);

INSERT INTO `nawanolza`.`game`
(`time`)
VALUES
(10);

INSERT INTO `nawanolza`.`game`
(`time`)
VALUES
(5);

INSERT INTO `nawanolza`.`characters`
(`name`,
`description`,
`rare`)
VALUES
("짱구",
"국내판 짱구 성우는 무척이나 다양한데, 위에서 보다시피 다른 성우들이 맡기도 했지만 많은 사람들이 그 중 박영남 버전을 가장 높게 인식한다. 박영남 성우 이전 첫 짱구 성우였던 이영주 성우 버전도 자연스럽고 듣기 좋다고 평가받는데 세월이 많이 지나 묻혔다. 국내 첫 짱구 성우인 이영주 성우와, 공중파 첫 짱구 성우이자 가장 오래 한 짱구 성우인 박영남 성우 이외의 다른 성우들은 이질적이라 다소 어색하다는 느낌을 준다. 박영남 성우가 짱구를 맡은 뒤부터는 짱구 성우가 안 바뀌어서 신짱구 = 박영남으로 평가받을 정도. 만약 박영남 성우가 짱구 역을 맡지 못하는 날이 온다면 그날은 한국 짱구의 제삿날이라는 농담까지 있을 정도로 의미가 크다",
1);

INSERT INTO `nawanolza`.`characters`
(`name`,
`description`,
`rare`)
VALUES
("흰둥이",
"짱구는 못말려의 노하라 일가가 키우는 강아지이며,[20] 수컷이다. 원작이나 애니나 초반에 등장한 고참 레귤러다.[21] 노하라 일가가 마타즈레장에 거주하고 있을 때는 맨션에서 반려견을 키울 수 없기 때문에 옆집 아주머니가 맨션에서 거주할 동안만 키워주신다는 설정으로 잠시 하차했었고, '이제 시로도 같이 살아요' 편에서 복귀하였다. 이름 그대로 털 색깔이 하얀색(白)이며, 로컬라이징판은 흰둥이다. 우리나라에서 하얀 개를 흰둥이, 검은 개를 검둥이라고 많이 부르듯이 일본에서도 하얀 개를 '시로', 검은 개를 '쿠로' 라고 많이 부른다. 이를 생각해 보면 매우 적절한 번역이다.",
0);

INSERT INTO `nawanolza`.`characters`
(`name`,
`description`,
`rare`)
VALUES
("철수",
"신짱구와 같은 떡잎 유치원 해바라기반에 다니고 있다. 떡잎마을 방범대의 일원 중 하나로, 5살임에도 불구하고 머리가 굉장히 좋고 박식하다. 단순히 평범한 아이가 잘난체를 하는 정도가 아니라 실제로 상당히 똑똑해 초등학생에 준하는 두뇌를 가지고 있고 정신연령도 또래 남자애들에 비해 높은편.",
0);
