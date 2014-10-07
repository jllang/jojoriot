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
        scanner = new Scanner("1\nasd\nasd\nasd\nasd\nasd\n\n\n\n\n\n\n4\nasd.bib\n7")
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