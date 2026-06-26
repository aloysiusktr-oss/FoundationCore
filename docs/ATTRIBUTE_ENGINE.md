# Attribute Engine Specification

## Purpose

The Attribute Engine controls all player stats used by combat, skills, items, equipment, buffs, pets, quests, and future RPG systems.

It prevents us from hardcoding stats directly into PlayerProfile.

---

## Core Rule

PlayerProfile should not contain individual stat fields like:

- health
- strength
- defense
- speed

Instead, it owns an AttributeMap.

---

## Base Attributes

### Combat

- HEALTH
- DEFENSE
- STRENGTH
- SPEED
- INTELLIGENCE
- CRIT_CHANCE
- CRIT_DAMAGE
- BONUS_ATTACK_SPEED
- FEROCITY

### Gathering

- MINING_FORTUNE
- FARMING_FORTUNE
- FORAGING_FORTUNE
- FISHING_SPEED

### Utility

- MAGIC_FIND

---

## Future Modifier Types

### Flat Add

Adds directly to the value.

Example:

Strength +50

### Percentage Add

Adds a percentage bonus.

Example:

Strength +20%

### Multiplicative Bonus

Multiplies the final value.

Example:

Final Damage x1.25

---

## Future Sources

Attributes may eventually come from:

- Base profile
- Armor
- Weapons
- Accessories
- Pets
- Skills
- Potions
- Buffs
- Guild bonuses
- Temporary events
- Area effects
- NPC blessings

---

## Design Rule

No gameplay system should calculate attributes manually.

All final stats must eventually go through the Attribute Engine.

---

## Initial Implementation

Phase 1:

- Attribute enum
- AttributeMap
- Add AttributeMap to PlayerProfile
- Save/load attributes to profile YAML
- Display attributes with `/foundation attributes`

Phase 2:

- AttributeModifier
- ModifierOperation
- AttributeCalculator

Phase 3:

- Equipment modifiers
- Buff modifiers
- Temporary modifiers