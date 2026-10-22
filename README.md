# Android course apps (Java)

The five apps I built during my Android course (2022–2023), recovered and refactored in **Java**, the language they were written in. Same screens, same features, cleaner code.

*Las cinco apps de mi curso de Android (2022-2023), recuperadas y con el código reescrito en Java: mismas pantallas y funciones, código más limpio.*

| App | What it practises |
|---|---|
| **AboutPage** | Implicit intents: Waze, phone dialer, Facebook, SMS and email |
| **Basic UI** | CheckBox, RadioGroup and a ProgressBar driven by a `Handler` |
| **Basic UI 3** | ConstraintLayout with guidelines and dimension ratios |
| **Class1** | A form whose input is echoed back on screen |
| **LastProject** | A Japanese sweets shop: register/login, product list with quantities, cart with totals, pickup date and time, order by SMS and email, profile with camera photo, and a contact page |

Download the APKs from [Releases](../../releases).

## What changed in the refactor

- **No duplicated code.** The shop had the same block copied six times (one per product) and the same top menu copied into three screens. Now there is a `Product` catalogue, a `CartStorage`, a shared `ShopActivity` menu and reusable `Dialogs` (confirm, gender, date).
- **Strings in resources.** Every user-facing text lives in `strings.xml`; messages with values use format strings.
- **Modern APIs.** Lambdas instead of anonymous classes, `registerForActivityResult` instead of `startActivityForResult`, `Handler` instead of a raw `Thread` touching the UI, listeners set in code instead of `android:onClick`.
- **Bugs fixed.**
  - The call and SMS buttons crashed because they never asked for permission; they now ask, or use intents that need none.
  - The Waze button crashed when Waze was not installed; it now falls back to the browser.
  - The cart's time picker swapped hours and minutes.
  - The email form accepted an empty message because of an inverted condition.
  - The login checked the same condition twice.
- **Safer account storage.** The password is stored as a salted SHA-256 hash instead of plain text, so the profile screen now leaves it empty to keep the current one.
- **No personal data.** Phone numbers, email, social profiles and map coordinates are placeholders, kept in one place (`Store.java`).

## Build

JDK 17+ and the Android SDK (API 36) are required.

```bash
./gradlew assembleDebug            # all five apps
./gradlew :lastproject:installDebug
./gradlew assembleRelease          # minified builds (R8)
```

`targetSdk` is 34 on purpose: the original layouts were designed before Android 15 made edge-to-edge mandatory.
