package cli

import logic.Project
import logic.generator.SerieDescriptor

object UI {

  def menu(project: Project) = {
    val menu = List(
      Choice("Oeuvre", oeuvre(project)),
      Choice("Casting", casting(project)),
      Choice("Planning", planning(project)))

    println("--- Menu ---")
    choices(menu)
  }

  def planning(project: Project) = {
    val menu = List(
      Choice("Menu", menu(project))
    )

    println("-- Planning --")
    choices(menu)
  }

  def casting(project: Project) = {
    val menu = List(
      Choice("Menu", menu(project))
    )

    println("-- Casting --")
    choices(menu)
  }

  def oeuvre(project: Project) = {
    val menu = List(
      Choice("Menu", menu(project))
    )

    println("-- Oeuvre --")
    choices(menu)
  }

  def serie = {
    print("Title: ")
    val title = readString()
    print("Seasons: ")
    val nbSaison = readInt()
    val theme = "Cool"
    print("Episodes: ")
    val nbEpisode = readInt()

    new SerieDescriptor(title, theme, nbSaison, nbEpisode)
  }

  def readString(): String = {
    scala.io.StdIn.readLine()
  }

  def readInt(): Int = {
    try
      scala.io.StdIn.readLine().toInt
    catch {
      case e: Exception => {
        print("enter a number")
        readInt()
      }
    }
  }

  def choices[T](choices: List[Choice[T]]) = {
    var i = 1
    do {
      choices.zipWithIndex.foreach { case (choice, index) => println(index + 1 + " - " + choice.name) }
      i = readInt() - 1
    } while (choices.isDefinedAt(i))
    choose(i, choices)
  }

  def choose[T](choice: Int, choices: List[Choice[T]]) = {
    choices(choice).fun
  }
}
