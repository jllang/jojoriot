import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi luoda uuden artikkeli-tyyppin viittauksen'

scenario "Käyttäjä luo uuden artikkeli-tyypin viittauksen", {
    given 'Käyttäjä valitsee uuden artikkeli-viittauksen luomisen'
    when 'tiedot viittaukseen syötetään'
    then 'viittauksen luominen onnistuu'
}

