package logic.generator

import logic.generator.constraint.Constraint

abstract class OeuvreGenerator {
  type O <: Oeuvre
  type D <: Descriptor

  def generate(desc: D): O

  def regenerate(constraints: List[Constraint], oeuvre: O): O
}

object OeuvreGenerator {
  def apply(kind: String) = kind match {
    case "Serie" => new SerieGenerator()
  }
}