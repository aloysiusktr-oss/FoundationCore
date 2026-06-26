# FoundationCore Architecture

> FoundationCore is not just a plugin. It is the engine layer for Project Foundation and the technical training ground for Project Ascension.

---

## Core Structure

```text
FoundationCore
│
├── engine
│   └── FoundationEngine
│
├── service
│   ├── FoundationService
│   ├── ServiceRegistry
│   ├── PlayerService
│   ├── ConfigService
│   ├── EconomyService
│   ├── GUIService
│   └── QuestService
│
├── player
│   ├── FoundationPlayer
│   ├── PlayerManager
│   └── PlayerListener
│
├── storage
│   └── PlayerDataStorage
│
├── command
├── config
├── economy
├── event
├── gui
├── item
├── npc
├── quest
├── scheduler
├── util
└── world