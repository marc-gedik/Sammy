package scriptGenerator

abstract class OeuvreGenerator {
  type O <: Oeuvre
  type D <: Descriptor 
  def generate(desc : D):O
  def regenerate(contraints : Array[Constraint], oeuvre : O) : O
}

object OeuvreGenerator {
  def apply(Kind : String) = Kind match {
    case "Serie" => new SerieGenerator()
  }
}