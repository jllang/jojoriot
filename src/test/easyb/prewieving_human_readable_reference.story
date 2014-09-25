import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi tarkastella viittausta ihmisluettavassa muodossa'

scenario "Käyttäjä tarkastelee viittausta", {
    given 'Käyttäjä valitsee viittausten esikatselun', {
        io = new Stub("2")
        session = new Session()
        ui = new CLI(io, session)
    }

    when 'käyttäjä valitsee listasta halutun viittauksen', {
        ui.start()
    }

    then 'viittaus näytetään oikeassa muodossa', {
        io.getPrints().shouldHave("")
    }
}

