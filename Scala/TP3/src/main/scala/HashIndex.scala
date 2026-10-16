import java.io.RandomAccessFile
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.util.Random
import scala.annotation.tailrec

object Config {
  // Stockage
  val recordSize: Int = 20 // taille d'un enregistrement
  val maxFileSize: Int = 2048 // taille limite de l'active log
  val dataDir: String = "data" // répertoire dédié (qu'on vide au démarrage)
  val activeLogName: String = "active.log"
  val archivePrefix: String = "log-" // préfixe : log-<timestamp>

  // Liste de clé pour le générateur de jeux de données
  val keys: List[String] =
    List("abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yza", "bcd")
  // pause pour assurer des timestamp de log différent (2 à 3s demandé dans le sujet)
  val pauseMinMs: Int = 10
  val pauseMaxMs: Int = 30

  // Nombre d'enregistrements par fichier, et nombre à générer
  // 3 archivages, puis 5 enregistrements dans l'active log
  // (il reste donc des clés absentes de l'index pour l'expérimentation)
  val recordsPerFile: Int = maxFileSize / recordSize
  val recordsToGenerate: Int = 3 * recordsPerFile + 5

  // nombre de requêtes par clé pour faire la moyenne du temps passé
  val repetitions: Int = 200
}

case class State(
                  index: Map[String, Int], // Map<clé, offset>
                  activeLogCurrentSize: Int, // taille courante de l'active log
                  archives: List[String] // noms des logs archivés du plus récent au plus ancien
                )

object State {
  val empty: State = State(Map.empty, 0, Nil)
}

// Enregistrement fichier
object Record {
  // Encodage d'une clé : 'key:value + n * de padding'
  def encode(key: String, value: String): String =
    s"$key:$value".padTo(Config.recordSize, '*').take(Config.recordSize)

  // Inverse d'encoder : Some((clé, valeur)) ou None si l'enregistrement est mal formé
  def decode(record: String): Option[(String, String)] = {
    val sep = record.indexOf(':')
    if (sep < 0) None
    else Some((record.take(sep), record.drop(sep + 1).takeWhile(_ != '*')))
  }

  // Valeur la plus récente d'une clé
  // Les enregistrements sont reçus du plus récent au plus ancien le premier trouvé est le bon.
  @tailrec
  def latest(key: String, recordsMostRecentFirst: List[String]): Option[String] =
    recordsMostRecentFirst match {
      case Nil => None
      case r :: rest =>
        decode(r) match {
          case Some((k, v)) if k == key => Some(v)
          case _ => latest(key, rest)
        }
    }
}

object Storage {
  def activePath: String = s"${Config.dataDir}/${Config.activeLogName}"

  def archivePath(name: String): String = s"${Config.dataDir}/$name"

  // Crée le répertoire de données et supprime les potentiels fichiers d'une exécution précédente
  def reset(): Unit = {
    val dir = Paths.get(Config.dataDir)
    Files.createDirectories(dir)
    dir.toFile.listFiles.foreach(_.delete())
  }

  // Ajoute un enregistrement à la fin de l'active log
  def append(record: String): Unit =
    Files.write(
      Paths.get(activePath),
      record.getBytes(StandardCharsets.UTF_8),
      StandardOpenOption.CREATE,
      StandardOpenOption.APPEND
    )

  // Lit l'enregistrement situé à un offset donné
  def readAt(path: String, offset: Int): String = {
    val file = new RandomAccessFile(path, "r")
    try {
      val buffer = new Array[Byte](Config.recordSize)
      file.seek(offset.toLong)
      file.readFully(buffer)
      new String(buffer, StandardCharsets.UTF_8)
    } finally file.close()
  }

  // Lit tous les enregistrements d'un fichier (dans l'ordre d'écriture)
  def readRecords(path: String): List[String] = {
    val content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8)
    content.grouped(Config.recordSize).toList
  }

  // Renomme l'active log en log-<timestamp> et renvoie le nouveau nom
  def archiveActive(): String = {
    val name = s"${Config.archivePrefix}${System.currentTimeMillis()}"
    Files.move(Paths.get(activePath), Paths.get(archivePath(name)))
    name
  }
}

object Engine {
  // Renvoie vrai si l'ajout d'un enregistrement dépasserait la taille limite de l'active log
  def isFull(state: State): Boolean =
    state.activeLogCurrentSize + Config.recordSize > Config.maxFileSize

  // Archive l'active log, le réinitialise et réinitialise l'index
  private def rotate(state: State): State = {
    val name = Storage.archiveActive()
    State(Map.empty, 0, name :: state.archives)
  }

  // Enregistre clé/valeur dans l'active log et indexe la clé, si l'active log est plein, il est d'abord archivé.
  def put(state: State, key: String, value: String): State = {
    val ready = if (isFull(state)) rotate(state) else state
    Storage.append(Record.encode(key, value))
    ready.copy(
      index = ready.index + (key -> ready.activeLogCurrentSize),
      activeLogCurrentSize = ready.activeLogCurrentSize + Config.recordSize
    )
  }

  // Recherche séquentielle dans les logs archivés, du plus récent au plus ancien
  @tailrec
  private def findInArchives(archives: List[String], key: String): Option[String] =
    archives match {
      case Nil => None
      case name :: older =>
        val records = Storage.readRecords(Storage.archivePath(name))
        Record.latest(key, records.reverse) match {
          case Some(value) => Some(value)
          case None => findInArchives(older, key)
        }
    }

  // Valeur la plus récente d'une clé :
  // clé dans l'index -> lecture à l'offset dans l'active log, sinon -> recherche dans les logs archivés
  def get(state: State, key: String): Option[String] =
    state.index.get(key) match {
      case Some(offset) =>
        Record.decode(Storage.readAt(Storage.activePath, offset)).map(_._2)
      case None => findInArchives(state.archives, key)
    }
}

// Affichage terminal pour l'expérimentation
object Display {
  def index(state: State): String =
    state.index.toList.sorted
      .map { case (k, offset) => s"    $k -> $offset" }
      .mkString("\n")

  @tailrec
  private def printRecords(records: List[String], offset: Int): Unit = records match {
    case Nil => ()
    case r :: rest =>
      println(f"    $offset%5d : $r")
      printRecords(rest, offset + Config.recordSize)
  }

  // Affiche un fichier (les 3 premiers et les 2 derniers enregistrements si long)
  def file(path: String): Unit = {
    val records = Storage.readRecords(path)
    val n = records.size
    println(s"  $path ($n enregistrement(s), ${n * Config.recordSize} octets)")
    if (n <= 6) printRecords(records, 0)
    else {
      printRecords(records.take(3), 0)
      println("    ...")
      printRecords(records.drop(n - 2), (n - 2) * Config.recordSize)
    }
  }

  // Rapport d'un archivage : index avant / après, fichiers sur disque
  def rotation(before: State, after: State): Unit = after.archives match {
    case Nil => ()
    case archive :: _ =>
      println(s"\nL'active log a atteint sa taille limite, Archivage (${before.activeLogCurrentSize}/${Config.maxFileSize}")
      println("Index avant :")
      println(index(before))
      println("Fichier archivé :")
      file(Storage.archivePath(archive))
      println("Index après (nouvelle clé enregistrée) :")
      println(index(after))
      println("Nouvel active log :")
      file(Storage.activePath)
  }

  def query(state: State, key: String): Unit = {
    val inIndex = if (state.index.contains(key)) "oui" else "non"
    Engine.get(state, key) match {
      case Some(value) =>
        println(s"  get($key) : clé dans l'index = $inIndex -> valeur la plus récente = $value")
      case None =>
        println(s"  get($key) : clé dans l'index = $inIndex -> clé introuvable (index et logs archivés)")
    }
  }
}

// Générateur jeux de test
object Generator {
  private def randomKey(): String = Config.keys(Random.nextInt(Config.keys.size))

  private def randomValue(): String = Random.nextInt().toString

  private def randomPause(): Int =
    Config.pauseMinMs + Random.nextInt(Config.pauseMaxMs - Config.pauseMinMs + 1)

  // Génère `remaining` couples clé/valeur et les enregistre dans le stockage
  @tailrec
  def run(state: State, remaining: Int): State =
    if (remaining == 0) state
    else {
      Thread.sleep(randomPause().toLong)
      val next = Engine.put(state, randomKey(), randomValue())
      if (Engine.isFull(state)) Display.rotation(state, next)
      run(next, remaining - 1)
    }
}

object Experiment {
  private def timed[A](f: () => A): (A, Long) = {
    val start = System.nanoTime()
    val result = f()
    (result, System.nanoTime() - start)
  }

  // Temps moyenen microsecondes d'un get, sur les clés données
  private def averageMicros(state: State, keys: List[String]): Option[Double] = {
    val times =
      for {
        key <- keys
        _ <- List.range(0, Config.repetitions)
      } yield timed(() => Engine.get(state, key))._2
    if (times.isEmpty) None
    else Some(times.sum.toDouble / times.size / 1000.0)
  }

  private def report(label: String, result: Option[Double]): Unit = result match {
    case Some(us) => println(f"  $label : $us%.1f microsecondes en moyenne")
    case None => println(s"  $label : aucune clé concernée")
  }

  def run(state: State): Unit = {
    val inActiveLog = Config.keys.filter(state.index.contains)
    val notInActiveLog = Config.keys.filterNot(state.index.contains)

    averageMicros(state, Config.keys)

    println(s"\n Expérimentation : (${Config.repetitions} requêtes par clé)")
    report(s"Valeur dans l'active log      (${inActiveLog.size} clés)", averageMicros(state, inActiveLog))
    report(s"Valeur hors de l'active log   (${notInActiveLog.size} clés)", averageMicros(state, notInActiveLog))
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println("Paramètres : ")
    println(s"  taille d'un enregistrement : ${Config.recordSize} octets")
    println(s"  taille limite de l'active log : ${Config.maxFileSize} octets (${Config.recordsPerFile} enregistrements)")
    println(s"  enregistrements à générer : ${Config.recordsToGenerate}")

    Storage.reset()
    val finalState = Generator.run(State.empty, Config.recordsToGenerate)

    println("\nÉtat final : ")
    println("Index :")
    println(Display.index(finalState))
    println(s"Logs archivés (du plus récent au plus ancien) : ${finalState.archives.mkString(", ")}")
    println("Active log :")
    Display.file(Storage.activePath)

    println("\nRequête :")
    val inActiveLog = Config.keys.filter(finalState.index.contains)
    val notInActiveLog = Config.keys.filterNot(finalState.index.contains)
    println("Clé présente dans l'index (valeur la plus récente) :")
    inActiveLog.take(1).foreach(key => Display.query(finalState, key))
    println("Clé absente de l'index mais présente dans un log archivé :")
    notInActiveLog.take(1).foreach(key => Display.query(finalState, key))
    println("Clé inconnue :")
    Display.query(finalState, "zzz")

    Experiment.run(finalState)
  }
}
