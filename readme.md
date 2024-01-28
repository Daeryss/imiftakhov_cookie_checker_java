# Cookie Analyzer Application

<details>
<summary>Task description</summary>

### Most Active Cookie

Given a cookie log file in the following format:

```markdown
cookie,timestamp
AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00
AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00
4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00
fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00
4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00
```

Write a command line program in your preferred language to process the log file and return the most active
cookie for a specific day. Please include a `-f` parameter for the filename to process and a `-d` parameter to
specify the date.

e.g. we’d execute your program like this to obtain the most active cookie for 9th Dec 2018.

`$ ./[command] -f cookie_log.csv -d 2018-12-09`

And it would write to stdout:

`AtY0laUfhglK3lC7`

We define the most active cookie as one seen in the log the most times during a given day.

Assumptions:

- If multiple cookies meet that criteria, please return all of them on separate lines.
- Please only use additional libraries for testing, logging and cli-parsing. There are libraries for most languages
  which make this too easy (e.g pandas) and we’d like you to show off your coding skills.
- You can assume `-d` parameter takes date in UTC time zone.
- You have enough memory to store the contents of the whole file.
- Cookies in the log file are sorted by timestamp (most recent occurrence is the first line of the file).

We're looking for a concise, maintainable, extendable and correct solution. We're hoping you'll deliver your solution as
production grade code and demonstrate:

- good testing practices
- knowledge of build systems, testing frameworks, etc.
- clean coding practices (meaningful names, clean abstractions, etc.)

Please use a programming language you’re very comfortable with. The next stage of the interview
will involve extending your code.

</details>

## Running the Application

To run the Cookie Analyzer Application, you need to execute the `CookieAnalyzerApp` class with specific command-line
arguments. The application requires two arguments:

- `-f` which specifies the path(s) to the cookie log file(s)
- `-d` which specifies the target date for which you want to find the most active cookie(s)

The date should be provided in the `YYYY-MM-DD` format.

For example, to run the application for December 9, 2018, and read from a file located
at `/path/to/your/cookie_log.csv`, you would use the following command:

```shell
java CookieAnalyzerApp -f /path/to/your/cookie_log.csv -d 2018-12-09
```

If you want to specify multiple files, you can list them all after the `-f` flag, separated by spaces:

```shell
java CookieAnalyzerApp -f /path/to/your/cookie_log1.csv /path/to/another/cookie_log2.csv -d 2018-12-09
```

## Logging Configuration

This project is configured to use standard Java logging. To enable logging to a file, you must set the VM options before
running the application. You can set the VM options in your IDE or command line to point to the `logging.properties`
file, which contains the logging settings.

For example, if you are using IntelliJ IDEA, you can set the VM options in the Run/Debug Configurations dialog:

- Open Run -> Edit Configurations in the top menu
- In the VM options field, add the following line:

```markdown
-Djava.util.logging.config.file=src/main/resources/logs/logging.properties
```

- Click "Apply" and then "OK" to save the configuration

Now, when you run the application, the logging output will be written to the file as configured in
your `logging.properties` file.

## Application Architecture

The Cookie Analyzer Application is built upon a series of abstractions that allow for scalability and future
enhancements. At its core, it utilizes interfaces such as `CookieFileReader` and `CookieFileProcessor` to define the
required behaviors for reading cookie logs and processing them to identify the most active cookies. Implementations of
these interfaces can be easily swapped or extended without affecting the rest of the system, which adheres to the
Open/Closed Principle of SOLID design.

By leveraging these abstractions, the application is well-positioned to adapt to new requirements, such as supporting
additional file formats or introducing new data processing algorithms, with minimal changes to the existing codebase.

This will enable file logging as per the configurations specified in `logging.properties`.

### Implemented Classes

- `CookieFileProcessorImpl`: Implements the `CookieFileProcessor` interface, processing the files to find the most
  active cookies for a specified date. It uses the `CSVCookieFileReader` to read and filter cookie data from CSV files.

- `CSVCookieFileReader`: Implements the `CookieFileReader` interface, responsible for parsing CSV files and
  creating `Cookie` instances that match the given date. It handles file validation and date comparison to ensure
  accurate data retrieval.

These classes encapsulate specific responsibilities, ensuring that the system is modular and maintainable. The use of
interfaces allows for easy expansion or alteration without impacting the overall system, providing a solid foundation
for future enhancements or modifications.

For instance, if a new requirement arises to support a different file format, a new class implementing
the `CookieFileReader` interface can be added without altering the processing logic within `CookieFileProcessorImpl`.
Similarly, changes to the processing logic can be made within `CookieFileProcessorImpl` without affecting the CSV
reading logic in `CSVCookieFileReader`.

