# file-filing-application
Opis i cel zadania
Twoim zadaniem jest napisanie programu, który będzie umożliwiał segregowanie plików. Program powinien:
stworzyć strukturę katalogów
HOME
DEV
TEST
W momencie pojawienia się w katalogu HOME pliku w zależności od rozszerzenia przeniesie go do folderu wg następujących reguł
plik z rozszerzeniem .jar, którego godzina utworzenia jest parzysta przenosimy do folderu DEV
plik z rozszerzeniem .jar, którego godzina utworzenia jest nieparzysta przenosimy do folderu TEST
plik z rozszerzeniem .xml, przenosimy do folderu DEV
Dodatkowo w nowo stworzonym pliku /home/count.txt należy przechowywać liczbę przeniesionych plików (wszystkich i w podziale na
katalogi), plik powinien w każdym momencie działania programu przechowywać aktualną liczbę przetworzonych plików.
