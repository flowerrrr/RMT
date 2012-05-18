rem create user-extensions.js
cat user-extensions.js.implicitWait ..\user-extensions.js.commands > user-extensions.js
java -jar selenium-server-standalone-2.21.0.jar -userExtensions user-extensions.js -singleWindow -htmlSuite "*firefox" "http://localhost:8080/das-tool/" ..\testcases\Systemtest-TestSuite.html results.html