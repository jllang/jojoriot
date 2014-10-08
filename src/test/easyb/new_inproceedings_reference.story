import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Käyttäjä voi luoda uuden inproceedings-tyyppin viittauksen'

scenario "Käyttäjä luo uuden inproceedings-tyypin viittauksen", {
    given 'Käyttäjä valitsee uuden inproceedings-viittauksen luomisen', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\n3\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\nBlaa\n8")
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
