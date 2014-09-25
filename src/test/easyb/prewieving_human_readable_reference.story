import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi tarkastella viittausta ihmisluettavassa muodossa'

scenario "Käyttäjä tarkastelee viittausta", {
    given 'Käyttäjä valitsee viittausten esikatselun'
    when 'käyttäjä valitsee listasta halutun viittauksen'
    then 'viittaus näytetään oikeassa muodossa'
}

