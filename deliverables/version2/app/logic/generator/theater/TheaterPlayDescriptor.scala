package logic.generator

class TheaterPlayDescriptor(
  val title: String,
  val theme: String,
  val nbAct: Int) extends Descriptor

object TheaterPlayDescriptor {
  def apply(title: String, theme: String, nbAct: Int): TheaterPlayDescriptor = {
    new TheaterPlayDescriptor(title, theme, nbAct)
  }

  def unapply(theaterPlayDescriptor: TheaterPlayDescriptor): Option[(String, String, Int)] = {
    Option(theaterPlayDescriptor.title, theaterPlayDescriptor.theme, theaterPlayDescriptor.nbAct)
  }
}
