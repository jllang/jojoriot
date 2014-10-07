import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi tarkastella viittausta bibtext-muodossa'

scenario "Käyttäjä tarkastelee yrittää tarkastella viittauksia joita ei ole", {
    given 'Yhtään viittausta ei ole syötetty', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("3\n7")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'Käyttäjä valitsee viittausten esikatselun', {
        cli.start()
    }

    then 'viittauksia ei näytetä', {
        output = out.toString()
        output.shouldNotHave("@")
    }
}

scenario "Käyttäjä tarkastelee bibtext-muotoista referenceä", {
    given 'Syötetään uusi viittaus', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\nasd\nasd\nasd\nasd\nasd\n\n\n\n\n\n\n3\n7")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'Käyttäjä valitsee viittausten esikatselun', {
        cli.start()
    }

    then 'viittauksia ei näytetä', {
        output = out.toString()
        output.shouldHave("@Article{asd")
    }
}