import java.io.File
import java.util.Scanner
import jojoriot.IO.*
import jojoriot.UI.*
import jojoriot.references.*
import jojoriot.viitemanageri.*

description 'Filu talteen'

scenario "Tallennus onnistuu", {
    given 'Käyttäjä valitsee viittausten tallentamisen', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\n1\nasd\nasd\nasd\nasd\nasd\n\n\n\n\n\n\n4\nasd.bib\n8")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'tiedoston nimi kirjoitetaan', {
        cli.start()
    }

    then 'tiedosto on tallennettu', {
        file = new File("asd.bib")
        s = new Scanner(file)
        lines = 0
        while (s.hasNextLine()) {
            s.nextLine()
            lines++
        }
        file.delete()
        lines.shouldEqual(6)
    }
}
scenario "Tiedoston lataaminen onnistuu", {
    given 'Käyttäjä valitsee viittausten tallentamisen', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("1\n1\nasd\nasd\nasd\nasd\nasd\n\n\n\n\n\n\n4\nasd.bib\n6\nasd.bib\n3\n\n8")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'tiedoston nimi kirjoitetaan', {
        cli.start()
    }

    then 'tiedosto on tallennettu', {
        output = out.toString()
        output.shouldHave("@Article{asd,")
    }
}
scenario "Tiedoston lataaminen epäonnistuu, koska nimi väärä", {
    given 'Käyttäjä valitsee viittausten tallentamisen', {
        out = new ByteArrayOutputStream()
        scanner = new Scanner("6\njoujoublingbling.filu\n\n8")
        session = new Session()
        cli = new CLI(scanner, new PrintStream(out), session)
    }

    when 'tiedoston nimi kirjoitetaan', {
        cli.start()
    }

    then 'tiedosto on tallennettu', {
        output = out.toString()
        output.shouldHave("File not found!")
    }
}