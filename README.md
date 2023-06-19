# conference-app

API pozwala zarejestrować się na prelekcje, które będą miały miejsce na konferencji 1 czerwca 2023

Po uruchomieniu aplikacji API dostępne jest pod adresem: http://localhost:8080

Do sprawdzenia działania zapytań można skorzystać z platformy Postman służącej do testowania API

Dane zapisywane są w bazie danych H2. DOstęp do niej możemy uzyskać pod adresem http://localhost:8080/h2-console<br>
login: sa<br>
hasło: sa<br>



# Endpoints

## Zapis na prelekcję

POST /conference/booking/create <br>
Request body:<br>
{<br>
    "user": <br>
    {<br>
        "login":"loginexample",<br>
        "email":"example@gmail.com"<br>
    }, <br>
    "lecture":<br>
    {<br>
        "time":10,<br>
        "path":2<br>
    }<br>
}<br>

Uwagi:<br>
-pole "time" ustawiamy na godzinę rozpoczęcia prelekcji: 10,12 lub 14<br>>
-pole "path" ustawiamy na numer wybranej ścieżki: 1, 2, lub 3<br>
-po pomyślnej rejestracji na wydarzenie do pliku /resources/powiadamienia.txt zapisywane jest potwierdzenie

-----------------------------------------------------------------------

## Anulowanie zapisu

GET /conference/booking/delete<br>
Request body:<br>
{<br>
    "user": <br>
    {<br>
        "login":"loginexample",<br>
        "email":"example@gmail.com"<br>
    },<br>
    "lecture":<br>
    {<br>
        "time":10,<br>
        "path":2<br>
    }<br>
}<br>

Uwagi:<br>
-pole "time" ustawiamy na godzinę rozpoczęcia prelekcji którą chcemy anulować: 10,12 lub 14<br>
-pole "path" ustawiamy na numer anulowanej ścieżki: 1, 2, lub 3 

-----------------------------------------------------------------------
## Wyświetlenie planu konferencji

GET /conference/getschedule

-----------------------------------------------------------------------
## Wyświetlenie listy zapisanych użytkowników

GET /conference/getusers

-----------------------------------------------------------------------
## Wyświetlenie listy rezerwacji danego użytkownika

GET /conference/{login}/getbookings

Przykład: http://localhost:8080/conference/loginexample/getbookings

-----------------------------------------------------------------------
## Zmiana adresu email

PATCH /conference/user/emailupdate

Request Parameters:
- login
- email
- newEmail

Przykład: http://localhost:8080/conference/user/emailupdate?login=loginexample&email=emailexample&newEmail=newemailexample
