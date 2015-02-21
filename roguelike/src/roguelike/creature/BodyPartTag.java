package roguelike.creature;

/**
 *
 * @author nik
 */
public enum BodyPartTag {
    /**
     * MIND - часть тела позволяет сущетву управлять собой
     * HAUL - часть тела позваляет переносить предметы
     * MOVE - часть тела позваляет хожить
     * FLY - часть тела позволяет летать
     * LABOR - часть тела позволяет делать ручную работу
     * ORGAN - чачть тела является органом
     * EQUIPMENTSLOT - имеет слот экипировки ВНИМАНИЕ он должен быть прописан отдельно
     * 
     * OWNERDIE - владелец умирает вместе с частью тела
     * CANBLEED - может кровоточить
     * BLEEDDIE - умирает от потери крови
     * MAGICIMMUNE  - имунитет к магии
     * 
     * HOLYIMUNE, UNHOLYIMUNE, NATUREIMMUNE, FIREIMMUNE, FROSTIMMUNE, PHUSICALIMMUNE
     * 
     * PIERCINGIMMUNE - имунитет к проникающему урону
     * CUTTINGIMMUNE - имунитет к режущему урону
     * CRUSHINGIMMUNE - имунитет к дробящему урону
     * DAMAGEIMMUNE - иммунитет к урону
     * 
     * CUTTED - часть тела отрезана
     * 
     */
    MIND, HAUL, MOVE, LABOR, ORGAN, EQUIPMENTSLOT, 
    OWNERDIE, CANBLEED, BLEEDDIE,
    MAGICIMMUNE, PIERCINGIMMUNE, CUTTINGIMMUNE, CRUSHINGIMMUNE,  DAMAGEIMMUNE, 
    HOLYIMUNE, UNHOLYIMUNE, NATUREIMMUNE, FIREIMMUNE, FROSTIMMUNE, PHUSICALIMMUNE,
    CUTTED
    
}
