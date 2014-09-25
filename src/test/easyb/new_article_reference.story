import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi luoda uuden artikkeli-tyyppin viittauksen'

scenario "Käyttäjä luo uuden artikkeli-tyypin viittauksen", {
    given 'Käyttäjä valitsee uuden artikkeli-viittauksen luomisen', {
        io = new Stub("1", "asd", "asd", "asd", "2014", "", "", "", "", "" ,
            "", "3")
        session = new Session()
        ui = new CLI(io, session)
    }

    when 'tiedot viittaukseen syötetään oikein', {
        ui.start()
    }

    then 'viittauksen luominen onnistuu', {
        io.getPrints().shouldHave("Reference added!")
    }
}

scenario "artikkeli-tyypin viittauksen luominen epäonnistuu", {
    given 'Käyttäjä valitsee uuden artikkeli-viittauksen luomisen', {
        io = new Stub("1", "asd", "asd", "asd", "", "", "", "", "", "" , "",
            "3")
        session = new Session()
        ui = new CLI(io, session)
    }

    when 'tiedot viittaukseen syötetään väärin', {
        ui.start()
    }

    then 'viittauksen luominen epäonnistuu', {
        io.getPrints().shouldHave("Adding reference failed!")
    }
}

