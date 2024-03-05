# Project

- Create a Gradle project
  - `New` => `Project...` => `Gradle`
- Modify [`build.gradle`](https://docs.gradle.org/current/userguide/antlr_plugin.html):
  ```gradle
  plugins {
      id 'antlr'
  }
  ```

The ANTLR plugin adds a number of tasks to your project,
such as `generateGrammarSource` and `generateTestGrammarSource`.

The ANTLR plugin adds an antlr dependency configuration which provides the ANTLR implementation to use.
If no dependency is declared, `antlr:antlr:2.7.7` will be used as the default.

  ```gradle
  dependencies {
    // https://mvnrepository.com/artifact/org.antlr/antlr4
    antlr 'org.antlr:antlr4:4.13.1'
  }
  ```

> WARNING: Do not use `implementation 'org.antlr:antlr4:4.13.1'`!
> This will also download `antlr:antlr:2.7.7` as the default.

Wait for Gradle to sync and download the dependencies.

- Project Structures
  - create directory `src/main/antlr`
  - create directory `src/test/antlr`

## Run
Run the `generateGrammarSource` task.

It will generate lexer and parser files in `build/generated-src/antlr/main`