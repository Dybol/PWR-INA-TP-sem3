### Uruchamianie serwera

Należy uruchomić metodę main z klasy ChineseCheckersServer.

### Uruchamianie klientów

Należy uruchomić metode main z klasy ChineseCheckersClient, podając jako argument adres serwera, czyli w przypadku
uruchamiania aplikacji na własnym komputerze, będzie to localhost.

### Początek gry

Grę zaczyna zawsze gracz, który pierwszy dołączy. W momencie, gdy dołączą wszyscy gracze, którzy mają wziąć udział w
danej rozgrywce, klika on przycisk start i gra się rozpoczyna.
<br>

#### Wariant dla 3 graczy:

<li> Pierwszy gracz, który zaczyna ma kolor niebieski </li>
<li> Drugi gracz ma kolor pomarańczowy </li>
<li> Trzeci gracz ma kolor żółty </li>

#### Wariant dla parzystej ilości graczy:

<li>Pierwszy gracz, który zaczyna ma kolor czerwony</li>
<li>Drugi gracz ma kolor niebieski</li>
<li>Trzeci gracz ma kolor pomarańczowy</li>
<li>Czwarty gracz ma kolor zółty</li>
<li>Piąty gracz ma kolor różowy</li>
<li>Szósty gracz ma kolor szary</li>
Oczywiście w przypadku, gdy w grze będzie brało udział mniej osób, pozostałe kolory będą nieużywane.

Ruch wykonujemy klikając na kwadrat (pionek), którym chcemy się ruszyć i przeciągamy go na inny kwadrat. Należy
pamiętać, że **białe kwadraty** służą tylko do narysowania planszy i **nie biorą udziału w grze**.
<br><br>
Poprawność każdego ruchu jest weryfikowana, więc jeżeli gracz złamał jakieś zasady gry, to wyświetli mu się odpowiednia
wiadomość i ruch nie zostanie wykonany.

## Diagram klas:

![diagram](https://i.imgur.com/r1pZljT.png)

Opis poszczególnych komponentów został zawarty w dokumentacji.