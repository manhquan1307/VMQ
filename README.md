# VMQ

High-performance distributed event streaming platform for scalable cloud-native systems.

## Overview

VMQ is a message queue / event streaming stack built as a set of Gradle modules. Each component can be built and run from its own directory.

## Repository layout

| Module    | Role |
|-----------|------|
| `broker`  | Message broker / coordination |
| `client`  | Client library or sample client |
| `common`  | Shared utilities and types |
| `protocol`| Wire format and protocol definitions |
| `storage` | Persistence layer |

## Build

Prerequisites: JDK compatible with the project’s Gradle version, Gradle wrapper included in each module.

From a module directory (for example `broker`):

```bash
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

## License

This project is licensed under the [MIT License](LICENSE).

Copyright © 2026 [manhquan1307](https://github.com/manhquan1307).
