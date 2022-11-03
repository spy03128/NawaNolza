INSERT INTO `nawanolza`.`types`
(`name`)
VALUES ("힘");

INSERT INTO `nawanolza`.`types`
(`name`)
VALUES ("지능");

INSERT INTO `nawanolza`.`types`
(`name`)
VALUES ("민첩");

INSERT INTO `nawanolza`.`types`
(`name`)
VALUES ("방어");

-- 캐릭터 1 판다
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("판다","중국 쓰촨성 일대에 주로 서식하는 포유류 동물이다. 중국어로 곰고양이라는 뜻이다. 판다곰이라는 이름답게 곰과 판다속 대왕판다종이다.", 1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(1,2);

-- 캐릭터 2 강아지
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("강아지","식육목 갯과 개속의 늑대 가운데 특히 야생성이 적고 인간에게 친숙한 아종을 이르는 말이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(2,2);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(2,3);

-- 캐릭터 3 당나귀
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("당나귀","말과에 속하는 가축. 야생의 당나귀를 가축화한 동물로 수송 수단으로 이용되며, 속담, 해학담의 소재로 등장하고 성의 상징적 존재의 설화도 전한다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(3,3);

-- 캐릭터 4 양
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("양","소과에 속하는 포유동물. 가축의 하나로 오스트레일리아와 뉴질랜드는 세계 제일의 면양국이다. 우리나라는 고려 때 금나라에서 들어온 것으로 희생용, 제사용, 약, 육용, 양모는 직물의 원료와 모피로 이용된다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(4,3);

-- 캐릭터 5 사자
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("사자","아시아의 호랑이와 함께 대형 고양이족 가운데 최대의 맹수로서 '백수의 왕'으로 불린다. 표범, 재규어, 호랑이와 근연속이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(5,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(5,2);

-- 캐릭터 6 사슴
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("사슴","사슴과에 속하는 동물의 총칭. 북극에서 아프리카 북서부, 남북대륙 어디서나 살고 있는 성질이 온순하고 비겁하며 순초식성으로 뿔은 녹용이라 하여 귀한 한약재다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(6,3);

-- 캐릭터 7 기린
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("기린","우제목 기린과에 속하는 포유동물. 포유동물 중에서 키가 가장 큰 동물로 사하라사막 이남의 대부분의 아프리카 대륙에 분포한다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(7,4);


-- 캐릭터 8 코끼리
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("코끼리","코끼리과에 속하는 동물의 총칭. 인도코끼리와 아프리카코끼리 2종으로 우리나라에는 자생하지 않는 영리하고 온순하여 사람에게 잘 사역된다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(8,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(8,2);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(8,4);

-- 캐릭터 9 다람쥐
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("다람쥐","다람쥐과에 속하는 동물. 우리 나라의 어느 지방에서나 볼 수 있는 동물로 울창한 침엽수림이 주 서식지며, 활엽수림, 돌담, 땅속 등에 살면서 식물저장창고를 만드는 동물로 인식된다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(9,3);

-- 캐릭터 10 젖소
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("젖소","홀스타인종 ·저지종 ·건지종 ·에어셔종 등이 대표적인 품종이며, 그 대부분이 유럽 원산이다. 한국에는 젖소로 개량된 품종은 없으며 모두가 수입한 품종이 계통적으로 번식되고 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(10,4);

-- 캐릭터 11 바다사자
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("바다사자","동해를 사이에 둔 한반도 해안과 일본 열도 해안 일대 연안에 서식했던 현재는 멸종한 바다사자류의 일종. 어류와 오징어를 주식으로 했으며, 체구는 수컷 2.5m, 암컷 1.64m 정도로 근연종인 캘리포니아바다사자보다 컸다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(11,2);

-- 캐릭터 12 호랑이
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("호랑이","호랑이는 고양이과에 속하는 포유동물이다. 우리나라의 건국신화에도 등장하고 올림픽대회의 마스코트로 선정될 정도로 우리에게 친숙한 동물이다.", 1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(12,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(12,2);

-- 캐릭터 13 원숭이
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("원숭이","포유류 영장목 중에서 사람을 제외한 동물의 총칭. 유럽.오스트레일리아.북아메리카를 제외한 신구 양세계의 적도를 중심으로 분포하며, 우수한 지능과 행동을 가졌다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(13,2);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(13,3);

-- 캐릭터 14 생쥐
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("생쥐","쥐목 쥣과에 속하는 동물. 몸길이는 6~10cm이고 꼬리길이도 이와 비슷하다. 전세계적으로 분포하며 실험용, 애완용으로도 기른다. 시궁쥐와 함께 '쥐'하면 가장 먼저 떠오르는 종이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(14,3);

-- 캐릭터 15 상어
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("상어","연골어류 악상어목에 속하는 종류의 총칭.전세계의 외양에 널리 분포한다. 몸길이는 콜롬비아의 바다에 사는 상어의 경우 16㎝밖에 되지 않을 정도로 작으며, 가장 큰 상어인 고래상어는 최대 18m까지 성장하는 등 종류에 따라 크기가 다양하다.", 1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(15,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(15,3);

-- 캐릭터 16 코뿔소
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("코뿔소","말목 코뿔소과에 속하는 동물의 총칭. 흰코뿔소는 남아프리카나 중앙아프리카의 일부에서 살고, 몸이 다른 코뿔소보다 크며 뿔은 두 개로 우리 나라에는 자생하지 않는다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(16,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(16,4);

-- 캐릭터 17 뱀
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("뱀","뱀목에 속하는 동물의 총칭. 뱀은 극지를 제외하고 전 세계에 분포하며, 주로 인간을 해치려는 사악한 존재, 신앙의 대상으로 등장하고 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(17,2);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(17,3);

-- 캐릭터 18 코알라
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("코알라","현지에서는 네이티브베어라고 하며, 아기보기곰·나무타기주머니곰·네이티브베어라고도 한다. 몸길이 60∼80㎝이고, 몸무게 수컷 약 10.5㎏, 암컷 약 8.2㎏이다. 영장류가 아니면서도 유일하게 지문이 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(18,2);

-- 캐릭터 19 고릴라
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("고릴라","고릴라는 사람과에 속하는 유인원으로 사하라 사막 이남의 중부 아프리카에 서식하고 있다. 동부고릴라와 서부고릴라로 나뉘어 있고, 동부 고릴라는 다시 산지고릴라와 저지고릴라 등이 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(19,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(19,2);

-- 캐릭터 20 멧돼지
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("멧돼지","멧돼지과에 속하는 동물. 유라시아대륙 중부와 남부의 삼림에 살며, 주로 깊은 산과 활엽수가 우거진 곳에 서식하기를 좋아한다. 부상당한 멧돼지는 난폭한 공격성을 지닌다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(20,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(20,2);

-- 캐릭터 21 흑표범
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("흑표범","표범의 흑변종(黑變種)이다. 흑표범·흑표라고도 한다. 몸길이는 140∼160㎝로, 다른 표범과 같다. 몸빛깔은 보통의 표범과는 달리 바탕색이 암갈색 또는 흑갈색으로 언뜻 보기에는 검게 보인다.", 1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(21,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(21,3);

-- 캐릭터 22 제규어
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("제규어","스페인어로는 하구아르. 아메리카 대륙의 열미저지대에 서식하는 묘과 동물. 옛날부터 신격화되어 메소아메리카의 올메카 문화나, 중앙안데스의 챠빈 문화 등에 그 형상이나 속성이 문양화되어 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(22,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(22,3);

-- 캐릭터 23 돼지
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("돼지","멧돼지과에 속하는 잡식성 포유동물로, 중국대륙 동부에서 우수리강 유역에 걸쳐 살고 있는 멧돼지와 동남아시아 멧돼지 등이 가축화된 것으로, 가축화되면서 각 지역마다 독특한 재래종이 형성된 것으로 추측된다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(23,2);

-- 캐릭터 24 얼룩말
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("얼룩말","검고 흰 얼룩무늬가 있는 말과 말속의 야생동물. 현생 말의 얼마 안 되는 친족이다. 아프리카 대륙의 사바나 지역에 널리 분포한다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(24,3);

-- 캐릭터 25 황소
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("황소","황소는 소 속에 속한 초식동물로, 가축의 하나이며, 어린 개체는 송아지라 부른다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(25,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(25,4);

-- 캐릭터 26 펭귄
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("펭귄","조류 펭귄목 펭귄과 동물을 통틀어 이르는 말. 펭귄과의 바닷새로서 남반구에 6속 18종이 있다.", 1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(26,2);

-- 캐릭터 27 하마
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("하마","하마과에 속하는 동물. 열대 아프리카의 하천과 호수 주변에 분포하는데, 특히 우간다 서부의 강과 호수에 많다. 우리 나라에는 1912년 6월 암수 1쌍이 수입된 것이 시초며 세계적인 희귀종으로 국제 보호 동물이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(27,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(27,4);

-- 캐릭터 28 여우
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("여우","개과에 속하는 포유동물로, 우리 나라에 분포된 여우는 유럽·북아프리카·아시아·북아메리카에 널리 분포하는 ‘레드 폭스’로 통칭되는 종류이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(28,2);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(28,3);

-- 캐릭터 29 두더지
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("두더지","두더지과에 속하는 동물로, 두더지는 집쥐 다음으로 우리 주변에 많이 서식하고 있는 짐승이나, 그 생태에 관하여는 잘 조사되어 있지 못하다. 그 이유는 두더지가 진동에 지극히 민감하여 사람이 가까이 가기 전에 숨어버리기 때문이다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(29,3);

-- 캐릭터 30 뱀
INSERT INTO `nawanolza`.`characters`(`name`,`description`,`rare`)
VALUES("뱀","뱀목에 속하는 동물의 총칭. 뱀은 극지를 제외하고 전 세계에 분포하며, 주로 인간을 해치려는 사악한 존재, 신앙의 대상으로 등장하고 있다.", 0);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(30,1);
INSERT INTO `nawanolza`.`character_type`(`character_id`,`type_id`)
VALUES(30,4);


