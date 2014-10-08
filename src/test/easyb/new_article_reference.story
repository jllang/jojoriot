import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi luoda uuden artikkeli-tyyppin viittauksen'

scenario "Käyttäjä luo uuden artikkeli-tyypin viittauksen", {
    given 'Käyttäjä valitsee uuden artikkeli-viittauksen luomisen', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\n1\nasd\nasd\nasd\nasd\n2014\n\n\n\n\n\n\n8")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'tiedot viittaukseen syötetään oikein', {
        cli.start()
    }

    then 'viittauksen luominen onnistuu', {
        output = out.toString()
        output.shouldHave("Reference added:")
    }
}

