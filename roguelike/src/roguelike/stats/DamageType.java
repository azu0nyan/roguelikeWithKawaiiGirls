package roguelike.stats;

/**
 *
 * @author nik
 */
public enum DamageType {
    /**
     * При добавлении новых типов урона внести изменения в mapDamageTypeToBodyPartImunneTag     * 
     * 
     * PIERCING - повреждает внутренние органы, вызывает кровотечение внутренних органов
     * CUTTING - может отрезать часть тела, вызывает кровотечение внешних частей тела, останавливается костями
     * CRUSHING - наносит большие повреждения, на среднюю глубину, и огромные повреждения костям  
     * UNIFORM  - воздействует на все части  тела равномерно
     */
    PIERCING, CUTTING, CRUSHING, UNIFORM 
}
