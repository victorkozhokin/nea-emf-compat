# Not Enough Animations: EMF Compat

## [Modrinth](https://modrinth.com/mod/not-enough-animations-emf-compat)

A small client-side mod for **Minecraft** that pauses **Entity Model Features** player animations while **Not Enough Animations** is active.

Tested with Fresh Animations: Player Extension but it should work with any player animation resource pack.

## Video

[![NEA EMF Compat](https://img.youtube.com/vi/QIZlt0xBARQ/maxresdefault.jpg)](https://youtu.be/QIZlt0xBARQ?si=IvY1qwvvB6avab2r)

## Covered Animations (EMF yields to NEA)

| Animation | Notes |
|---|---|
| Boat (rowing) | |
| Horse (riding) | |
| Eating / Drinking | |
| Hug | |
| Item Swap | |
| Petting | |
| Map Holding | |
| Hold Up Items | Lanterns, etc. |
| Burning | Works even while sprinting |
| Freezing | Works even while sprinting |

## Ignored Animations (Fresh Animations: Player Extension handles them better)

| Animation | Reason |
|---|---|
| Ladder | FA:PE handles ladders better |
| Crawling | FA:PE handles crawling better |
| Elytra | FA:PE handles elytra better |
| Falling | FA:PE handles falling better |
| Death | FA:PE handles death better |
| Swimming | FA:PE handles swimming better |
| Bow / Crossbow | FA:PE handles bow/crossbow better; excluded intentionally due to broken behaviour |

## Sprint Behavior

The compat layer is **disabled while sprinting**, with the exception of `BurningAnimation` and `FreezingAnimation`.

## Recommended "Not Enough Animations" Settings

*(thanks to Cymock for the tip)*

These settings are in the mod **Not Enough Animations**. This mod does not feature its own settings.

| Setting | Value |
|---|---|
| Animation Smoothing | OFF |
| Disable Leg Smoothing | ON |
| Ladder Animation | OFF |

## Known Limitations

> The mod uses a pose save/restore workaround instead of the official EMF `pauseCustomAnimationsForThesePartsOfEntity` API. The API may not work correctly with ASM math compilation enabled (default in EMF 3.2.4+), though this is only a suspected issue on our end.

## Dependencies

- [NeoForge](https://neoforged.net/) 21.1+
- [Not Enough Animations](https://modrinth.com/mod/not-enough-animations) 1.8+
- [Entity Model Features](https://modrinth.com/mod/entity-model-features) 3.2.4+
- [Entity Texture Features](https://modrinth.com/mod/entitytexturefeatures) Required for EMF

## Build

```bash
./gradlew build
```

For development, put the NEA, EMF and ETF jars in `libs/` (see `build.gradle`).
