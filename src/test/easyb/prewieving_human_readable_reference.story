import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi tarkastella viittausta ihmisluettavassa muodossa'

scenario "Käyttäjä tarkastelee viittausta", {
    given 'Käyttäjä valitsee viittausten esikatselun', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\nasd\nasd\nasd\nasd\nasd\n\n\n\n\n\n\n3\n4")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'käyttäjä valitsee listasta halutun viittauksen', {
        cli.start()
    }

    then 'viittaus näytetään oikeassa muodossa', {
        output = out.toString()
        output.shouldHave("title: asd")
    }
}

